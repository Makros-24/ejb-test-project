package tn.ejb.ejb.impl;

import jakarta.ejb.Singleton;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import tn.ejb.ejb.DbConnection;
import tn.ejb.ejb.model.DbProperties;

import java.io.File;
import java.sql.*;

/**
 * Created by mohamed.iben-el-abed on 12/22/2023
 */

@Singleton
public class DbConnectionImpl implements DbConnection {
    private Connection connection;

    private void initializeConnection() {
        try {
            File file = new File(DbProperties.class.getClassLoader().getResource("datasource.xml").getFile());
            JAXBContext jaxbContext = JAXBContext.newInstance(DbProperties.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            DbProperties connect = (DbProperties) jaxbUnmarshaller.unmarshal(file);
            Class.forName(connect.getDriver());
            connection = createNewDatabase(connect.getUrl());
            createTable();
        } catch (Exception e) {
            System.out.println("connection falled down " + e);
        }
    }

    public Connection createNewDatabase(String url) throws SQLException {

        Connection conn = DriverManager.getConnection(url);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
                return conn;
            }
        return null;
    }

    private void createTable() throws SQLException {
        Statement statement = connection.createStatement();

        String createProjectTable = "CREATE TABLE IF NOT EXISTS Project ("
                + "code VARCHAR(255) PRIMARY KEY, "
                + "description VARCHAR(255), "
                + "startDate DATE"
                + ")";

        statement.execute(createProjectTable);

        String createTaskTable = "CREATE TABLE IF NOT EXISTS Task (" +
                "code VARCHAR(255) PRIMARY KEY, " +
                "description VARCHAR(255), " +
                "startDate DATE, " +
                "endDate DATE, " +
                "projectCode VARCHAR(255), " +
                "FOREIGN KEY (projectCode) REFERENCES Project(code)" +
                ")";


        statement.execute(createTaskTable);
    }

    @Override
    public Connection getConnection() {
        if (connection == null) initializeConnection();
        return connection;
    }
}
