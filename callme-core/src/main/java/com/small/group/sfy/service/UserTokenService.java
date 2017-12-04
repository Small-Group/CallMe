package com.small.group.sfy.service;

import com.small.group.sfy.domain.user.UserToken;
import com.small.group.sfy.repository.UserTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yq on 2017/12/4.
 */
@Service
public class UserTokenService {

    @Autowired
    UserTokenRepository userTokenRepository;

    public void save(UserToken userToken){
        userTokenRepository.save(userToken);
    }
}
