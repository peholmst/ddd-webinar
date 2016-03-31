package org.vaadin.peholmst.samples.dddwebinar.ui.converters;

import java.util.Locale;

import org.vaadin.peholmst.samples.dddwebinar.domain.doctors.Doctor;

import com.vaadin.data.util.converter.Converter;

public class DoctorConverter implements Converter<String, Doctor> {
    @Override
    public Doctor convertToModel(String value, Class<? extends Doctor> targetType, Locale locale)
        throws ConversionException {
        throw new ConversionException("This is a one-way converter");
    }

    @Override
    public String convertToPresentation(Doctor value, Class<? extends String> targetType, Locale locale)
        throws ConversionException {
        return value == null ? "" : String.format("Dr. %s %s", value.getFirstName(), value.getLastName());
    }

    @Override
    public Class<Doctor> getModelType() {
        return Doctor.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }
}
