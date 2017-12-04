package com.small.group.sfy.repository;

import com.small.group.sfy.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yq on 2017/12/3.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUserName(String userName);

}
