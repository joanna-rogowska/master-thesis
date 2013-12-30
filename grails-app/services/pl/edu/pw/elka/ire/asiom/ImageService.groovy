package pl.edu.pw.elka.ire.asiom

import org.springframework.beans.factory.annotation.Autowired
import pl.edu.pw.elka.ire.asiom.descriptor.Descriptor
import pl.edu.pw.elka.ire.asiom.image.ImageUtils
import pl.edu.pw.elka.ire.asiom.image.operations.ColorSpaceTransformUtils
import pl.edu.pw.elka.ire.asiom.image.types.ImageData
import pl.edu.pw.elka.ire.asiom.image.types.YCbCrPixel

class ImageService {

    @Autowired
    DescriptorService descriptorService

    @Autowired
    ColorSpaceTransformUtils colorSpaceTransformUtils

    @Autowired
    ImageUtils imageUtils

    def processImageCalculation(ImageFile image) {
        ImageData imageData = imageUtils.extractImageData(image);
        YCbCrPixel[] yCbCrPixels = colorSpaceTransformUtils.convertRGBDataToYCbCr(imageData.getPixels());
        Descriptor fakeDescriptor = descriptorService.calculateDescriptors(new ImageData(yCbCrPixels, imageData.getWidth(), imageData.getHeight()));
        image.addToDescriptors(fakeDescriptor);
    }
}
