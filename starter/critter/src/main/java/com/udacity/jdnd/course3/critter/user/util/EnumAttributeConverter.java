package com.udacity.jdnd.course3.critter.user.util;

import javax.persistence.AttributeConverter;

public interface EnumAttributeConverter<E extends Enum<E>, Y>
        extends AttributeConverter<E, Y> {

}

