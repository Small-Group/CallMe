package com.small.group.sfy.repository;

import com.small.group.sfy.domain.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yq on 2017/12/4.
 */
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {

    UserToken findUserTokenByUserName(String userName);

}
