package com.kmak.daoImpl;

import com.kmak.DBUtils.DataSourceDBConfig;
import com.kmak.DBUtils.JDBCAccess;
import com.kmak.dao.RoleRepository;
import com.kmak.entity.business.Role;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * Created by Leaf.Ye on 2017/3/7.
 */
@Repository
public class RoleRepositoryImpl implements RoleRepository{

    @Inject
    private DataSourceDBConfig dataSourceDBConfig;

    public void addRole(Role role) {
        JDBCAccess jdbcAccess = dataSourceDBConfig.jdbcAccess();
        jdbcAccess.insert("insert into t_role value(?,?,?)",new Object[]{role.getId(),role.getRoleName(),role.getDescription()});
    }
}
