package com.kmak.daoImpl;

import com.kmak.DBUtils.DataSourceDBConfig;
import com.kmak.DBUtils.JDBCAccess;
import com.kmak.DBUtils.RowMapper;
import com.kmak.dao.UserRepository;
import com.kmak.pojo.User;
import org.apache.taglibs.standard.tag.common.sql.DataSourceWrapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Leaf.Ye on 2017/3/2.
 */
@Repository
public class UserRepositoryImpl implements UserRepository{

    public boolean login(User user) {
        if(user!=null&&"".equals(user.getName())) return false;
        DataSourceDBConfig dataSourceDBConfig = new DataSourceDBConfig();
        JDBCAccess jdbcAccess = dataSourceDBConfig.jdbcAccess();
        List<Map<String, Object>> users = jdbcAccess.queryForList("select * from t_user where username='"+user.getName()+"' and password='"+user.getPassword()+"'");
        if(CollectionUtils.isEmpty(users))return false;
        return true;
    }

    public void addUser(User user) {

    }
}
