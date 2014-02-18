package pl.edu.pw.elka.ire.asiom

import org.springframework.beans.factory.annotation.Autowired
import pl.edu.pw.elka.ire.asiom.descriptor.Descriptor

import java.awt.image.BufferedImage

class ImageService {

    @Autowired
    DescriptorService descriptorService

    def processImageCalculation(ImageFile image, BufferedImage bimg) {
        descriptorService.calculateDescriptors(bimg).each { descriptor ->
            image.addToDescriptors(descriptor);
        }
    }

    def orderByClosestDistance(final def image, def images) {
        def imageDescValues = getBaseImageDescValues(image)
        return images.sort { image1, image2 ->
            def (double image1Dist, double image2Dist) = countCosDistanceForImages(image1, image2, imageDescValues)
            (image2Dist<=>image1Dist) //descendant order
        }
    }

    private List countCosDistanceForImages(def image1, def image2, def baseImageValues) {
        double image1Dist = 1;
        double image2Dist = 1;
        for (Descriptor.Type type : Descriptor.Type.values()) {
            def image1DescValues = image1.descriptors.find { it.type == type }.values.collect { it.value }
            def image2DescValues = image2.descriptors.find { it.type == type }.values.collect { it.value }
            image1Dist *= countCosDistanceForVectors(baseImageValues[type], image1DescValues);
            image2Dist *= countCosDistanceForVectors(baseImageValues[type], image2DescValues);
        }
        [image1Dist, image2Dist]
    }

    private def getBaseImageDescValues(image) {
        def imageDescValues = [:]
        for (Descriptor.Type type : Descriptor.Type.values()) {
            imageDescValues.put(type, image.descriptors.find { it.type == type }.values.collect { it.value })
        }
        return imageDescValues
    }

    float countCosDistanceForVectors(List<Integer> baseVec, List<Integer> distanceVec) {
        float dotOperationResult = 0;
        float baseVecSize = 0;
        float distanceVecSize = 0;
        for (int i = 0; i < baseVec.size() && i < distanceVec.size(); ++i) {
            dotOperationResult += baseVec.get(i) * distanceVec.get(i);
            baseVecSize += baseVec.get(i) * baseVec.get(i);
            distanceVecSize += distanceVec.get(i) * distanceVec.get(i);
        }
        double cosValue = dotOperationResult / (Math.sqrt(baseVecSize) * Math.sqrt(distanceVecSize));
        return Math.pow(cosValue, cosValue);
    }
}
