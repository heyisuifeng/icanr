package com.kmak.service;

import com.kmak.pojo.User;
import org.springframework.stereotype.Service;

/**
 * Created by 1 on 2017/3/2.
 */
public interface LoginService {
    public boolean login(User user);
}
