package com.arshad.taskmanager.repository;

import com.arshad.taskmanager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface TaskRepository extends JpaRepository<Task, Long> {


}
