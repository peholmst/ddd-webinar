package org.vaadin.peholmst.samples.dddwebinar.ui.converters;

import java.math.BigDecimal;
import java.util.Locale;

import com.vaadin.data.util.converter.Converter;

public class MoneyConverter implements Converter<String, BigDecimal> {
    @Override
    public BigDecimal convertToModel(String value, Class<? extends BigDecimal> targetType, Locale locale)
        throws ConversionException {
        throw new ConversionException("This is a one-way converter");
    }

    @Override
    public String convertToPresentation(BigDecimal value, Class<? extends String> targetType, Locale locale)
        throws ConversionException {
        return value == null ? "" : String.format("$%.2f", value.doubleValue());
    }

    @Override
    public Class<BigDecimal> getModelType() {
        return BigDecimal.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }
}
