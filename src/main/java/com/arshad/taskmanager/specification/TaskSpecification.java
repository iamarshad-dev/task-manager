package com.arshad.taskmanager.specification;

import com.arshad.taskmanager.entity.Task;
import com.arshad.taskmanager.entity.TaskPriority;
import com.arshad.taskmanager.entity.TaskStatus;
import org.springframework.data.jpa.domain.Specification;

public final class TaskSpecification {

    private TaskSpecification() {
    }

    public static Specification<Task> hasStatus(TaskStatus status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.equal(
                    root.get("status"),
                    status
            );
        };
    }

    public static Specification<Task> hasPriority(TaskPriority priority) {
        return (root, query, criteriaBuilder) -> {
            if (priority == null){
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.equal(
                    root.get("priority"),
                    priority
            );
        };
    }

}
