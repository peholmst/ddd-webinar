package org.vaadin.peholmst.samples.dddwebinar.ui.admin;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.peholmst.samples.dddwebinar.domain.licensing.LicenseTypeRepository;
import org.vaadin.peholmst.samples.dddwebinar.domain.procedures.Procedure;
import org.vaadin.peholmst.samples.dddwebinar.domain.procedures.ProcedureCategory;
import org.vaadin.peholmst.samples.dddwebinar.domain.procedures.ProcedureCategoryRepository;
import org.vaadin.peholmst.samples.dddwebinar.ui.converters.LicenseTypeMapConverter;

import javax.annotation.PostConstruct;

@SpringView(name = "admin/procedureCategories")
public class ProcedureCategoryAdminView extends VerticalLayout implements View {

    @Autowired
    ProcedureCategoryRepository procedureCategoryRepository;

    @Autowired
    LicenseTypeRepository licenseTypeRepository;

    private BeanItemContainer<ProcedureCategory> procedureCategoryContainer;
    private Grid procedureCategoryGrid;

    @PropertyId(("name"))
    private TextField name;

    @PropertyId("licenseTypes")
    private LicenseTypeMapField licenseTypes;

    private BeanFieldGroup<ProcedureCategory> binder = new BeanFieldGroup<>(ProcedureCategory.class);
    private HorizontalLayout formLayout;

    @PostConstruct
    void init() {
        setSpacing(true);
        setMargin(true);
        setSizeFull();
        Label label = new Label("Procedure Categories");
        label.addStyleName(ValoTheme.LABEL_H1);
        addComponent(label);

        procedureCategoryContainer = new BeanItemContainer<>(ProcedureCategory.class);
        procedureCategoryGrid = new Grid(procedureCategoryContainer);
        procedureCategoryGrid.setColumns("name", "licenseTypes");
        procedureCategoryGrid.getColumn("licenseTypes").setConverter(new LicenseTypeMapConverter());
        procedureCategoryGrid.setSizeFull();
        procedureCategoryGrid.addSelectionListener(this::select);

        addComponent(procedureCategoryGrid);
        setExpandRatio(procedureCategoryGrid, 1.0f);

        formLayout = new HorizontalLayout();
        formLayout.setSpacing(true);
        formLayout.setVisible(false);
        addComponent(formLayout);

        name = new TextField("Category Name");
        formLayout.addComponent(name);
        formLayout.setComponentAlignment(name, Alignment.TOP_LEFT);

        licenseTypes = new LicenseTypeMapField("License Types");
        licenseTypes.setSelectable(licenseTypeRepository.findAll());
        formLayout.addComponent(licenseTypes);

        Button save = new Button("Save", this::save);
        formLayout.addComponent(save);
        formLayout.setComponentAlignment(save, Alignment.BOTTOM_LEFT);

        binder.bindMemberFields(this);
        refresh();
    }

    private void save(Button.ClickEvent event) {
        // TODO
    }

    private void select(SelectionEvent event) {
        if (event.getSelected().isEmpty()) {
            formLayout.setVisible(false);
        } else {
            formLayout.setVisible(true);
            binder.setItemDataSource((ProcedureCategory) event.getSelected().iterator().next());
        }
    }

    private void refresh() {
        procedureCategoryContainer.removeAllItems();
        procedureCategoryContainer.addAll(procedureCategoryRepository.findAll());
        procedureCategoryGrid.select(null);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        // NOP
    }
}
