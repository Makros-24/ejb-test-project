package tn.ejb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import tn.ejb.dao.TaskDao;
import tn.ejb.dao.model.TaskModel;
import tn.ejb.dao.model.Task;

import java.util.List;

@Path("/task")
public class TaskRestController {

    @EJB
    private TaskDao taskDao;

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTaskList() {
        List<Task> tasks = taskDao.listTasks();
        ObjectMapper objectMapper = new ObjectMapper();
        List<TaskModel> taskModels = objectMapper.convertValue(tasks, List.class);
        Response response = Response.ok(taskModels,MediaType.APPLICATION_JSON).build();
        return response;
    }    @GET


    @Path("/list/{projectCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTaskListByProjectCode(@PathParam("projectCode") String projectCode) {
        List<Task> tasks = taskDao.findTaskByProjectCode(projectCode);
        ObjectMapper objectMapper = new ObjectMapper();
        List<TaskModel> taskModels = objectMapper.convertValue(tasks, List.class);
        Response response = Response.ok(taskModels,MediaType.APPLICATION_JSON).build();
        return response;
    }

    @GET
    @Path("/{taskCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTask(@PathParam("taskCode") String taskCode) {
        Task task = taskDao.findTaskByTaskCode(taskCode);
        ObjectMapper objectMapper = new ObjectMapper();
        TaskModel taskModel = objectMapper.convertValue(task, TaskModel.class);
        Response response = Response.ok(taskModel,MediaType.APPLICATION_JSON).build();
        return response;
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addTask(TaskModel taskModel) {
        ObjectMapper objectMapper = new ObjectMapper();
        taskDao.addOrUpdateTask(objectMapper.convertValue(taskModel, Task.class));

        return Response.ok().build();
    }

    @DELETE
    @Path("/{taskCode}")
    public Response deleteTask(@PathParam("taskCode") String taskCode) {
        taskDao.deleteTask(taskCode);
        return Response.ok().build();
    }
}