package com.example.application.views.list;

import com.example.application.data.entity.Tasks;
import com.example.application.data.service.CrmService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.security.PermitAll;


@Component
@Scope("prototype")
@Route(value="", layout = MainLayout.class)
@PageTitle("todolist | Jarvis")
@PermitAll
public class ListView extends VerticalLayout {
    Grid<Tasks> grid = new Grid<>(Tasks.class);
    TextField filterText = new TextField();
    ContactForm form;
    CrmService service;

    public ListView(CrmService service) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        form = new ContactForm(service.findAllCompanies(), service.findAllStatuses());
        form.setWidth("25em");
        form.addListener(ContactForm.SaveEvent.class, this::saveTask);
        form.addListener(ContactForm.DeleteEvent.class, this::deleteTask);
        form.addListener(ContactForm.CloseEvent.class, e -> closeEditor());

        FlexLayout content = new FlexLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.setFlexShrink(0, form);
        content.addClassNames("content", "gap-m");
        content.setSizeFull();

        add(getToolbar(), content);
        updateList();
        closeEditor();
        grid.asSingleSelect().addValueChangeListener(event ->
            editTask(event.getValue()));
    }

    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setColumns("task", "details", "priority", "project");
        grid.addColumn(contact -> contact.getStatus().getName()).setHeader("Status");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addTaskButton = new Button("Add task");
        addTaskButton.addClickListener(click -> addTask());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addTaskButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void saveTask(ContactForm.SaveEvent event) {
        service.saveTask(event.getTask());
        updateList();
        closeEditor();
    }

    private void deleteTask(ContactForm.DeleteEvent event) {
        service.deleteContact(event.getTask());
        updateList();
        closeEditor();
    }

    public void editTask(Tasks tasks) {
        if (tasks == null) {
            closeEditor();
        } else {
            form.setTask(tasks);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    void addTask() {
        grid.asSingleSelect().clear();
        editTask(new Tasks());
    }

    private void closeEditor() {
        form.setTask(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(service.findAllContacts(filterText.getValue()));
    }


}
