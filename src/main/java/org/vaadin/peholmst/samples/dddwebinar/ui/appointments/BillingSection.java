package org.vaadin.peholmst.samples.dddwebinar.ui.appointments;

import java.math.BigDecimal;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.peholmst.samples.dddwebinar.domain.billing.*;
import org.vaadin.peholmst.samples.dddwebinar.domain.doctors.License;
import org.vaadin.peholmst.samples.dddwebinar.domain.patients.Insurance;
import org.vaadin.peholmst.samples.dddwebinar.ui.appointments.model.BillingModel;
import org.vaadin.peholmst.samples.dddwebinar.ui.converters.MoneyConverter;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@ViewScope
@SpringComponent
public class BillingSection extends VerticalLayout implements Observer {

    @Autowired
    BillingModel model;

    private BeanItemContainer<LedgerEntry> ledgerContainer;
    private Grid ledger;

    private ClaimSubSection claimSubSection;
    private InvoiceSubSection invoiceSubSection;

    private TextField insuranceClaimAmount;
    private Button sendInsuranceClaim;

    private ObjectProperty<BigDecimal> insurancePaymentAmountProperty;
    private TextField insurancePaymentAmount;
    private Button recordInsurancePayment;

    private TextField invoiceAmount;
    private Button sendInvoice;

    private ObjectProperty<BigDecimal> invoicePaymentAmountProperty;
    private TextField invoicePaymentAmount;
    private Button recordInvoicePayment;

    @PostConstruct
    void init() {
        setSpacing(true);
        setMargin(false);
        setSizeFull();
        Label title = new Label("Billing");
        title.addStyleName(ValoTheme.LABEL_H2);
        addComponent(title);

        ledgerContainer = new BeanItemContainer<>(LedgerEntry.class);
        ledger = new Grid("Ledger", ledgerContainer);
        ledger.setSizeFull();
        ledger.setSelectionMode(Grid.SelectionMode.NONE);
        ledger.setColumns("entryDate", "entryDescription", "entryAmount");
        ledger.getColumn("entryDate").setHeaderCaption("Date");
        ledger.getColumn("entryDescription").setHeaderCaption("Description");
        ledger.getColumn("entryAmount").setHeaderCaption("Amount").setConverter(new MoneyConverter());
        ledger.addFooterRowAt(0).getCell("entryDescription").setText("Outstanding");
        addComponent(ledger);
        setExpandRatio(ledger, 1.0f);

        Accordion receivables = new Accordion();
        receivables.setSizeFull();
        addComponent(receivables);
        setExpandRatio(receivables, 1.0f);

        claimSubSection = new ClaimSubSection();
        receivables.addTab(claimSubSection, "Insurance Claim");

        invoiceSubSection = new InvoiceSubSection();
        receivables.addTab(invoiceSubSection, "Invoice");

        model.addObserver(this); // Same scope, no need to remove afterwards
    }

    @Override
    public void update(Observable o, Object arg) {
        MoneyConverter moneyConverter = new MoneyConverter();

        ledgerContainer.removeAllItems();
        ledgerContainer.addAll(model.getLedger().getEntries());
        ledger.getFooterRow(0).getCell("entryAmount").setText(
            moneyConverter.convertToPresentation(model.getLedger().getOutstanding(), String.class, getLocale()));

        claimSubSection.update();
        invoiceSubSection.update();
    }

    abstract class ReceivableSubSection extends VerticalLayout {

        FormLayout receivable;
        Label issueDate;
        Label amount;
        Label notCreatedYet;

        HorizontalLayout paymentLayout = new HorizontalLayout();
        ObjectProperty<BigDecimal> paymentAmountProperty;
        TextField paymentAmount;
        Button recordPayment;

        ReceivableSubSection() {
            setSpacing(true);
            setMargin(true);
            setSizeFull();

            receivable = new FormLayout();
            receivable.setMargin(false);
            addComponent(receivable);
            setExpandRatio(receivable, 1.0f);

            notCreatedYet = new Label("Not created yet");
            addComponent(notCreatedYet);

            paymentAmountProperty = new ObjectProperty<>(null, BigDecimal.class);
            paymentAmount = new TextField("Payment", paymentAmountProperty);
            paymentAmount.setConverter(new MoneyConverter());

            recordPayment = new Button("Record Payment", evt -> recordPayment(paymentAmountProperty.getValue()));
            paymentLayout = new HorizontalLayout(paymentAmount, recordPayment);
            paymentLayout.setSpacing(true);
            paymentLayout.setComponentAlignment(recordPayment, Alignment.BOTTOM_LEFT);
            addComponent(paymentLayout);

            issueDate = createAndAdd("Issue Date");
            amount = createAndAdd("Amount");
        }

        abstract void recordPayment(BigDecimal payment);

        void updateDateAndAmount(Optional<? extends Receivable> receivable, Optional<Payment> payment) {
            this.receivable.setVisible(receivable.isPresent());
            this.notCreatedYet.setVisible(!this.receivable.isVisible());
            receivable.map(Receivable::getAmount).ifPresent(
                a -> amount.setValue(new MoneyConverter().convertToPresentation(a, String.class, getLocale())));
            receivable.map(Receivable::getIssueDate).ifPresent(d -> issueDate.setValue(d.toString()));
            paymentLayout.setVisible(receivable.isPresent() && !payment.isPresent());
        }

        Label createAndAdd(String caption) {
            Label lbl = new Label();
            lbl.setCaption(caption);
            lbl.setSizeUndefined();
            receivable.addComponent(lbl);
            return lbl;
        }
    }

    class ClaimSubSection extends ReceivableSubSection {

        private Button createClaim;
        private Label insurance;
        private Label license;

        ClaimSubSection() {
            createClaim = new Button("Create and Send Claim", evt -> model.createInsuranceClaim());
            addComponent(createClaim);
            setComponentAlignment(createClaim, Alignment.BOTTOM_RIGHT);

            insurance = createAndAdd("Insurance");
            license = createAndAdd("License");
        }

        void update() {
            updateDateAndAmount(model.getInsuranceClaim(), model.getInsurancePayment());
            model.getInsuranceClaim().map(InsuranceClaim::getLicense).map(License::getDisplayName)
                .ifPresent(license::setValue);
            model.getInsuranceClaim().map(InsuranceClaim::getInsurance).map(Insurance::getDisplayName)
                .ifPresent(insurance::setValue);
            createClaim.setVisible(!model.getInsuranceClaim().isPresent());
        }

        @Override
        void recordPayment(BigDecimal payment) {
            model.recordInsuranceClaimPayment(payment);
        }
    }

    class InvoiceSubSection extends ReceivableSubSection {

        private Button createInvoice;
        private Label recipient;
        private Label dueDate;

        InvoiceSubSection() {
            createInvoice = new Button("Create and Send Invoice", evt -> model.createInvoice());
            addComponent(createInvoice);
            setComponentAlignment(createInvoice, Alignment.BOTTOM_RIGHT);

            recipient = createAndAdd("Recipient");
            dueDate = createAndAdd("Due");
        }

        void update() {
            updateDateAndAmount(model.getInvoice(), model.getInvoicePayment());
            model.getInvoice().map(Invoice::getRecipientName).ifPresent(recipient::setValue);
            model.getInvoice().map(Invoice::getDueDate).ifPresent(d -> dueDate.setValue(d.toString()));
            createInvoice.setVisible(model.getInsurancePayment().isPresent() && !model.getInvoice().isPresent());
        }

        @Override
        void recordPayment(BigDecimal payment) {
            model.recordInvoicePayment(payment);
        }
    }
}
