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

package org.vaadin.example.bookstore.ui.profile;

import java.time.LocalDate;

import org.vaadin.example.bookstore.ui.MainLayout;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "Profile", layout = MainLayout.class)
@PageTitle("Profile")
public class ProfileView extends VerticalLayout {

    public ProfileView() {

        DatePicker datePicker = new DatePicker("Birth date");
        datePicker.setValue(LocalDate.of(1993, 6, 13));

        EmailField email = new EmailField("Email");
        email.setValue("foo@bar.com");

        RadioButtonGroup<String> gender = new RadioButtonGroup<>();
        gender.setLabel("Gender");
        gender.setItems("Male", "Female", "Other");
        gender.setValue("Male");

        NumberField luckyNumber = new NumberField("Lucky number");
        luckyNumber.setValue(10d);
        luckyNumber.setHasControls(true);

        TextArea description = new TextArea("Description");
        description.setValue("Lorem ipsum");

        ComboBox<String> diet = new ComboBox<>("Special diet");
        diet.setItems("Gluten-free", "Vegetarian", "Vegan");
        diet.setClearButtonVisible(true);

        add(datePicker, email, gender, luckyNumber, description, diet);
    }
}
