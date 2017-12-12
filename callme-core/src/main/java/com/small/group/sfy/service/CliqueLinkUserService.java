package com.small.group.sfy.service;

import com.small.group.sfy.domain.CliqueLinkUser;
import com.small.group.sfy.repository.CliqueLinkUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yq on 2017/12/12.
 */
@Service
public class CliqueLinkUserService {

    @Autowired
    CliqueLinkUserRepository cliqueLinkUserRepository;

    public void save(CliqueLinkUser cliqueLinkUser) {
        cliqueLinkUserRepository.save(cliqueLinkUser);
    }

    public void delete(CliqueLinkUser cliqueLinkUser) {
        cliqueLinkUserRepository.delete(cliqueLinkUser);
    }

    public void delete(List<CliqueLinkUser> cliqueLinkUserList){
        cliqueLinkUserRepository.delete(cliqueLinkUserList);
    }

    public List<CliqueLinkUser> findCliqueLinkUsersByUserName(String userName) {
        return cliqueLinkUserRepository.findCliqueLinkUsersByUserNameAndDeleteIsNull(userName);
    }

    public List<CliqueLinkUser> findCliqueLinkUsersBySerialNum(String serialNum) {
        return cliqueLinkUserRepository.findCliqueLinkUsersBySerialNumAndDeleteIsNull(serialNum);
    }

    public CliqueLinkUser findCliqueLinkUserByUserNameAndSerialNum(String userName,String serialNum){
        return cliqueLinkUserRepository.findCliqueLinkUserByUserNameAndSerialNumAndDeleteIsNull(userName,serialNum);
    }

}
