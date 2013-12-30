package pl.edu.pw.elka.ire.asiom

import org.springframework.beans.factory.annotation.Autowired
import pl.edu.pw.elka.ire.asiom.descriptor.Descriptor
import pl.edu.pw.elka.ire.asiom.descriptor.DescriptorValue
import pl.edu.pw.elka.ire.asiom.image.descritors.ColorLayoutDescriptorUtils
import pl.edu.pw.elka.ire.asiom.image.types.ImageData

class DescriptorService {

    @Autowired
    ColorLayoutDescriptorUtils colorLayoutDescriptorUtils;

    Descriptor calculateDescriptors(ImageData image) {
        colorLayoutDescriptorUtils.createTinyImage(image);

        Descriptor fake_descriptor = new Descriptor(type: Descriptor.Type.FAKE)
        fake_descriptor.addToValues(new DescriptorValue(value: 0, ordinalNumber: 0))
        fake_descriptor.addToValues(new DescriptorValue(value: 1, ordinalNumber: 1))
        fake_descriptor.addToValues(new DescriptorValue(value: 2, ordinalNumber: 2))
        fake_descriptor.addToValues(new DescriptorValue(value: 3, ordinalNumber: 3))
        return fake_descriptor;
    }
}
