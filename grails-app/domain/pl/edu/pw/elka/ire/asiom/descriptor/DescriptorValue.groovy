package pl.edu.pw.elka.ire.asiom.descriptor

import org.apache.commons.lang.builder.HashCodeBuilder

class DescriptorValue implements Serializable {
    Double value

    int ordinalNumber

    static belongsTo = [descriptor: Descriptor]

    static constraints = {
        value nullable: false
        ordinalNumber nullable: false
        descriptor nullable: false
    }

    static mapping = {
        table 'DESCRIPTORS_VALUES'
        version false
        id composite: ['descriptor', 'ordinalNumber']
        value column: 'VALUE' //TODO: set types
        ordinalNumber column: 'ORDINAL_NUMBER'
        descriptor column: 'DESCRIPTOR_ID', index: 'DESCRIPTOR_IDX'
    }

    boolean equals(other) {
        if (!(other instanceof DescriptorValue)) {
            return false
        }
        other.descriptor == descriptor && other.ordinalNumber == ordinalNumber
    }

    int hashCode() {
        def builder = new HashCodeBuilder()
        builder.append descriptor
        builder.append ordinalNumber
        builder.toHashCode()
    }
}
