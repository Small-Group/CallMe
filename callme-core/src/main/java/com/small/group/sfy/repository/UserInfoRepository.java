package com.small.group.sfy.repository;

import com.small.group.sfy.domain.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yq on 2017/12/4.
 */
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    UserInfo findUserInfoByUserName(String userName);

}
