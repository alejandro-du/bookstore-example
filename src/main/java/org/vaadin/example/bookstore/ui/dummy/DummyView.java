/*
 * Copyright 2000-2020 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.vaadin.example.bookstore.ui.dummy;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.vaadin.example.bookstore.ui.MainLayout;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.Crosshair;
import com.vaadin.flow.component.charts.model.ListSeries;
import com.vaadin.flow.component.charts.model.Tooltip;
import com.vaadin.flow.component.charts.model.XAxis;
import com.vaadin.flow.component.charts.model.YAxis;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.crud.CrudEditorPosition;
import com.vaadin.flow.component.crud.CrudFilter;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.gridpro.GridPro;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.richtexteditor.RichTextEditor;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "Components", layout = MainLayout.class)
@PageTitle("Components")
public class DummyView extends VerticalLayout {

    public static class Item {
        private String name;
        private boolean married;

        public Item(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isMarried() {
            return married;
        }

        public void setMarried(boolean married) {
            this.married = married;
        }
    }

    private List<Item> items = Arrays.asList(new Item("foo"), new Item("bar"),
            new Item("baz"));

    public DummyView() {
        add(new H1("Dummy components for testing RTL"));

        Tabs tabs = new Tabs(new Tab("First tab"), new Tab("Second tab"),
                new Tab("Third tab"));

        Accordion accordion = new Accordion();
        accordion.add("foo", new Paragraph("Lorem ipsum"));
        accordion.add("bar", new Paragraph("Lorem ipsum"));

        Upload upload = new Upload();

        MenuBar menuBar = new MenuBar();
        MenuItem menuItem = menuBar.addItem("foo");
        menuBar.addItem("bar");
        MenuItem subMenuItem = menuItem.getSubMenu().addItem("foo");
        menuItem.getSubMenu().addItem("bar");
        subMenuItem.getSubMenu().addItem("foo");
        subMenuItem.getSubMenu().addItem("bar");

        TimePicker timePicker = new TimePicker("time");
        timePicker.setValue(LocalTime.now());

        ProgressBar progressBar = new ProgressBar();
        progressBar.setValue(0.3);

        TreeGrid<Item> treeGrid = new TreeGrid<>();
        treeGrid.setItems(
                IntStream.range(0, 10).mapToObj(i -> new Item("Item " + i)),
                item -> {
                    if (item.getName().length() < 10) {
                        return IntStream.range(0, 5)
                                .mapToObj(i -> new Item(item.getName() + i));
                    } else {
                        return Stream.empty();
                    }
                });
        treeGrid.addHierarchyColumn(item -> item.getName());

        Button openNotification = new Button("Open notification",
                e -> Notification.show("At position BOTTOM_START", 2000,
                        Position.BOTTOM_START));
        openNotification.getElement().setProperty("____header", "Notification");

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.addToPrimary(new Paragraph("Primary content"));
        splitLayout.addToSecondary(new Paragraph("Secondary content"));

        FormLayout formLayout = createFormLayout();

        RichTextEditor richTextEditor = new RichTextEditor();

        GridPro<Item> gridPro = new GridPro<>();
        gridPro.setItems(items);
        gridPro.addEditColumn(Item::getName)
                .text((item, val) -> item.setName(val)).setHeader("name");
        gridPro.addEditColumn(Item::isMarried)
                .checkbox((item, val) -> item.setMarried(val))
                .setHeader("married");

        ConfirmDialog confirmDialog = new ConfirmDialog("Unsaved changes",
                "Do you want to save or discard your changes before navigating away?",
                "Save", e -> {
                }, "Discard", e -> {
                }, "Cancel", e -> {
                });
        Button openConfirmDialog = new Button("Open confirm dialog");
        openConfirmDialog.addClickListener(event -> confirmDialog.open());
        openConfirmDialog.getElement().setProperty("____header",
                "ConfirmDialog");

        Chart chart = createChart();

        Crud crud = createCrud();

        addComponents(tabs, accordion, upload, menuBar, timePicker, progressBar,
                treeGrid, openNotification, splitLayout, formLayout,
                richTextEditor, gridPro, openConfirmDialog, chart, crud);
    }

    private void addComponents(Component... components) {
        Stream.of(components).forEach(c -> {
            String header = c.getElement().getProperty("____header", "");
            if (header.length() == 0) {
                header = c.getClass().getSimpleName();
            }
            add(new H3(header));
            add(c);
            c.getElement().getStyle().set("margin", "50px 0");
            add(new Hr());
        });
    }

    private FormLayout createFormLayout() {
        FormLayout layoutWithFormItems = new FormLayout();

        TextField firstName = new TextField();
        firstName.setPlaceholder("John");
        layoutWithFormItems.addFormItem(firstName, "First name");

        TextField lastName = new TextField();
        lastName.setPlaceholder("Doe");
        layoutWithFormItems.addFormItem(lastName, "Last name");

        return layoutWithFormItems;
    }

    private Chart createChart() {
        Chart chart = new Chart();

        Configuration configuration = chart.getConfiguration();
        configuration.setTitle("Monthly Average Rainfall");
        configuration.setSubTitle("Source: WorldClimate.com");
        chart.getConfiguration().getChart().setType(ChartType.COLUMN);

        configuration.addSeries(new ListSeries("Tokyo", 49.9, 71.5, 106.4,
                129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4));
        configuration.addSeries(new ListSeries("New York", 83.6, 78.8, 98.5,
                93.4, 106.0, 84.5, 105.0, 104.3, 91.2, 83.5, 106.6, 92.3));
        configuration.addSeries(new ListSeries("London", 48.9, 38.8, 39.3, 41.4,
                47.0, 48.3, 59.0, 59.6, 52.4, 65.2, 59.3, 51.2));
        configuration.addSeries(new ListSeries("Berlin", 42.4, 33.2, 34.5, 39.7,
                52.6, 75.5, 57.4, 60.4, 47.6, 39.1, 46.8, 51.1));

        XAxis x = new XAxis();
        x.setCrosshair(new Crosshair());
        x.setCategories("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
                "Sep", "Oct", "Nov", "Dec");
        configuration.addxAxis(x);

        YAxis y = new YAxis();
        y.setMin(0);
        y.setTitle("Rainfall (mm)");
        configuration.addyAxis(y);

        Tooltip tooltip = new Tooltip();
        tooltip.setShared(true);
        configuration.setTooltip(tooltip);

        return chart;
    }

    private Crud createCrud() {

        Crud<Item> crud = new Crud<>(Item.class, createCrudItemEditor());

        AbstractBackEndDataProvider<Item, CrudFilter> dataProvider = new AbstractBackEndDataProvider<Item, CrudFilter>() {
            @Override
            protected Stream<Item> fetchFromBackEnd(
                    Query<Item, CrudFilter> query) {
                return items.stream();
            }

            @Override
            protected int sizeInBackEnd(Query<Item, CrudFilter> query) {
                return items.size();
            }
        };
        crud.setDataProvider(dataProvider);
        crud.setEditorPosition(CrudEditorPosition.ASIDE);

        return crud;
    }

    private CrudEditor<Item> createCrudItemEditor() {
        TextField name = new TextField("name");
        Checkbox married = new Checkbox("married");
        FormLayout form = new FormLayout(name, married);

        Binder<Item> binder = new Binder<>(Item.class);
        binder.bind(name, Item::getName, Item::setName);
        binder.bind(married, Item::isMarried, Item::setMarried);

        return new BinderCrudEditor<>(binder, form);
    }

}
