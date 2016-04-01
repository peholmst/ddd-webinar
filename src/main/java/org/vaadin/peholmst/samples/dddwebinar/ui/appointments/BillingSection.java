package org.vaadin.peholmst.samples.dddwebinar.ui.appointments;

import java.math.BigDecimal;
import java.util.Observable;
import java.util.Observer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.peholmst.samples.dddwebinar.ui.appointments.model.BillingModel;
import org.vaadin.peholmst.samples.dddwebinar.ui.converters.MoneyConverter;

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;

@ViewScope
@SpringComponent
public class BillingSection extends FormLayout implements Observer {

    @Autowired
    BillingModel model;

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

    private TextField outstanding;

    @PostConstruct
    void init() {
        setSpacing(true);
        setMargin(false);
        setSizeFull();

        insuranceClaimAmount = new TextField("Insurance Claim Amount");
        insuranceClaimAmount.setWidth("100%");
        addComponent(insuranceClaimAmount);

        sendInsuranceClaim = new Button("Send Insurance Claim", evt -> model.createInsuranceClaim());
        addComponent(sendInsuranceClaim);

        insurancePaymentAmountProperty = new ObjectProperty<>(null, BigDecimal.class);
        insurancePaymentAmount = new TextField("Insurance Claim Payment");
        insurancePaymentAmount.setConverter(new MoneyConverter());
        insurancePaymentAmount.setPropertyDataSource(insurancePaymentAmountProperty);
        insurancePaymentAmount.setWidth("100%");
        addComponent(insurancePaymentAmount);

        recordInsurancePayment = new Button("Record Payment", evt -> model.recordInsuranceClaimPayment(insurancePaymentAmountProperty.getValue()));
        addComponent(recordInsurancePayment);

        invoiceAmount = new TextField("Invoice Amount");
        invoiceAmount.setWidth("100%");
        addComponent(invoiceAmount);

        sendInvoice = new Button("Send Invoice", evt -> model.createInvoice());
        addComponent(sendInvoice);

        invoicePaymentAmountProperty = new ObjectProperty<>(null, BigDecimal.class);
        invoicePaymentAmount = new TextField("Invoice Payment");
        invoicePaymentAmount.setConverter(new MoneyConverter());
        invoicePaymentAmount.setPropertyDataSource(invoicePaymentAmountProperty);
        invoicePaymentAmount.setWidth("100%");
        addComponent(invoicePaymentAmount);

        recordInvoicePayment = new Button("Record Payment", evt -> model.recordInvoicePayment(invoicePaymentAmountProperty.getValue()));
        addComponent(recordInvoicePayment);

        outstanding = new TextField("Outstanding");
        outstanding.setWidth("100%");
        addComponent(outstanding);

        model.addObserver(this); // Same scope, no need to remove afterwards
    }

    @Override
    public void update(Observable o, Object arg) {
        MoneyConverter moneyConverter = new MoneyConverter();

        insuranceClaimAmount.setReadOnly(false);
        insuranceClaimAmount.setValue(moneyConverter.convertToPresentation(model.getInsuranceClaimAmount().orElse(null),
            String.class, getLocale()));
        insuranceClaimAmount.setReadOnly(true);
        sendInsuranceClaim.setVisible(!model.getInsuranceClaimAmount().isPresent());

        insurancePaymentAmount.setVisible(!sendInsuranceClaim.isVisible());
        insurancePaymentAmountProperty.setValue(model.getInsurancePaymentAmount().orElse(null));
        insurancePaymentAmount.setReadOnly(model.getInsurancePaymentAmount().isPresent());
        recordInsurancePayment.setVisible(insurancePaymentAmount.isVisible() && !insurancePaymentAmount.isReadOnly());

        invoiceAmount.setReadOnly(false);
        invoiceAmount.setValue(moneyConverter.convertToPresentation(model.getInvoiceAmount().orElse(null), String.class, getLocale()));
        invoiceAmount.setReadOnly(true);
        invoiceAmount.setVisible(insurancePaymentAmount.isVisible() && insurancePaymentAmount.isReadOnly());
        sendInvoice.setVisible(invoiceAmount.isVisible() && !model.getInvoiceAmount().isPresent());

        invoicePaymentAmount.setVisible(invoiceAmount.isVisible() && !sendInvoice.isVisible());
        invoicePaymentAmountProperty.setValue(model.getInvoicePaymentAmount().orElse(null));
        invoicePaymentAmount.setReadOnly(model.getInvoicePaymentAmount().isPresent());
        recordInvoicePayment.setVisible(invoicePaymentAmount.isVisible() && !invoicePaymentAmount.isReadOnly());

        outstanding.setReadOnly(false);
        outstanding.setValue(moneyConverter.convertToPresentation(model.getOutstanding(), String.class, getLocale()));
        outstanding.setReadOnly(true);
    }
}
