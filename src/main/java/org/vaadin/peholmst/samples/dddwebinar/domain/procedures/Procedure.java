package org.vaadin.peholmst.samples.dddwebinar.domain.procedures;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Procedure extends AbstractPersistable<Long> {

    private BigDecimal fee;
    private String code;
    @ManyToOne
    private ProcedureCategory category;

    public Procedure() {
    }

    public Procedure(BigDecimal fee, String code, ProcedureCategory category) {
        this.fee = fee;
        this.code = code;
        this.category = category;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public String getCode() {
        return code;
    }

    public ProcedureCategory getCategory() {
        return category;
    }
}
