package com.small.group.sfy.service;

import com.small.group.sfy.domain.Clique;
import com.small.group.sfy.repository.CliqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yq on 2017/12/10.
 */
@Service
public class CliqueService {

    @Autowired
    CliqueRepository cliqueRepository;

    public void save(Clique clique) {
        cliqueRepository.save(clique);
    }

    public void delete(Clique clique) {
        cliqueRepository.delete(clique);
    }

    public Clique findCliqueBySerialNum(String serialNum) {
        return cliqueRepository.findCliqueBySerialNum(serialNum);
    }

    public Clique findCliqueByName(String name) {
        return cliqueRepository.findCliqueByName(name);
    }

    public List<Clique> findCliquesByNameLike(String cliqueName){
        return cliqueRepository.findCliquesByNameLike(cliqueName);
    }

}
