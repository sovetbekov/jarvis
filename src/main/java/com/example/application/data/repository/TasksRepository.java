package com.example.application.data.repository;

import com.example.application.data.entity.Tasks;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TasksRepository extends JpaRepository<Tasks, Integer> {

    @Query("select c from Tasks c " +
        "where lower(c.task) like lower(concat('%', :searchTerm, '%')) " +
        "or lower(c.project) like lower(concat('%', :searchTerm, '%'))")
    List<Tasks> search(@Param("searchTerm") String searchTerm);
}
