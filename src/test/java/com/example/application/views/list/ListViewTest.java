package com.example.application.views.list;

import com.example.application.data.entity.Tasks;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ListViewTest {

    @Autowired
    private ListView listView;

    @Test
    public void formShownWhenContactSelected() {
        Grid<Tasks> grid = listView.grid;
        Tasks firstTasks = getFirstItem(grid);

        ContactForm form = listView.form;

        Assert.assertFalse(form.isVisible());
        grid.asSingleSelect().setValue(firstTasks);
        Assert.assertTrue(form.isVisible());
        Assert.assertEquals(firstTasks.getTask(), form.task.getValue());
    }
    private Tasks getFirstItem(Grid<Tasks> grid) {
        return( (ListDataProvider<Tasks>) grid.getDataProvider()).getItems().iterator().next();
    }
}