package com.small.group.sfy.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.small.group.sfy.domain.Clique;
import com.small.group.sfy.domain.UserInfo;
import com.small.group.sfy.rest.UserRest;

import java.util.Date;

/**
 * Created by yq on 2017/12/4.
 */
public class POJOHandle {

    public static JsonNode handleUserInfo(UserInfo userInfo) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("userName", userInfo.getUserName());
        objectNode.put("nickName", userInfo.getNickName());
        objectNode.put("name", userInfo.getName());
        objectNode.put("portrait", userInfo.getPortrait());
        objectNode.put("sex", userInfo.getSex());
        objectNode.put("level", userInfo.getLevel());
        objectNode.put("point", userInfo.getPoint());
        objectNode.put("landLine", userInfo.getLandLine());
        objectNode.put("phone", userInfo.getPhone());
        objectNode.put("qq", userInfo.getQq());
        objectNode.put("weChat", userInfo.getWeChat());
        objectNode.put("email", userInfo.getEmail());
        objectNode.put("city", userInfo.getCity());
        objectNode.put("company", userInfo.getCompany());
        objectNode.put("school", userInfo.getSchool());
        objectNode.put("remark", userInfo.getRemark());
        return objectNode;
    }

    public static UserInfo handleUserInfo(String dataJson,UserInfo userInfo) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(dataJson);
        userInfo.setNickName(jsonNode.path("nickName").asText());
        userInfo.setName(jsonNode.path("name").asText());
        userInfo.setPortrait(jsonNode.path("portrait").asText());
        userInfo.setSex(jsonNode.path("sex").asText());
        userInfo.setLevel(jsonNode.path("level").asText());
        userInfo.setPoint(jsonNode.path("point").asInt());
        userInfo.setLandLine(jsonNode.path("landLine").asText());
        userInfo.setPhone(jsonNode.path("phone").asText());
        userInfo.setQq(jsonNode.path("qq").asText());
        userInfo.setWeChat(jsonNode.path("weChat").asText());
        userInfo.setEmail(jsonNode.path("email").asText());
        userInfo.setCity(jsonNode.path("city").asText());
        userInfo.setCompany(jsonNode.path("company").asText());
        userInfo.setSchool(jsonNode.path("school").asText());
        userInfo.setRemark(jsonNode.path("remark").asText());
        userInfo.setUpdateTime(new Date());
        return userInfo;
    }

    public static ObjectNode handleClique(Clique clique) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("serialNum", clique.getSerialNum());
        objectNode.put("name", clique.getName());
        objectNode.put("icon", clique.getIcon());
        objectNode.put("creator", clique.getCreator());
        objectNode.put("createTime", UserRest.formatter.format(clique.getCreateTime()));
        objectNode.put("updateTime", UserRest.formatter.format(clique.getUpdateTime()));
        return objectNode;
    }

}
