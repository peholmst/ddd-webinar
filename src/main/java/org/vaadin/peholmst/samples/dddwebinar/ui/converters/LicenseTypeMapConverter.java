package org.vaadin.peholmst.samples.dddwebinar.ui.converters;

import java.util.*;
import java.util.stream.Collectors;

import org.vaadin.peholmst.samples.dddwebinar.domain.licensing.LicenseType;

import com.vaadin.data.util.converter.Converter;

public class LicenseTypeMapConverter implements Converter<String, Map> {
    @Override
    public Map convertToModel(String value, Class<? extends Map> targetType, Locale locale) throws ConversionException {
        throw new ConversionException("This is a one-way converter");
    }

    @Override
    @SuppressWarnings("unchecked")
    public String convertToPresentation(Map value, Class<? extends String> targetType, Locale locale)
        throws ConversionException {
        List<Map.Entry<LicenseType, Integer>> entryList = new ArrayList<>(value.entrySet());
        Collections.sort(entryList, (e1, e2) -> e1.getValue() - e2.getValue());
        return String.join(", ", entryList.stream()
            .map(e -> String.format("%s (%d)", e.getKey().getName(), e.getValue())).collect(Collectors.toList()));
    }

    @Override
    public Class<Map> getModelType() {
        return Map.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }
}
