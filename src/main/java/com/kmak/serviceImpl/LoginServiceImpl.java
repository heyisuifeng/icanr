package com.kmak.serviceImpl;

import com.kmak.pojo.User;
import com.kmak.service.LoginService;
import org.springframework.stereotype.Service;

/**
 * Created by 1 on 2017/3/2.
 */
@Service
public class LoginServiceImpl implements LoginService{
    public boolean login(User user) {
        if(user!=null&&"".equals(user.getName()))return false;
        return true;
    }
}
