package org.vaadin.peholmst.samples.dddwebinar.domain.patients;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.Entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Patient extends AbstractPersistable<Long> {

    private String firstName;
    private String lastName;
    private Date dateOfBirth;

    public Patient() {
    }

    public Patient(String firstName, String lastName, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = Date.valueOf(dateOfBirth);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth == null ? null : dateOfBirth.toLocalDate();
    }

    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }
}
