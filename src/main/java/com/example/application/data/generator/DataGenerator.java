package com.example.application.data.generator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.example.application.data.entity.Company;
import com.example.application.data.entity.Tasks;
import com.example.application.data.entity.Status;
import com.example.application.data.repository.CompanyRepository;
import com.example.application.data.repository.TasksRepository;
import com.example.application.data.repository.StatusRepository;
import com.vaadin.flow.spring.annotation.SpringComponent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.vaadin.artur.exampledata.DataType;
import org.vaadin.artur.exampledata.ExampleDataGenerator;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(TasksRepository tasksRepository, CompanyRepository companyRepository,
                                      StatusRepository statusRepository) {

        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (tasksRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;

            logger.info("Generating demo data");
            ExampleDataGenerator<Company> companyGenerator = new ExampleDataGenerator<>(Company.class,
                    LocalDateTime.now());

            companyGenerator.setData(Company::setName, DataType.OCCUPATION);
            List<Company> companies = companyRepository.saveAll(companyGenerator.create(5, seed));




            List<Status> statuses = statusRepository
                    .saveAll(Stream.of("need to begin", "in progress", "finishing")
                            .map(Status::new).collect(Collectors.toList()));

            logger.info("... generating 50 Tasks entities...");
            ExampleDataGenerator<Tasks> contactGenerator = new ExampleDataGenerator<>(Tasks.class,
                    LocalDateTime.now());
            contactGenerator.setData(Tasks::setTask, DataType.BOOK_GENRE);
            contactGenerator.setData(Tasks::setDetails, DataType.BOOK_TITLE);




//            Random r = new Random(seed);
//            List<Tasks> contacts = contactGenerator.create(0, seed).stream().peek(contact -> {
//                contact.setCompany(companies.get(r.nextInt(companies.size())));
//                contact.setStatus(statuse s.get(r.nextInt(statuses.size())));
//            }).collect(Collectors.toList());

//            tasksRepository.saveAll(contacts);

            logger.info("Generated demo data");
        };
    }

}
