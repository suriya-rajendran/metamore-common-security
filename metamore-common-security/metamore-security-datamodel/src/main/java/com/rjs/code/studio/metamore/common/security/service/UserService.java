package com.rjs.code.studio.metamore.common.security.service;

import com.rjs.code.studio.metamore.common.security.datamodel.UserDetails;
import com.rjs.code.studio.metamore.common.security.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    public UserDetails loadByUserName(String username){
        return userRepo.findByUsername(username);
    }
}
