package tn.ejb.dao.impl;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import tn.ejb.dao.ProjectDao;
import tn.ejb.dao.TaskDao;
import tn.ejb.dao.model.Project;
import tn.ejb.ejb.DbConnection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by mohamed.iben-el-abed on 12/22/2023
 */
@Stateless(mappedName = "ProjectDao")
public class ProjectDaoImpl implements ProjectDao {

    @EJB
    private DbConnection dbConnection;
    @EJB
    private TaskDao taskDao;

    @Override
    public void addOrUpdateProject(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("project is null");
        }
        Project projectByCode = findProjectByCode(project.getCode());
        if (projectByCode != null) {
            try (PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement("UPDATE Project SET description = ?, startDate = ? WHERE code = ?")) {
                preparedStatement.setString(1, project.getDescription());
                preparedStatement.setDate(2, new Date(project.getStartDate()!=null?project.getStartDate().getTime():Calendar.getInstance().getTimeInMillis()));
                preparedStatement.setString(3, project.getCode());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return;
        } else {
            try (PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement("INSERT INTO Project (code, description, startDate) VALUES (?, ?, ?)")) {
                preparedStatement.setString(1, project.getCode());
                preparedStatement.setString(2, project.getDescription());
                preparedStatement.setDate(3, new Date(project.getStartDate()!=null?project.getStartDate().getTime():Calendar.getInstance().getTimeInMillis()));
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Project findProjectByCode(String projectCode){
        Project project = null;
        try (PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement("SELECT * FROM Project WHERE code = ?")) {
            preparedStatement.setString(1, projectCode);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                project = new Project();
                project.setCode(resultSet.getString("code"));
                project.setDescription(resultSet.getString("description"));
                project.setStartDate(resultSet.getDate("startDate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return project;
    }

    @Override
    public void deleteProject(String projectCode) {
        try (PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement("DELETE FROM Task WHERE project_code = ?")) {
            preparedStatement.setString(1, projectCode);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement("DELETE FROM Project WHERE code = ?")) {
            preparedStatement.setString(1, projectCode);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Project> listProjects() {
        List<Project> projects = new ArrayList<>();
        try (PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement("SELECT * FROM Project")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Project project = new Project();
                project.setCode(resultSet.getString("code"));
                project.setDescription(resultSet.getString("description"));
                project.setStartDate(resultSet.getDate("startDate"));
                project.setTasks(new ArrayList<>());
                taskDao.findTaskByProjectCode(project.getCode()).forEach(task -> project.addTaskToProject(task));
                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }
}
