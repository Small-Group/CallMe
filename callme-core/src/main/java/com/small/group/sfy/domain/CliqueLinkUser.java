package com.small.group.sfy.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by yq on 2017/12/12.
 */
@Entity(name = "clique_link_user")
public class CliqueLinkUser  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @Column(name = "serial_num", nullable = false, unique = true)
    private String serialNum;

    @Column(name = "delete")
    private String delete;

    @Column(name = "exit")
    private String exit;// 0：自己退出，1：被T出，2：圈子被删除

    @Column(name = "create_time", nullable = false)
    private Date createTime;

    @Column(name = "update_time", nullable = false)
    private Date updateTime;

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

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getDelete() {
        return delete;
    }

    public void setDelete(String delete) {
        this.delete = delete;
    }

    public String getExit() {
        return exit;
    }

    public void setExit(String exit) {
        this.exit = exit;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "CliqueLinkUser{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", serialNum='" + serialNum + '\'' +
                ", delete='" + delete + '\'' +
                ", exit='" + exit + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
