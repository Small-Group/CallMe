package com.small.group.sfy.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.small.group.sfy.component.CommonService;
import com.small.group.sfy.domain.Clique;
import com.small.group.sfy.domain.CliqueLinkUser;
import com.small.group.sfy.domain.UserInfo;
import com.small.group.sfy.service.*;
import com.small.group.sfy.util.POJOHandle;
import com.small.group.sfy.util.ReturnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Created by yq on 2017/12/10.
 */
@RestController
@RequestMapping("/clique")
public class CliqueRest {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private CliqueService cliqueService;

    @Autowired
    private CliqueLinkUserService cliqueLinkUserService;

    @PostMapping(value = "/create")
    public JsonNode create(@RequestHeader(name = "token") String token,
                           @RequestBody String dataJson) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.readTree(dataJson);
            String userName = jsonNode.path("userName").asText();
            if (commonService.checkToken(userName, token)) {
                Clique clique = new Clique();
                clique.setName(jsonNode.path("name").asText());
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
            } else {
                return ReturnUtil.error("401 授权失败");
            }
        } catch (Exception e) {
            return ReturnUtil.error(e.toString());
        }
        return ReturnUtil.success();
    }

    @PostMapping(value = "/join")
    public JsonNode join(@RequestHeader(name = "token") String token,
                         @RequestBody String dataJson) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.readTree(dataJson);
            String userName = jsonNode.path("userName").asText();
            String serialNum = jsonNode.path("serialNum").asText();
            if (commonService.checkToken(userName, token)) {
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
            } else {
                return ReturnUtil.error("401 授权失败");
            }
        } catch (Exception e) {
            return ReturnUtil.error(e.toString());
        }
        return ReturnUtil.success();
    }

    @GetMapping(value = "/findCliqueList/{userName}")
    public JsonNode findCliqueList(@RequestHeader(name = "token") String token,
                                   @Param("userName") String userName) {
        if (commonService.checkToken(userName, token)) {
            ObjectMapper mapper = new ObjectMapper();
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
            return ReturnUtil.success(arrayNode);
        } else {
            return ReturnUtil.error("401 授权失败！");
        }
    }

    @GetMapping(value = "/findUserInfoList/{userName}/{serialNum}")
    public JsonNode findUserInfoList(@RequestHeader(name = "token") String token,
                                     @Param("userName") String userName,
                                     @Param("serialNum") String serialNum) {
        if (commonService.checkToken(userName, token)) {
            ObjectMapper mapper = new ObjectMapper();
            ArrayNode arrayNode = mapper.createArrayNode();
            List<CliqueLinkUser> cliqueLinkUserList = cliqueLinkUserService.findCliqueLinkUsersBySerialNum(serialNum);
            for (CliqueLinkUser cliqueLinkUser : cliqueLinkUserList) {
                UserInfo userInfo = userInfoService.findUserInfoByUserName(cliqueLinkUser.getUserName());
                arrayNode.add(POJOHandle.handleUserInfo(userInfo));
            }
            return ReturnUtil.success(arrayNode);
        } else {
            return ReturnUtil.error("401 授权失败！");
        }
    }

    @GetMapping(value = "/checkCliqueCreator/{userName}/{serialNum}")
    public JsonNode checkCliqueCreator(@RequestHeader(name = "token") String token,
                                       @Param("userName") String userName,
                                       @Param("serialNum") String serialNum) {
        if (commonService.checkToken(userName, token)) {
            Clique clique = cliqueService.findCliqueBySerialNum(serialNum);
            if (clique.getCreator().equals(userName)) {
                return ReturnUtil.success();
            } else {
                return ReturnUtil.error("不是创建者！");
            }
        } else {
            return ReturnUtil.error("401 授权失败！");
        }
    }

    @GetMapping(value = "/delete/{userName}/{serialNum}")
    public JsonNode delete(@RequestHeader(name = "token") String token,
                           @Param("userName") String userName,
                           @Param("serialNum") String serialNum) {
        if (commonService.checkToken(userName, token)) {
            Clique clique = cliqueService.findCliqueBySerialNum(serialNum);
            cliqueService.delete(clique);
            List<CliqueLinkUser> cliqueLinkUserList = cliqueLinkUserService.findCliqueLinkUsersBySerialNum(serialNum);
            for (CliqueLinkUser cliqueLinkUser : cliqueLinkUserList) {
                cliqueLinkUser.setDelete("true");
                cliqueLinkUser.setExit("2");
                cliqueLinkUser.setUpdateTime(new Date());
                cliqueLinkUserService.save(cliqueLinkUser);
            }
            return ReturnUtil.success();
        } else {
            return ReturnUtil.error("401 授权失败！");
        }
    }

    @PostMapping(value = "/clean/{userName}/{serialNum}")
    public JsonNode clean(@RequestHeader(name = "token") String token,
                          @Param("userName") String userName,
                          @Param("serialNum") String serialNum,
                          @RequestBody String dataJson) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.readTree(dataJson);
            if (commonService.checkToken(userName, token)) {
                Iterator<JsonNode> iterator = jsonNode.elements();
                while (iterator.hasNext()) {
                    CliqueLinkUser cliqueLinkUser = cliqueLinkUserService.findCliqueLinkUserByUserNameAndSerialNum(
                            iterator.next().path("userName").asText(), serialNum);
                    cliqueLinkUser.setDelete("true");
                    cliqueLinkUser.setUpdateTime(new Date());
                    cliqueLinkUser.setExit("1");
                    cliqueLinkUserService.save(cliqueLinkUser);
                }
            } else {
                return ReturnUtil.error("401 授权失败");
            }
        } catch (Exception e) {
            return ReturnUtil.error(e.toString());
        }
        return ReturnUtil.success();
    }

}
