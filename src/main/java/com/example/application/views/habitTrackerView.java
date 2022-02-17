package com.example.application.views;


import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;

@Route(value = "Habit", layout = MainLayout.class)
@PageTitle("Habit tracker | jarvis.")
@PermitAll
public class habitTrackerView extends VerticalLayout {

}