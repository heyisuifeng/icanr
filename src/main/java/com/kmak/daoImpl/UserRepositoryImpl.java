package com.kmak.daoImpl;

import com.kmak.DBUtils.DataSourceDBConfig;
import com.kmak.DBUtils.JDBCAccess;
import com.kmak.dao.UserRepository;
import com.kmak.entity.business.User;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * Created by Leaf.Ye on 2017/3/2.
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    @Inject
    private DataSourceDBConfig dataSourceDBConfig;

    public boolean login(User user) {
        if(user!=null&&"".equals(user.getName())) return false;
        JDBCAccess jdbcAccess = dataSourceDBConfig.jdbcAccess();
        List<Map<String, Object>> users = jdbcAccess.queryForList("select * from t_user where username='"+user.getName()+"' and password='"+user.getPassword()+"'");
        if(CollectionUtils.isEmpty(users))return false;
        return true;
    }

    public void addUser(User user) {
        JDBCAccess jdbcAccess = dataSourceDBConfig.jdbcAccess();
        jdbcAccess.insert("insert into t_user (username,password) value (?,?)",new Object[]{user.getName(),user.getPassword()});
    }
}
