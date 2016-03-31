package org.vaadin.peholmst.samples.dddwebinar.domain.doctors;

import javax.persistence.Entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Doctor extends AbstractPersistable<Long> {

    private String firstName;
    private String lastName;

    public Doctor() {
    }

    public Doctor(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

}
