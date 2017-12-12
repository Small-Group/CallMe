package com.small.group.sfy.component;

import com.small.group.sfy.domain.UserToken;
import com.small.group.sfy.service.UserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by yq on 2017/12/10.
 */
@Component
public class CommonService {

    @Autowired
    private UserTokenService userTokenService;

    public boolean checkToken(String userName, String token) {
        UserToken userToken = userTokenService.findUserTokenByUserName(userName);
        if (userToken != null && userToken.getToken().equals(token)) {
            return true;
        }
        return false;
    }

}
