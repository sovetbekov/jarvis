package com.example.application.views.list;

import com.example.application.data.entity.Company;
import com.example.application.data.entity.Tasks;
import com.example.application.data.entity.Status;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class ContactForm extends FormLayout {
  private Tasks tasks;

  TextField task = new TextField("Task");
  TextArea details = new TextArea("Details");

  TextField project = new TextField("Project");
  TextField priority = new TextField("Priority");


  ComboBox<Status> status = new ComboBox<>("Status");
  Binder<Tasks> binder = new BeanValidationBinder<>(Tasks.class);

  Button save = new Button("Save");
  Button completed = new Button("Completed");
  Button close = new Button("Cancel");

  public ContactForm(List<Company> companies, List<Status> statuses) {
    addClassName("tasks-form");
    binder.bindInstanceFields(this);

    status.setItems(statuses);
    status.setItemLabelGenerator(Status::getName);
    add(task,
            details,
        priority,
        project,
        status,
        createButtonsLayout());
  }

  private HorizontalLayout createButtonsLayout() {
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    completed.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
    close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

    save.addClickShortcut(Key.ENTER);
    close.addClickShortcut(Key.ESCAPE);

    save.addClickListener(event -> validateAndSave());
    completed.addClickListener(event -> fireEvent(new DeleteEvent(this, tasks)));
    close.addClickListener(event -> fireEvent(new CloseEvent(this)));


    binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

    return new HorizontalLayout(save, completed, close);
  }

  public void setTask(Tasks tasks) {
    this.tasks = tasks;
    binder.readBean(tasks);
  }

  private void validateAndSave() {
    try {
      binder.writeBean(tasks);
      fireEvent(new SaveEvent(this, tasks));
    } catch (ValidationException e) {
      e.printStackTrace();
    }
  }

  // Events
  public static abstract class TasksFormEvent extends ComponentEvent<ContactForm> {
    private Tasks tasks;

    protected TasksFormEvent(ContactForm source, Tasks tasks) {
      super(source, false);
      this.tasks = tasks;
    }

    public Tasks getTask() {
      return tasks;
    }
  }

  public static class SaveEvent extends TasksFormEvent {
    SaveEvent(ContactForm source, Tasks tasks) {
      super(source, tasks);
    }
  }

  public static class DeleteEvent extends TasksFormEvent {
    DeleteEvent(ContactForm source, Tasks tasks) {
      super(source, tasks);
    }

  }

  public static class CloseEvent extends TasksFormEvent {
    CloseEvent(ContactForm source) {
      super(source, null);
    }
  }

  public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                ComponentEventListener<T> listener) {
    return getEventBus().addListener(eventType, listener);
  }
}