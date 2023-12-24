package tn.ejb.controller;

import java.io.*;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import tn.ejb.dao.ProjectDao;
import tn.ejb.dao.model.Project;

@WebServlet(name = "projectController", value = "/project")
public class ProjectController extends HttpServlet {
    @EJB
    private ProjectDao projectDao;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("projects", projectDao.listProjects());
        request.getRequestDispatcher("/WEB-INF/views/project.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Project project = new Project();
        project.setCode(req.getParameter("projectCode"));
        project.setDescription(req.getParameter("projectDescription"));

        projectDao.addOrUpdateProject(project);

        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }



    public void destroy() {
    }
}