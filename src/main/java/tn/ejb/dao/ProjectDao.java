package tn.ejb.dao;

import jakarta.ejb.Remote;
import tn.ejb.dao.model.Project;

import java.util.List;

/**
 * Created by mohamed.iben-el-abed on 12/22/2023
 */
@Remote
public interface ProjectDao {


    void addOrUpdateProject(Project project);

    Project findProjectByCode(String projectCode);

    void deleteProject(String projectCode);
    List<Project> listProjects();

}
