package pl.edu.pw.elka.ire.asiom

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException

import javax.imageio.ImageIO
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
        render(view: '/index', model: [imageList: ImageFile.listOrderById(params)])
    }

    def save() {
        def f = request.getFile('fileName')
        if (f == null || f.size <= 0) {
            flash.message = message(code: 'image.create.failed.empty')
            redirect(action: "index")
            return
        }
        ImageFile imageInstance = new ImageFile(data: f.getBytes(), name: f.getOriginalFilename())
        imageService.processImageCalculation(imageInstance, ImageIO.read(f.getFileItem().getInputStream()))
        imageInstance.save()
        if (imageInstance.hasErrors()) {
            flash.message = message(code: 'image.create.failed', args: [imageInstance.name])
        } else {
            flash.message = message(code: 'image.create.success', args: [imageInstance.name])
        }
        redirect(action: "index")
    }

    def delete() {
        def imageInstance = ImageFile.get(params.id)
        if (!imageInstance) {
//            flash.message = message(code: 'default.not.found.message', args: [message(code: 'image.label', default: 'ImageFile'), id])
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
//        switch(image.getType()) {
//            case ImageType.DICOM:
//                data = dicomService.getJpegData(image.getData())
//                break;
//            case ImageType.JPEG:
        data = image.data;
//                break;
//        }

        int width = new ImageIcon(data).image.getWidth()
        int height = new ImageIcon(data).image.getHeight()

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
        g2d.drawImage(new ImageIcon(data).image, 0, 0, destinationWidth, destinationHeight, null, null)

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
