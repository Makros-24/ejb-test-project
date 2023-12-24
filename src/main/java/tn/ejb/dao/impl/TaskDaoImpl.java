package tn.ejb.dao.impl;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import tn.ejb.dao.TaskDao;
import tn.ejb.dao.model.Task;
import tn.ejb.ejb.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Stateless
public class TaskDaoImpl implements TaskDao {

    @EJB
    private DbConnection dbConnection;
    @Override
    public void addOrUpdateTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("project is null");
        }
        Task taskByCode = findTaskByTaskCode(task.getCode());
        if (taskByCode != null) {
            try (PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement("UPDATE task SET description = ?, startDate = ? , endDate = ? WHERE code = ?")) {
                preparedStatement.setString(1, task.getDescription());
                preparedStatement.setDate(2, new Date(task.getStartDate()!=null?task.getStartDate().getTime(): Calendar.getInstance().getTimeInMillis()));
                preparedStatement.setDate(3, new Date(task.getEndDate()!=null?task.getStartDate().getTime(): Calendar.getInstance().getTimeInMillis()));
                preparedStatement.setString(4, task.getCode());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try (PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement("INSERT INTO task (code, description, startDate, endDate, projectCode) VALUES (?, ?, ?, ?, ?)")) {
                preparedStatement.setString(1, task.getCode());
                preparedStatement.setString(2, task.getDescription());
                preparedStatement.setDate(3, new Date(task.getStartDate()!=null?task.getStartDate().getTime():Calendar.getInstance().getTimeInMillis()));
                preparedStatement.setDate(4, new Date(task.getEndDate()!=null?task.getEndDate().getTime():Calendar.getInstance().getTimeInMillis()));
                preparedStatement.setString(5, task.getProjectCode());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Task findTaskByTaskCode(String taskCode) {
        Task task = null;
        try (PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement("SELECT * FROM task WHERE code = ?")) {
            preparedStatement.setString(1, taskCode);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                task = new Task();
                task.setCode(resultSet.getString("code"));
                task.setDescription(resultSet.getString("description"));
                task.setStartDate(resultSet.getDate("startDate"));
                task.setEndDate(resultSet.getDate("endDate"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return task;
    }

    @Override
    public void deleteTask(String taskCode) {
        try {
            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement("DELETE FROM task WHERE code = ?");
            preparedStatement.setString(1, taskCode);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Task> findTaskByProjectCode(String projectCode) {
        List<Task> tasks = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement("SELECT * FROM task WHERE projectCode = ?");
            preparedStatement.setString(1, projectCode);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Task task = new Task();
                task.setCode(resultSet.getString("code"));
                task.setDescription(resultSet.getString("description"));
                task.setStartDate(resultSet.getDate("startDate"));
                task.setEndDate(resultSet.getDate("endDate"));

                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    @Override
    public List<Task> listTasks() {
        List<Task> tasks = new ArrayList<>();
        try {
            Statement statement = dbConnection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM task");
            while (resultSet.next()) {
                Task task = new Task();
                task.setCode(resultSet.getString("code"));
                task.setDescription(resultSet.getString("description"));
                task.setStartDate(resultSet.getDate("startDate"));
                task.setEndDate(resultSet.getDate("endDate"));

                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }
}