package pl.edu.pw.elka.ire.asiom

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException

import javax.imageio.ImageIO
import javax.imageio.ImageReadParam
import javax.imageio.ImageReader
import javax.imageio.stream.ImageInputStream
import javax.swing.*
import java.awt.*
import java.awt.image.BufferedImage

class ImageController {

    static allowedMethods = [save: "POST", index: "GET", delete: "GET"]

    @Autowired
    DescriptorService descriptorService

    @Autowired
    ImageService imageService

    def index() {
        render(view: '/index', model: [imageList: flash.imageInstance != null ?
                imageService.orderByClosestDistance(flash.imageInstance, ImageFile.findAll())
                : ImageFile.listOrderById()])
    }

    def save() {
        ImageFile imageInstance = getImageInstance(request)
        imageInstance.save()
        if (imageInstance.hasErrors()) {
            flash.message = message(code: 'image.create.failed', args: [imageInstance.name])
        } else {
            flash.message = message(code: 'image.create.success', args: [imageInstance.name])
        }
        flash.imageInstance = imageInstance;

        render(view: '/index', model: [imageList: ImageFile.listOrderById()])
    }

    def getImageInstance = { request ->
        def f = request.getFile('fileName')
        if (f == null || f.size <= 0) {
            flash.message = message(code: 'image.create.failed.empty')
            redirect(action: "index")
            return
        }
        ImageFile imageInstance = new ImageFile(data: f.getBytes(), name: f.getOriginalFilename())

        imageService.processImageCalculation(imageInstance, getImageAsBufferedImage(imageInstance))

        return imageInstance
    }

    private BufferedImage getImageAsBufferedImage(ImageFile imageInstance) {
        ImageIO.scanForPlugins()
        ByteArrayInputStream bais = new ByteArrayInputStream(imageInstance.data);
        Iterator<ImageReader> iter = ImageIO.getImageReadersByFormatName("dicom")
        ImageReader reader = (ImageReader) iter.next()
        ImageReadParam param = (ImageReadParam) reader.getDefaultReadParam()
        ImageInputStream iis = ImageIO.createImageInputStream(bais);
        reader.setInput(iis, false);
        return reader.read(0, param) //todo: get representative image (from middle)
    }

    def searchWithImage() {
        flash.imageInstance = getImageInstance(request);

        render(view: '/index',
                model: [imageList: imageService.orderByClosestDistance(flash.imageInstance, ImageFile.findAll()),
                        imageInstance: getImageInstance(request)])
    }

    def delete() {
        def imageInstance = ImageFile.get(params.id)
        if (!imageInstance) {
//            flash.message = message(code: 'default.not.found.message', args: [message(code: 'image.table.name', default: 'ImageFile'), id])
            redirect(action: "index")
            return
        }

        try {
            imageInstance.delete(flush: true)
//            flash.message = message(code: 'default.deleted.message', args: [message(code: 'image.label', default: 'ImageFile'), id])
            redirect(action: "index")
        }
        catch (DataIntegrityViolationException e) {
//            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'image.label', default: 'ImageFile'), id])
            redirect(action: "index", id: id)
        }
    }


    def displayImage = {
        def img = ImageFile.get(params.id)
        def data_small = resizeImage(img)
        response.setHeader('Content-length', data_small.length as String)
        response.contentType = 'types.image/jpg'
        response.outputStream << data_small
        response.outputStream.flush()
    }

    private byte[] resizeImage(ImageFile image) {
        byte[] data;
        int maxWidth = 100
        int maxHeight = 100

        BufferedImage srcImage = getImageAsBufferedImage(image)

        int width = srcImage.getWidth()
        int height = srcImage.getHeight()

        float aspectRatio = width / height
        float requiredAspectRatio = maxWidth / maxHeight

        int destinationWidth = 0
        int destinationHeight = 0
        if (requiredAspectRatio < aspectRatio) {
            destinationWidth = maxWidth
            destinationHeight = Math.round(maxWidth / aspectRatio)
        } else {
            destinationHeight = maxHeight
            destinationWidth = Math.round(maxHeight * aspectRatio)
        }

        BufferedImage bufferedImage = new BufferedImage(destinationWidth, destinationHeight, BufferedImage.TYPE_INT_RGB)
        Graphics2D g2d = bufferedImage.createGraphics()
        g2d.drawImage(srcImage, 0, 0, destinationWidth, destinationHeight, null, null)

        ByteArrayOutputStream baos = new ByteArrayOutputStream(maxWidth * maxHeight);
        byte[] resultImageAsRawBytes = null
        try {
            ImageIO.write(bufferedImage, "jpg", baos);

            baos.flush();
            resultImageAsRawBytes = baos.toByteArray();
        } finally {
            baos.close()
        }

        return resultImageAsRawBytes
    }
}
