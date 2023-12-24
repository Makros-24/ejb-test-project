package tn.ejb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import tn.ejb.dao.ProjectDao;
import tn.ejb.dao.model.Project;
import tn.ejb.dao.model.ProjectModel;

import java.util.List;

/**
 * Created by mohamed.iben-el-abed on 12/22/2023
 */

@Path("/project")
public class ProjectRestController {

    @EJB
    private ProjectDao projectDao;

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProjectList() {
        List<Project> projects = projectDao.listProjects();
        ObjectMapper objectMapper = new ObjectMapper();
        List<ProjectModel> projectModels = objectMapper.convertValue(projects, List.class);
        Response response = Response.ok(projectModels,MediaType.APPLICATION_JSON).build();
        return response;
    }

    @GET
    @Path("/{projectCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProjectList(@PathParam("projectCode") String projectCode) {
        Project project = projectDao.findProjectByCode(projectCode);
        ObjectMapper objectMapper = new ObjectMapper();
        ProjectModel projectModel = objectMapper.convertValue(project, ProjectModel.class);
        Response response = Response.ok(projectModel,MediaType.APPLICATION_JSON).build();
        return response;
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addProject(ProjectModel projectModel) {
        ObjectMapper objectMapper = new ObjectMapper();
        projectDao.addOrUpdateProject(objectMapper.convertValue(projectModel, Project.class));
        return Response.ok().build();
    }

    @DELETE
    @Path("/{projectCode}")
    public Response deleteProject(@PathParam("projectCode") String projectCode) {
        projectDao.deleteProject(projectCode);
        return Response.ok().build();
    }
}
