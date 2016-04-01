package org.vaadin.peholmst.samples.dddwebinar.ui.admin;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.ui.*;
import org.vaadin.peholmst.samples.dddwebinar.domain.licensing.LicenseType;

import java.util.List;
import java.util.Map;

public class LicenseTypeMapField extends CustomField<Map> {

    private Grid selectable;
    private IndexedContainer selectableContainer;
    private Grid selected;
    private IndexedContainer selectedContainer;
    private Button add;
    private Button remove;

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
        selectableContainer.removeAllItems();
        licenseTypes.forEach(lt -> selectableContainer.addItem(lt).getItemProperty("name").setValue(lt.getName()));
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void setInternalValue(Map newValue) {
        super.setInternalValue(newValue);
        selectedContainer.removeAllItems();
        if (newValue != null) {
            ((Map<LicenseType, Integer>) newValue).entrySet()
                .forEach(e -> addSelectedItem(e.getKey(), e.getValue()));
        }
    }

    @SuppressWarnings("unchecked")
    private void addSelectedItem(LicenseType licenseType, int rank) {
        Item item = selectedContainer.addItem(licenseType);
        item.getItemProperty("name").setValue(licenseType.getName());
        item.getItemProperty("rank").setValue(rank);
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

        selected = new Grid(selectedContainer);
        selected.setWidth("330px");
        selected.setHeight("200px");
        selected.getColumn("rank").setEditable(true);
        layout.addComponent(selected);

        return layout;
    }

    @Override
    public Class<? extends Map> getType() {
        return Map.class;
    }
}
