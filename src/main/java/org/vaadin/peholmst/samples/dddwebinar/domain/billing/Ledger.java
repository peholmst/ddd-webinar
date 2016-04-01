package org.vaadin.peholmst.samples.dddwebinar.domain.billing;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class Ledger {

    private final List<LedgerEntry> entries;
    private final BigDecimal outstanding;

    public Ledger(BigDecimal fee, List<LedgerEntry> entries) {
        this.entries = Collections.unmodifiableList(entries);
        BigDecimal outstanding = fee;
        for (LedgerEntry entry : entries) {
            if (entry.getEntryAmount().signum() < 0) {
                outstanding = outstanding.add(entry.getEntryAmount());
            }
        }
        this.outstanding = outstanding;
    }

    public List<LedgerEntry> getEntries() {
        return entries;
    }

    public BigDecimal getOutstanding() {
        return outstanding;
    }
}
