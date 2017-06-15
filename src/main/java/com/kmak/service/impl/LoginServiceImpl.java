package com.kmak.service.impl;

import com.kmak.dao.UserRepository;
import com.kmak.entity.business.User;
import com.kmak.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 1 on 2017/3/2.
 */
@Service
public class LoginServiceImpl implements LoginService{
    @Autowired
    private UserRepository userRepository;

    public boolean login(User user) {

        return userRepository.login(user);
    }
}
