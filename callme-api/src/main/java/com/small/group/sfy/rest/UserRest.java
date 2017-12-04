package com.small.group.sfy.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.small.group.sfy.domain.user.User;
import com.small.group.sfy.domain.user.UserInfo;
import com.small.group.sfy.domain.user.UserToken;
import com.small.group.sfy.service.UserInfoService;
import com.small.group.sfy.service.UserService;
import com.small.group.sfy.service.UserTokenService;
import com.small.group.sfy.util.POJOHandle;
import com.small.group.sfy.util.ReturnUtil;
import com.small.group.sfy.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;


/**
 * Created by yq on 2017/12/3.
 */
@RestController
@RequestMapping("/user")
public class UserRest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserTokenService userTokenService;

    @PostMapping(name = "/register")
    public JsonNode register(@RequestBody String dataJson) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.readTree(dataJson);
            String userName = jsonNode.path("userName").asText();
            String passWord = jsonNode.path("passWord").asText();
            if (!existUserName(userName)) {
                User user = new User();
                user.setUserName(userName);
                user.setPassWord(passWord);
                user.setCreateTime(new Date());
                user.setUpdateTime(new Date());
                userService.save(user);
                UserInfo userInfo = new UserInfo();
                userInfo.setUserName(userName);
                userInfo.setPortrait("url:www.baidu.com");
                userInfo.setCreateTime(new Date());
                userInfo.setUpdateTime(new Date());
                userInfoService.save(userInfo);
                UserToken userToken = new UserToken();
                userToken.setUserName(userName);
                userTokenService.save(userToken);
            } else {
                return ReturnUtil.error("用户名已存在！");
            }
        } catch (Exception e) {
            return ReturnUtil.error(e.toString());
        }
        return ReturnUtil.success();
    }

    @PostMapping(name = "/login")
    public JsonNode login(@RequestBody String dataJson) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.readTree(dataJson);
            String userName = jsonNode.path("userName").asText();
            String passWord = jsonNode.path("passWord").asText();
            if (existUserName(userName)) {
                String token = UUID.randomUUID().toString();
                User user = userService.findUserByUserName(userName);
                if (user.getPassWord().equals(passWord)) {
                    UserToken userToken = userTokenService.findUserTokenByUserName(userName);
                    userToken.setToken(token);
                    userTokenService.save(userToken);
                    return ReturnUtil.success(mapper.createObjectNode().put("token", token));
                } else {
                    return ReturnUtil.error("密码错误！");
                }
            } else {
                return ReturnUtil.error("用户名不存在！");
            }
        } catch (Exception e) {
            return ReturnUtil.error(e.toString());
        }
    }

    @GetMapping(name = "/findUserInfo/{userName}")
    public JsonNode findUserInfo(@RequestHeader(name = "token") String token,
                                 @Param("userName") String userName) {
        if (checkToken(userName, token)) {
            UserInfo userInfo = userInfoService.findUserInfoByUserName(userName);
            return ReturnUtil.success(POJOHandle.handleUserInfo(userInfo));
        }
        return ReturnUtil.error("未知错误！");
    }

    @PostMapping(name = "/updateUserInfo")
    public JsonNode updateUserInfo(@RequestHeader(name = "token") String token,
                                   @RequestBody String dataJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(dataJson);
            String userName = jsonNode.path("userName").asText();
            if (checkToken(userName, token)) {
                UserInfo userInfo = userInfoService.findUserInfoByUserName(userName);
                userInfoService.save(POJOHandle.handleUserInfo(dataJson, userInfo));
                return ReturnUtil.success();
            }
            return ReturnUtil.error("未知错误！");
        } catch (Exception e) {
            return ReturnUtil.error(e.toString());
        }
    }

    @PostMapping(name = "/updatePassWord")
    public JsonNode updatePassWord(@RequestHeader(name = "token") String token,
                                   @RequestBody String dataJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(dataJson);
            String userName = jsonNode.path("userName").asText();
            String passWord = jsonNode.path("passWord").asText();
            if (checkToken(userName, token)) {
                User user = userService.findUserByUserName(userName);
                user.setPassWord(passWord);
                user.setUpdateTime(new Date());
                userService.save(user);
                return ReturnUtil.success();
            }
            return ReturnUtil.error("未知错误！");
        } catch (Exception e) {
            return ReturnUtil.error(e.toString());
        }
    }

    @GetMapping(name = "/delete/{userName}")
    public JsonNode deleteUser(@RequestHeader(name = "token") String token,
                               @Param("userName") String userName) {
        if (checkToken(userName, token)) {
            User user = userService.findUserByUserName(userName);
            UserInfo userInfo = userInfoService.findUserInfoByUserName(userName);
            UserToken userToken = userTokenService.findUserTokenByUserName(userName);
            userTokenService.delete(userToken);
            userInfoService.delete(userInfo);
            userService.delete(user);
            return ReturnUtil.success();
        } else {
            return ReturnUtil.error("未知错误！");
        }
    }

    @GetMapping(name = "/check/{userName}")
    public JsonNode checkUserName(@Param("userName") String userName) {
        if (!existUserName(userName)) {
            return ReturnUtil.success();
        } else {
            return ReturnUtil.error("用户名已被占用！");
        }
    }

    private boolean existUserName(String userName) {
        if (StringUtil.isNotNull(userName)) {
            User user = userService.findUserByUserName(userName);
            if (user != null) {
                return true;
            }
        }
        return false;
    }

    private boolean checkToken(String userName, String token) {
        UserToken userToken = userTokenService.findUserTokenByUserName(userName);
        if (userToken != null && userToken.getToken().equals(token)) {
            return true;
        }
        return false;
    }
}
