package com.kmak.serviceImpl;

import com.kmak.dao.RoleRepository;
import com.kmak.dao.UserRepository;
import com.kmak.entity.business.User;
import com.kmak.entity.requestEntity.TestTransactionalEntity;
import com.kmak.service.UserService;
import org.springframework.stereotype.Service;
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

    @Transactional
    public void addUser(User user) {
        userRepository.addUser(user);
    }

    @Transactional
    public void addUserAndRole(TestTransactionalEntity entity) {
        userRepository.addUser(entity.getUsers().get(0));
        roleRepository.addRole(entity.getRoles().get(0));
    }
}
