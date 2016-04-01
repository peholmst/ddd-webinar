package org.vaadin.peholmst.samples.dddwebinar.ui.admin;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.peholmst.samples.dddwebinar.domain.procedures.Procedure;
import org.vaadin.peholmst.samples.dddwebinar.domain.procedures.ProcedureCategory;
import org.vaadin.peholmst.samples.dddwebinar.domain.procedures.ProcedureCategoryRepository;
import org.vaadin.peholmst.samples.dddwebinar.domain.procedures.ProcedureRepository;
import org.vaadin.peholmst.samples.dddwebinar.ui.converters.MoneyConverter;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@SpringView(name = "admin/procedures")
public class ProcedureAdminView extends VerticalLayout implements View {

    @Autowired
    ProcedureRepository procedureRepository;

    @Autowired
    ProcedureCategoryRepository procedureCategoryRepository;

    private BeanItemContainer<Procedure> procedureContainer;
    private Grid procedureGrid;

    @PropertyId("code")
    private TextField code;

    @PropertyId("category")
    private ComboBox procedureCategory;

    @PropertyId("fee")
    private TextField fee;

    private BeanFieldGroup<Procedure> binder = new BeanFieldGroup<>(Procedure.class);
    private HorizontalLayout formLayout;

    @PostConstruct
    void init() {
        setSpacing(true);
        setMargin(true);
        setSizeFull();
        Label label = new Label("Procedures");
        label.addStyleName(ValoTheme.LABEL_H1);
        addComponent(label);

        procedureContainer = new BeanItemContainer<>(Procedure.class);
        procedureContainer.addNestedContainerProperty("category.name");
        procedureGrid = new Grid(procedureContainer);
        procedureGrid.setColumns("code", "fee", "category.name");
        procedureGrid.getColumn("fee").setConverter(new MoneyConverter());
        procedureGrid.getColumn("category.name").setHeaderCaption("Procedure Category");
        procedureGrid.setSizeFull();
        procedureGrid.addSelectionListener(this::select);

        addComponent(procedureGrid);
        setExpandRatio(procedureGrid, 1.0f);

        formLayout = new HorizontalLayout();
        formLayout.setSpacing(true);
        formLayout.setVisible(false);
        addComponent(formLayout);

        code = new TextField("Code");
        formLayout.addComponent(code);

        fee = new TextField("Fee");
        fee.setConverter(new MoneyConverter());
        formLayout.addComponent(fee);

        procedureCategory = new ComboBox("Procedure Category",
            new BeanItemContainer<>(ProcedureCategory.class, procedureCategoryRepository.findAll()));
        procedureCategory.setItemCaptionPropertyId("name");
        procedureCategory.setNullSelectionAllowed(false);
        formLayout.addComponent(procedureCategory);

        Button save = new Button("Save", this::save);
        formLayout.addComponent(save);
        formLayout.setComponentAlignment(save, Alignment.BOTTOM_LEFT);

        binder.bindMemberFields(this);
        refresh();
    }

    private void save(Button.ClickEvent event) {
        try {
            binder.commit();
            Procedure procedure = binder.getItemDataSource().getBean();
            procedureRepository.saveAndFlush(procedure);
            refresh();
        } catch (FieldGroup.CommitException ex) {
            Notification.show(ex.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
    }

    private void select(SelectionEvent event) {
        if (event.getSelected().isEmpty()) {
            formLayout.setVisible(false);
        } else {
            formLayout.setVisible(true);
            binder.setItemDataSource((Procedure) event.getSelected().iterator().next());
        }
    }

    private void refresh() {
        procedureContainer.removeAllItems();
        procedureContainer.addAll(procedureRepository.findAll());
        procedureGrid.select(null);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // NOP
    }
}
