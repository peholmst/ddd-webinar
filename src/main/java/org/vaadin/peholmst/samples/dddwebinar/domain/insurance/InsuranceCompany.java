package org.vaadin.peholmst.samples.dddwebinar.domain.insurance;

import javax.persistence.Entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class InsuranceCompany extends AbstractPersistable<Long> {

    private String name;
    private String address;

    public InsuranceCompany() {
    }

    public InsuranceCompany(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
