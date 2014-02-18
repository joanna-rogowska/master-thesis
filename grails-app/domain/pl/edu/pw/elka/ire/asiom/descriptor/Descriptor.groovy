package pl.edu.pw.elka.ire.asiom.descriptor

import pl.edu.pw.elka.ire.asiom.ImageFile

class Descriptor {

    Type type

    List values

    static hasMany = [values: DescriptorValue]

    static belongsTo = [image: ImageFile]

    static constraints = {
        type nullable: false
        values nullable: false
        image nullable: false
    }

    static mapping = {
        table 'DESCRIPTOR'
        version false
        id column: 'ID'
        type column: 'TYPE'
        image column: 'IMAGE_ID', index: 'IMAGE_ID_IDX'
        values cascade: 'all', 'save-update', 'all-delete-orphan'
    }

    enum Type {
        CLD,
        EHD
    }

    def getValuesAsList() {
        def list = new ArrayList<Double>();
        values.findAll {
            list[it.ordinalNumber] = it.value
        }
        return list
    }
}
