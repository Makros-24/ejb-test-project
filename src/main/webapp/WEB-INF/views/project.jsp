<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="tn.ejb.dao.model.Project" %>
<%@ page import="tn.ejb.dao.model.Task" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Project Management</title>
</head>
<body>
<h1>Project Management</h1>

<%
    // Assuming that the projects are stored in the request attribute "projects"
    List<Project> projects = (List<Project>) request.getAttribute("projects");
%>

<table border="1">
    <tr>
        <th>Project Code</th>
        <th>Project Description</th>
        <th>Tasks</th>
    </tr>
    <% for (Project project : projects) { %>
    <tr>
        <td><%= project.getCode() %></td>
        <td><%= project.getDescription() %></td>
        <td><%= project.getStartDate()%></td>
        <td>
            <table border="1">
                <tr>
                    <th>Task Code</th>
                    <th>Task Description</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                </tr>
                <% if (!project.getTasks().isEmpty()) { %>
                <% for (Task task : project.getTasks()) { %>
                    <tr>
                        <td><%= task.getCode() %></td>
                        <td><%= task.getDescription() %></td>
                        <td><%= task.getStartDate() %></td>
                        <td><%= task.getEndDate() %></td>
                    </tr>
                  <% } %>
                <% } %>
            </table>
        </td>
    </tr>
    <% } %>
</table>

</body>
</html>