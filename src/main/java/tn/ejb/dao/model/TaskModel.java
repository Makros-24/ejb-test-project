package tn.ejb.dao.model;

import lombok.Data;

import java.util.Date;

/**
 * Created by mohamed.iben-el-abed on 12/22/2023
 */

@Data
public class TaskModel {

    private String code;
    private String description;
    private Date startDate;
    private Date endDate;
    private String projectCode;

}
