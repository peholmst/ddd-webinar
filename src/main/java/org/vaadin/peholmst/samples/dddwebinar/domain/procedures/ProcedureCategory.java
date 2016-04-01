package org.vaadin.peholmst.samples.dddwebinar.domain.procedures;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.vaadin.peholmst.samples.dddwebinar.domain.licensing.LicenseType;

@Entity
public class ProcedureCategory extends AbstractPersistable<Long> {

    private String name;
    @ElementCollection(fetch = FetchType.EAGER)
    private Map<LicenseType, Integer> licenseTypes = new HashMap<>();

    public ProcedureCategory() {
    }

    public ProcedureCategory(String name, Map<LicenseType, Integer> licenseTypes) {
        this.name = name;
        this.licenseTypes = licenseTypes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<LicenseType, Integer> getLicenseTypes() {
        return licenseTypes;
    }

    public void setLicenseTypes(
        Map<LicenseType, Integer> licenseTypes) {
        this.licenseTypes = licenseTypes;
    }

    public static class Builder {
        private String name;
        private Map<LicenseType, Integer> licenseTypes = new HashMap<>();

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withLicenseType(LicenseType licenseType, int rank) {
            licenseTypes.put(licenseType, rank);
            return this;
        }

        public ProcedureCategory build() {
            return new ProcedureCategory(name, licenseTypes);
        }
    }
}
