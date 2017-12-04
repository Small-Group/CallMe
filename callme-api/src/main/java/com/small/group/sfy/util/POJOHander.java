package com.small.group.sfy.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.small.group.sfy.domain.user.UserInfo;

/**
 * Created by yq on 2017/12/4.
 */
public class POJOHander {

    public static JsonNode handerUserInfo(UserInfo userInfo) {
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
}
