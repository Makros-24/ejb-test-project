package tn.ejb.ejb;

/**
 * Created by mohamed.iben-el-abed on 12/22/2023
 */
import jakarta.ejb.Local;

import java.sql.Connection;

@Local
public interface DbConnection {
    Connection getConnection();
}
