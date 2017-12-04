package com.small.group.sfy.service;

import com.small.group.sfy.domain.user.UserInfo;
import com.small.group.sfy.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yq on 2017/12/4.
 */
@Service
public class UserInfoService {

    @Autowired
    UserInfoRepository userInfoRepository;

    public void save(UserInfo userInfo) {
        userInfoRepository.save(userInfo);
    }

    public UserInfo findUserInfoByUserName(String userName) {
        return userInfoRepository.findUserInfoByUserName(userName);
    }
}
