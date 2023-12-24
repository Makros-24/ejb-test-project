package tn.ejb.dao.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by mohamed.iben-el-abed on 12/22/2023
 */

@Data
public class ProjectModel {
    private String code;
    private String description;
    private Date startDate;
    private List<TaskModel> tasks;

}
