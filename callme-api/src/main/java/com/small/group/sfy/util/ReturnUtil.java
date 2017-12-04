package com.small.group.sfy.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Created by yq on 2017/12/4.
 */
public class ReturnUtil {

    public static JsonNode success() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("code", 0);
        objectNode.put("msg", "");
        return objectNode;
    }

    public static JsonNode success(JsonNode jsonNode) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("code", 0);
        objectNode.put("msg", "");
        objectNode.put("data", jsonNode);
        return objectNode;
    }

    public static JsonNode error(String msg) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("code", -1);
        objectNode.put("msg", msg);
        return objectNode;
    }

}
