package tn.ejb.dao;

import jakarta.ejb.Remote;
import tn.ejb.dao.model.Task;

import java.util.List;

@Remote
public interface TaskDao {

    void addOrUpdateTask(Task task);

    Task findTaskByTaskCode(String taskCode);
    List<Task> findTaskByProjectCode(String taskCode);

    void deleteTask(String taskCode);
    List<Task> listTasks();

}