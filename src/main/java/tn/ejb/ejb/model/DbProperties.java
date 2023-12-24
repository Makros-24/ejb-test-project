package tn.ejb.ejb.model;

/**
 * Created by mohamed.iben-el-abed on 12/22/2023
 */

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;


@Getter
@XmlRootElement(name = "DbProperties")
public class DbProperties {

    private String url;
    private String driver;
    private String user;
    private String password;

    @XmlElement(name = "url")
    public void setUrl(String url) {
        this.url = url;
    }

    @XmlElement(name = "driver")
    public void setDriver(String driver) {
        this.driver = driver;
    }

    @XmlElement(name = "user")
    public void setUser(String user) {
        this.user = user;
    }

    @XmlElement(name = "password")
    public void setPassword(String password) {
        this.password = password;
    }
}