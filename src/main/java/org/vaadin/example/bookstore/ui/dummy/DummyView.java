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
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.vaadin.example.bookstore.ui.MainLayout;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.contextmenu.MenuItem;
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
        gridPro.setItems(new Item("foo"), new Item("bar"), new Item("baz"));
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

        addComponents(tabs, accordion, upload, menuBar, timePicker, progressBar,
                treeGrid, openNotification, splitLayout, formLayout,
                richTextEditor, gridPro, openConfirmDialog);
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

}
