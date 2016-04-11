package org.vaadin.peholmst.samples.dddwebinar.ui.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.vaadin.peholmst.samples.dddwebinar.domain.licensing.LicenseType;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.*;

/**
 * Please note! This custom field is not of production quality.
 */
public class LicenseTypeMapField extends CustomField<Map> {

    private Grid selectable;
    private IndexedContainer selectableContainer;
    private Grid selected;
    private IndexedContainer selectedContainer;
    private Button add;
    private Button remove;
    private List<LicenseType> selectableTypes;

    public LicenseTypeMapField(String caption) {
        this();
        setCaption(caption);
    }

    public LicenseTypeMapField() {
        selectableContainer = new IndexedContainer();
        selectableContainer.addContainerProperty("name", String.class, "");

        selectedContainer = new IndexedContainer();
        selectedContainer.addContainerProperty("name", String.class, "");
        selectedContainer.addContainerProperty("rank", Integer.class, 1);
    }

    @SuppressWarnings("unchecked")
    public void setSelectable(List<LicenseType> licenseTypes) {
        this.selectableTypes = licenseTypes;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void setInternalValue(Map newValue) {
        if (newValue != null) {
            newValue = new HashMap<>(newValue);
        }
        super.setInternalValue(newValue);
        selectedContainer.removeAllItems();
        selectableContainer.removeAllItems();
        selectableTypes.forEach(this::addSelectableItem);
        if (newValue != null) {
            ((Map<LicenseType, Integer>) newValue).entrySet().forEach(e -> {
                addSelectedItem(e.getKey(), e.getValue());
                removeSelectableItem(e.getKey());
            });
        }
    }

    @Override
    protected Map<LicenseType, Integer> getInternalValue() {
        return super.getInternalValue();
    }

    @SuppressWarnings("unchecked")
    private void addSelectedItem(LicenseType licenseType, int rank) {
        Item item = selectedContainer.addItem(licenseType);
        item.getItemProperty("name").setValue(licenseType.getName());
        item.getItemProperty("rank").setValue(rank);
        ((ValueChangeNotifier) item.getItemProperty("rank")).addValueChangeListener(event -> {
            Integer value = (Integer) event.getProperty().getValue();
            if (value == null) {
                value = 1;
            }
            getInternalValue().put(licenseType, value);
        });
    }

    private void addSelectableItem(LicenseType licenseType) {
        Item item = selectableContainer.addItem(licenseType);
        item.getItemProperty("name").setValue(licenseType.getName());
    }

    private void removeSelectedItem(LicenseType licenseType) {
        getInternalValue().remove(licenseType);
        selectedContainer.removeItem(licenseType);
    }

    private void removeSelectableItem(LicenseType licenseType) {
        selectableContainer.removeItem(licenseType);
    }

    @Override
    protected Component initContent() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeUndefined();
        layout.setSpacing(true);

        selectable = new Grid(selectableContainer);
        selectable.setWidth("300px");
        selectable.setHeight("200px");

        layout.addComponent(selectable);

        VerticalLayout buttons = new VerticalLayout();
        buttons.setSpacing(true);

        add = new Button("Add", this::add);

        buttons.addComponent(add);

        remove = new Button("Remove", this::remove);
        buttons.addComponent(remove);

        layout.addComponent(buttons);

        selected = new Grid(selectedContainer);
        selected.setWidth("330px");
        selected.setHeight("200px");
        selected.getColumn("name").setEditable(false);
        selected.setEditorEnabled(true);
        selected.setEditorBuffered(false);
        layout.addComponent(selected);

        return layout;
    }

    private void add(Button.ClickEvent event) {
        LicenseType licenseType = (LicenseType) selectable.getSelectedRow();
        if (licenseType != null) {
            addSelectedItem(licenseType, 1);
            removeSelectableItem(licenseType);
            selected.select(null);
            selectable.select(null);
        }
    }

    private void remove(Button.ClickEvent event) {
        LicenseType licenseType = (LicenseType) selected.getSelectedRow();
        if (licenseType != null) {
            removeSelectedItem(licenseType);
            addSelectableItem(licenseType);
            selected.select(null);
            selectable.select(null);
        }
    }

    @Override
    public Class<? extends Map> getType() {
        return Map.class;
    }
}
