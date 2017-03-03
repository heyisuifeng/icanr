package com.kmak.daoImpl;

import com.kmak.dao.UserRepository;
import com.kmak.pojo.User;
import org.springframework.stereotype.Repository;

/**
 * Created by 1 on 2017/3/2.
 */
@Repository
public class UserRepositoryImpl implements UserRepository{
    public boolean login(User user) {
        if(user!=null&&"".equals(user.getName())) return false;
        return true;
    }

    public void addUser(User user) {

    }
}
