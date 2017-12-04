package com.small.group.sfy.service;

import com.small.group.sfy.domain.user.User;
import com.small.group.sfy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yq on 2017/12/3.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
    }

    public User findUserByUserName(String userName) {
        return userRepository.findUserByUserName(userName);
    }

}
