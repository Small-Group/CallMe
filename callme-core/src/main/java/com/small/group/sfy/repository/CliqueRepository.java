package com.small.group.sfy.repository;

import com.small.group.sfy.domain.Clique;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by yq on 2017/12/10.
 */
public interface CliqueRepository extends JpaRepository<Clique, Long> {

    Clique findCliqueBySerialNum(String serialNum);

    Clique findCliqueByName(String name);

    List<Clique> findCliquesByNameLike(String name);

}
