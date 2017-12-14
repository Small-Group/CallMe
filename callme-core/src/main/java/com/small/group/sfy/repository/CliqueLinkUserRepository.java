package com.small.group.sfy.repository;

import com.small.group.sfy.domain.CliqueLinkUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by yq on 2017/12/12.
 */
public interface CliqueLinkUserRepository extends JpaRepository<CliqueLinkUser, Long> {

    List<CliqueLinkUser> findCliqueLinkUsersByUserNameAndDeletedIsNull(String userName);

    List<CliqueLinkUser> findCliqueLinkUsersBySerialNumAndDeletedIsNull(String serialNum);

    CliqueLinkUser   findCliqueLinkUserByUserNameAndSerialNumAndDeletedIsNull(String userName,String serialNum);

}
