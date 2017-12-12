package com.small.group.sfy.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by yq on 2017/12/3.
 */
@Entity(name = "user_info")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @Column(name = "nick_name", nullable = false, unique = true)
    private String nickName;

    @Column(name = "name")
    private String name;

    @Column(name = "portrait")
    private String portrait;

    @Column(name = "sex")
    private String sex;

    @Column(name = "level")
    private String level;

    @Column(name = "point")
    private Integer point;

    @Column(name = "land_line")
    private String landLine;

    @Column(name = "phone")
    private String phone;

    @Column(name = "qq")
    private String qq;

    @Column(name = "we_chat")
    private String weChat;

    @Column(name = "email")
    private String email;

    @Column(name = "city")
    private String city;

    @Column(name = "company")
    private String company;

    @Column(name = "school")
    private String school;

    @Column(name = "remark")
    private String remark;

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getLandLine() {
        return landLine;
    }

    public void setLandLine(String landLine) {
        this.landLine = landLine;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        return "UserInfo{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", name='" + name + '\'' +
                ", portrait='" + portrait + '\'' +
                ", sex='" + sex + '\'' +
                ", level='" + level + '\'' +
                ", point='" + point + '\'' +
                ", landLine='" + landLine + '\'' +
                ", phone='" + phone + '\'' +
                ", qq='" + qq + '\'' +
                ", weChat='" + weChat + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", company='" + company + '\'' +
                ", school='" + school + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
