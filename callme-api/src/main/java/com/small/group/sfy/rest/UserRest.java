package com.small.group.sfy.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.small.group.sfy.domain.user.User;
import com.small.group.sfy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Created by yq on 2017/12/3.
 */
@RestController
@RequestMapping("user")
public class UserRest {

    @Autowired
    private UserService userService;

//    @PostMapping
//    public JsonNode register(@RequestBody String dataJson){
//    }

    @GetMapping()
    public JsonNode findUser() {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();
        List<User> userList = userService.findAllUser();
        for (User user : userList) {
            ObjectNode objectNode = mapper.createObjectNode();
            objectNode.put("userName", user.getUserName());
            objectNode.put("passWord", user.getPassWord());
            arrayNode.add(objectNode);
        }
        return arrayNode;
    }
}
