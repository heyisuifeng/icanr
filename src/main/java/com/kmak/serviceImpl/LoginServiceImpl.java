package com.kmak.serviceImpl;

import com.kmak.dao.UserRepository;
import com.kmak.pojo.User;
import com.kmak.service.LoginService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by 1 on 2017/3/2.
 */
@Service
public class LoginServiceImpl implements LoginService{
    @Inject
    private UserRepository userRepository;

    public boolean login(User user) {

        return userRepository.login(user);
    }
}
