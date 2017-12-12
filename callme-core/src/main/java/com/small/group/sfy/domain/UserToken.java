package com.small.group.sfy.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by yq on 2017/12/3.
 */
@Entity(name = "user_token")
public class UserToken implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @Column(name = "token")
    private String token;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserToken{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

}
