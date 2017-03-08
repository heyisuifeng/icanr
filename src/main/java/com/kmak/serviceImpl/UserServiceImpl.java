package com.kmak.serviceImpl;

import com.kmak.dao.RoleRepository;
import com.kmak.dao.UserRepository;
import com.kmak.entity.business.User;
import com.kmak.entity.requestEntity.TestTransactionalEntity;
import com.kmak.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by Leaf.Ye on 2017/3/7.
 */
@Service
public class UserServiceImpl implements UserService{

    @Inject
    private UserRepository userRepository;
    @Inject
    private RoleRepository roleRepository;

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
    public void addUser(User user) throws Exception{
        try {
            userRepository.addUser(user);
            User user1 = null;
            userRepository.addUser(user1);
        }catch (Exception e){
            throw new RuntimeException("运行时异常");
        }
    }

    @Transactional
    public void addUserAndRole(TestTransactionalEntity entity) {
        userRepository.addUser(entity.getUsers().get(0));
        roleRepository.addRole(entity.getRoles().get(0));
    }

    @Transactional
    public void deleteUserById(int id) throws Exception{
        userRepository.deleteUserById(id);
        throw new RuntimeException("运行时异常");
    }
}
