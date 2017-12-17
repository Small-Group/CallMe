package com.small.group.sfy.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.small.group.sfy.domain.Clique;
import com.small.group.sfy.domain.CliqueLinkUser;
import com.small.group.sfy.domain.User;
import com.small.group.sfy.domain.UserInfo;
import com.small.group.sfy.service.CliqueLinkUserService;
import com.small.group.sfy.service.CliqueService;
import com.small.group.sfy.service.UserInfoService;
import com.small.group.sfy.service.UserService;
import com.small.group.sfy.util.POJOHandle;
import com.small.group.sfy.util.ReturnUtil;
import com.small.group.sfy.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
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
    private CliqueService cliqueService;

    @Autowired
    private CliqueLinkUserService cliqueLinkUserService;

    /**
     * 用户注册 （需先校验用户名和昵称）
     * PathVariable：json对象，包含：userName passWord nickName
     */
    @PostMapping(value = "/register")
    public JsonNode register(@RequestBody String dataJson) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.readTree(dataJson);
            String userName = jsonNode.path("userName").asText();
            String passWord = jsonNode.path("passWord").asText();
            String nickName = jsonNode.path("nickName").asText();
            if (!existUserName(userName)) {
                User user = new User();
                user.setUserName(userName);
                user.setPassWord(passWord);
                user.setCreateTime(new Date());
                user.setUpdateTime(new Date());
                userService.save(user);
                UserInfo userInfo = new UserInfo();
                userInfo.setUserName(userName);
                userInfo.setNickName(nickName);
                userInfo.setPortrait("url:www.baidu.com");
                userInfo.setCreateTime(new Date());
                userInfo.setUpdateTime(new Date());
                userInfoService.save(userInfo);
            } else {
                return ReturnUtil.error("用户名已存在！");
            }
        } catch (Exception e) {
            return ReturnUtil.error(e.toString());
        }
        return ReturnUtil.success();
    }

    /**
     * 用户登录
     * PathVariable：json对象，包含：userName passWord
     * return：用户信息,圈子列表
     */
    @PostMapping(value = "/login")
    public JsonNode login(@RequestBody String dataJson) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.readTree(dataJson);
            ObjectNode returnNode = mapper.createObjectNode();
            String userName = jsonNode.path("userName").asText();
            String passWord = jsonNode.path("passWord").asText();
            if (existUserName(userName)) {
                User user = userService.findUserByUserName(userName);
                if (user.getPassWord().equals(passWord)) {
                    //用户信息
                    UserInfo userInfo = userInfoService.findUserInfoByUserName(userName);
                    returnNode.set("userInfo", POJOHandle.handleUserInfo(userInfo));
                    //圈子列表
                    ArrayNode arrayNode = mapper.createArrayNode();
                    List<CliqueLinkUser> cliqueLinkUserList = cliqueLinkUserService.findCliqueLinkUsersByUserName(userName);
                    for (CliqueLinkUser cliqueLinkUser : cliqueLinkUserList) {
                        Clique clique = cliqueService.findCliqueBySerialNum(cliqueLinkUser.getSerialNum());
                        ObjectNode objectNode = mapper.createObjectNode();
                        objectNode.put("name", clique.getName());
                        objectNode.put("serialNum", clique.getSerialNum());
                        objectNode.put("creator", clique.getCreator());
                        objectNode.put("createTime", clique.getCreateTime().toString());
                        objectNode.put("updateTime", clique.getUpdateTime().toString());
                        arrayNode.add(objectNode);
                    }
                    returnNode.set("clique", arrayNode);
                    return ReturnUtil.success(returnNode);
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

    @GetMapping(value = "/findUserInfo/{userName}")
    public JsonNode findUserInfo(@PathVariable("userName") String userName) {
        UserInfo userInfo = userInfoService.findUserInfoByUserName(userName);
        return ReturnUtil.success(POJOHandle.handleUserInfo(userInfo));
    }

    @PostMapping(value = "/updateUserInfo")
    public JsonNode updateUserInfo(@RequestBody String dataJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(dataJson);
            String userName = jsonNode.path("userName").asText();
            UserInfo userInfo = userInfoService.findUserInfoByUserName(userName);
            userInfoService.save(POJOHandle.handleUserInfo(dataJson, userInfo));
            return ReturnUtil.success();
        } catch (Exception e) {
            return ReturnUtil.error(e.toString());
        }
    }

    @PostMapping(value = "/updatePassWord")
    public JsonNode updatePassWord(@RequestBody String dataJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(dataJson);
            String userName = jsonNode.path("userName").asText();
            String passWord = jsonNode.path("passWord").asText();
            User user = userService.findUserByUserName(userName);
            user.setPassWord(passWord);
            user.setUpdateTime(new Date());
            userService.save(user);
            return ReturnUtil.success();
        } catch (Exception e) {
            return ReturnUtil.error(e.toString());
        }
    }

    @GetMapping(value = "/delete/{userName}")
    public JsonNode deleteUser(@PathVariable("userName") String userName) {
        User user = userService.findUserByUserName(userName);
        UserInfo userInfo = userInfoService.findUserInfoByUserName(userName);
        userInfoService.delete(userInfo);
        userService.delete(user);
        return ReturnUtil.success();
    }

    @GetMapping(value = "/checkUserName")
    public JsonNode checkUserName(@PathVariable("userName") String userName) {
        if (!existUserName(userName)) {
            return ReturnUtil.success();
        } else {
            return ReturnUtil.error("用户名已被占用！");
        }
    }

    @GetMapping(value = "/check/nickName")
    public JsonNode existNickName(@PathVariable("nickName") String nickName) {
        if (StringUtil.isNotNull(nickName)) {
            UserInfo userInfo = userInfoService.findUserInfoByNickName(nickName);
            if (userInfo == null) {
                return ReturnUtil.success();
            }
        }
        return ReturnUtil.error("昵称已被占用！");
    }

    @PostMapping(value = "/create")
    public JsonNode create(@RequestBody String dataJson) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.readTree(dataJson);
            String userName = jsonNode.path("userName").asText();
            Clique clique = new Clique();
            clique.setName(jsonNode.path("cliqueName").asText());
            clique.setIcon("www.baidu.com");
            String serialNum = UUID.randomUUID().toString();
            clique.setSerialNum(serialNum);
            clique.setCreator(userName);
            clique.setCreateTime(new Date());
            clique.setUpdateTime(new Date());
            cliqueService.save(clique);
            CliqueLinkUser cliqueLinkUser = new CliqueLinkUser();
            cliqueLinkUser.setUserName(userName);
            cliqueLinkUser.setSerialNum(serialNum);
            cliqueLinkUser.setCreateTime(new Date());
            cliqueLinkUser.setUpdateTime(new Date());
            cliqueLinkUserService.save(cliqueLinkUser);
        } catch (Exception e) {
            return ReturnUtil.error(e.toString());
        }
        return ReturnUtil.success();
    }

    @PostMapping(value = "/join")
    public JsonNode join(@RequestBody String dataJson) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.readTree(dataJson);
            String userName = jsonNode.path("userName").asText();
            String serialNum = jsonNode.path("serialNum").asText();
            CliqueLinkUser cliqueLinkUser = cliqueLinkUserService.findCliqueLinkUserByUserNameAndSerialNum(userName, serialNum);
            if (cliqueLinkUser == null) {
                cliqueLinkUser = new CliqueLinkUser();
                cliqueLinkUser.setUserName(userName);
                cliqueLinkUser.setSerialNum(serialNum);
                cliqueLinkUser.setCreateTime(new Date());
                cliqueLinkUser.setUpdateTime(new Date());
                cliqueLinkUserService.save(cliqueLinkUser);
            } else {
                return ReturnUtil.error("已经在圈子中");
            }
        } catch (Exception e) {
            return ReturnUtil.error(e.toString());
        }
        return ReturnUtil.success();
    }

    @GetMapping(value = "/findUserInfoList/{serialNum}")
    public JsonNode findUserInfoList(@PathVariable("serialNum") String serialNum) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();
        List<CliqueLinkUser> cliqueLinkUserList = cliqueLinkUserService.findCliqueLinkUsersBySerialNum(serialNum);
        for (CliqueLinkUser cliqueLinkUser : cliqueLinkUserList) {
            UserInfo userInfo = userInfoService.findUserInfoByUserName(cliqueLinkUser.getUserName());
            arrayNode.add(POJOHandle.handleUserInfo(userInfo));
        }
        return ReturnUtil.success(arrayNode);
    }

    @GetMapping(value = "/delete/{userName}/{serialNum}")
    public JsonNode delete(@PathVariable("userName") String userName,
                           @PathVariable("serialNum") String serialNum) {
        if (checkCliqueCreator(userName, serialNum)) {
            Clique clique = cliqueService.findCliqueBySerialNum(serialNum);
            cliqueService.delete(clique);
            List<CliqueLinkUser> cliqueLinkUserList = cliqueLinkUserService.findCliqueLinkUsersBySerialNum(serialNum);
            for (CliqueLinkUser cliqueLinkUser : cliqueLinkUserList) {
                cliqueLinkUser.setDeleted("true");
                cliqueLinkUser.setExited("2");
                cliqueLinkUser.setUpdateTime(new Date());
                cliqueLinkUserService.save(cliqueLinkUser);
            }
            return ReturnUtil.success();
        }
        return ReturnUtil.error("不是创建者！");
    }

    @PostMapping(value = "/clean/{userName}/{serialNum}")
    public JsonNode clean(@PathVariable("userName") String userName,
                          @PathVariable("serialNum") String serialNum,
                          @RequestBody String dataJson) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            if (checkCliqueCreator(userName, serialNum)) {
                JsonNode jsonNode = mapper.readTree(dataJson);
                Iterator<JsonNode> iterator = jsonNode.elements();
                while (iterator.hasNext()) {
                    CliqueLinkUser cliqueLinkUser = cliqueLinkUserService.findCliqueLinkUserByUserNameAndSerialNum(
                            iterator.next().path("userName").asText(), serialNum);
                    cliqueLinkUser.setDeleted("true");
                    cliqueLinkUser.setUpdateTime(new Date());
                    cliqueLinkUser.setExited("1");
                    cliqueLinkUserService.save(cliqueLinkUser);
                }
            }
        } catch (Exception e) {
            return ReturnUtil.error(e.toString());
        }
        return ReturnUtil.success();
    }

    @GetMapping(value = "/search/{cliqueName}")
    public JsonNode search(@PathVariable("cliqueName") String cliqueName) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();
        String likeName = "%" + cliqueName + "%";
        List<Clique> cliqueList = cliqueService.findCliquesByNameLike(likeName);
        for (Clique clique : cliqueList) {
            arrayNode.add(POJOHandle.handleClique(clique));
        }
        return ReturnUtil.success(arrayNode);
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

    private boolean checkCliqueCreator(String userName, String serialNum) {
        Clique clique = cliqueService.findCliqueBySerialNum(serialNum);
        if (clique.getCreator().equals(userName)) {
            return true;
        } else {
            return false;
        }
    }

}
