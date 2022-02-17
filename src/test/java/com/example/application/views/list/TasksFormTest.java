package com.example.application.views.list;

import com.example.application.data.entity.Company;
import com.example.application.data.entity.Tasks;
import com.example.application.data.entity.Status;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class TasksFormTest {
    private List<Company> companies;
    private List<Status> statuses;
    private Tasks marcUsher;
    private Company company1;
    private Company company2;
    private Status status1;
    private Status status2;

    @Before
    public void setupData() {
        companies = new ArrayList<>();
        company1 = new Company();
        company1.setName("Vaadin Ltd");
        company2 = new Company();
        company2.setName("IT Mill");
        companies.add(company1);
        companies.add(company2);

        statuses = new ArrayList<>();
        status1 = new Status();
        status1.setName("Status 1");
        status2 = new Status();
        status2.setName("Status 2");
        statuses.add(status1);
        statuses.add(status2);

        marcUsher = new Tasks();
        marcUsher.setTask("Go to the gym");
        marcUsher.setDetails("biceps & back");
        marcUsher.setStatus(status1);
        marcUsher.setCompany(company2);
    }

    @Test
    public void formFieldsPopulated() {
        ContactForm form = new ContactForm(companies, statuses);
        form.setTask(marcUsher);
        Assert.assertEquals("Go to the gym", form.task.getValue());
        Assert.assertEquals("biceps&back", form.details.getValue());
       // Assert.assertEquals(company2, form.company.getValue());
        Assert.assertEquals(status1, form.status.getValue());
    }

    @Test
    public void saveEventHasCorrectValues() {
        ContactForm form = new ContactForm(companies, statuses);
        Tasks tasks = new Tasks();
        form.setTask(tasks);
        form.task.setValue("John");
        form.details.setValue("Doe");
      //  form.company.setValue(company1);
        form.status.setValue(status2);

        AtomicReference<Tasks> savedContactRef = new AtomicReference<>(null);
        form.addListener(ContactForm.SaveEvent.class, e -> {
            savedContactRef.set(e.getTask());
        });
        form.save.click();
        Tasks savedTasks = savedContactRef.get();

        Assert.assertEquals("John", savedTasks.getTask());
        Assert.assertEquals("Doe", savedTasks.getDetails());
        Assert.assertEquals(company1, savedTasks.getCompany());
        Assert.assertEquals(status2, savedTasks.getStatus());
    }
}