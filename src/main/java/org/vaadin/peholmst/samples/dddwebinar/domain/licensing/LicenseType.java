package org.vaadin.peholmst.samples.dddwebinar.domain.licensing;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class LicenseType extends AbstractPersistable<Long> {

    private String name;
    @Column(unique = true)
    private String code;

    public LicenseType() {
    }

    public LicenseType(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
