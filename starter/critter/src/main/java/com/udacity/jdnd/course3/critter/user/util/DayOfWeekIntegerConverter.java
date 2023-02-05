package com.udacity.jdnd.course3.critter.user.util;

import javax.persistence.Converter;
import java.time.DayOfWeek;

import static java.util.Optional.ofNullable;

@Converter
public class DayOfWeekIntegerConverter
        implements EnumAttributeConverter<DayOfWeek, Integer> {

    @Override
    public Integer convertToDatabaseColumn(final DayOfWeek attribute) {
        return ofNullable(attribute).map(DayOfWeek::getValue).orElse(null);
    }

    @Override
    public DayOfWeek convertToEntityAttribute(final Integer dbData) {
        return ofNullable(dbData).map(DayOfWeek::of).orElse(null);
    }
}

