package com.example.application.data.service;

import com.example.application.data.entity.Company;
import com.example.application.data.entity.Tasks;
import com.example.application.data.entity.Status;
import com.example.application.data.repository.CompanyRepository;
import com.example.application.data.repository.TasksRepository;
import com.example.application.data.repository.StatusRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrmService {

    private final TasksRepository tasksRepository;
    private final CompanyRepository companyRepository;
    private final StatusRepository statusRepository;

    public CrmService(TasksRepository tasksRepository,
                      CompanyRepository companyRepository,
                      StatusRepository statusRepository) {
        this.tasksRepository = tasksRepository;
        this.companyRepository = companyRepository;
        this.statusRepository = statusRepository;
    }

    public List<Tasks> findAllContacts(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return tasksRepository.findAll(Sort.by(Sort.Direction.ASC, "priority"));

        } else {
            return tasksRepository.search(stringFilter);
        }
    }

    public long countContacts() {
        return tasksRepository.count();
    }

    public void deleteContact(Tasks tasks) {
        tasksRepository.delete(tasks);
    }

    public void saveTask(Tasks tasks) {
        if (tasks == null) {
            System.err.println("Tasks is null. Are you sure you have connected your form to the application?");
            return;
        }
        tasksRepository.save(tasks);
    }

    public List<Company> findAllCompanies() {
        return companyRepository.findAll();
    }

    public List<Status> findAllStatuses(){
        return statusRepository.findAll();
    }
}
