package pl.edu.pw.elka.ire.asiom

import pl.edu.pw.elka.ire.asiom.descriptor.Descriptor

class ImageFile {

    String name;

    byte[] data;

    static hasMany = [descriptors: Descriptor];

    static constraints = {
        data nullable: false, maxSize: 1024 * 1024 * 2
        name nullable: false
    }

    static mapping = {
        table 'IMAGE'
        version false
        id column: 'ID'
        name column: 'FILE_NAME'
        data column: 'DATA'
        descriptors cascade: 'all', 'save-update', 'all-delete-orphan'
    }
}
