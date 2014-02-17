package pl.edu.pw.elka.ire.asiom

import net.semanticmetadata.lire.imageanalysis.ColorLayout
import net.semanticmetadata.lire.imageanalysis.EdgeHistogram
import org.apache.commons.lang.ArrayUtils
import pl.edu.pw.elka.ire.asiom.descriptor.Descriptor
import pl.edu.pw.elka.ire.asiom.descriptor.DescriptorValue

import java.awt.image.BufferedImage

class DescriptorService {

    def calculateDescriptors(BufferedImage image) {
        return [extractCLDValues(image), extractEHDValues(image)];
    }

    private Descriptor extractCLDValues(BufferedImage image) {
        Descriptor cld = new Descriptor(type: Descriptor.Type.CLD);

        ColorLayout vd = new ColorLayout();
        vd.extract(image);

        int i = 0;
        for (Integer value : ArrayUtils.addAll(ArrayUtils.addAll(Arrays.copyOf(vd.getYCoeff(), 6), Arrays.copyOf(vd.getCbCoeff(), 3)), Arrays.copyOf(vd.getCrCoeff(), 3))) {
            cld.addToValues(new DescriptorValue(value: value, ordinalNumber: i++))
        }

        return cld;
    }

    private Descriptor extractEHDValues(BufferedImage image) {
        Descriptor ehd = new Descriptor(type: Descriptor.Type.EHD);

        EdgeHistogram vd = new EdgeHistogram();
        vd.extract(image);

        int i = 0;
        for (Integer value : vd.getDoubleHistogram()) {
            ehd.addToValues(new DescriptorValue(value: value, ordinalNumber: i++))
        }

        return ehd;
    }
}
