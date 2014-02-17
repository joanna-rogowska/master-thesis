package pl.edu.pw.elka.ire.asiom

import org.springframework.beans.factory.annotation.Autowired

import java.awt.image.BufferedImage

class ImageService {

    @Autowired
    DescriptorService descriptorService

    def processImageCalculation(ImageFile image, BufferedImage bimg) {
        descriptorService.calculateDescriptors(bimg).each { descriptor ->
            image.addToDescriptors(descriptor);
        }
    }
}
