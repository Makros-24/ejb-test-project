package tn.ejb.dao.model;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mohamed.iben-el-abed on 12/22/2023
 */

@Data
public class Project implements Serializable {
    private String code;
    private String description;
    private Date startDate;
    private List<Task> tasks;

    public void addTaskToProject(Task task) {
        if (tasks == null) {
            tasks = new ArrayList<>();
        }
        tasks.add(task);
    }

    public void removeTaskFromProject(Task task) {
        tasks.remove(task);
    }

}
