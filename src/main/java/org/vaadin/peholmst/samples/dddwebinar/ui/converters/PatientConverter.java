package org.vaadin.peholmst.samples.dddwebinar.ui.converters;

import java.util.Locale;

import org.vaadin.peholmst.samples.dddwebinar.domain.patients.Patient;

import com.vaadin.data.util.converter.Converter;

public class PatientConverter implements Converter<String, Patient> {

    @Override
    public Patient convertToModel(String value, Class<? extends Patient> targetType, Locale locale)
        throws ConversionException {
        throw new ConversionException("This is a one-way converter");
    }

    @Override
    public String convertToPresentation(Patient value, Class<? extends String> targetType, Locale locale)
        throws ConversionException {
        return value == null ? "" : String.format("%s %s", value.getFirstName(), value.getLastName());
    }

    @Override
    public Class<Patient> getModelType() {
        return Patient.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }
}
