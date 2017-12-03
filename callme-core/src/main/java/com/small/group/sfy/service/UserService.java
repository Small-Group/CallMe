package com.small.group.sfy.service;

import com.small.group.sfy.domain.user.User;
import com.small.group.sfy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yq on 2017/12/3.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAllUser(){
      return  userRepository.findAll();
    }
}
