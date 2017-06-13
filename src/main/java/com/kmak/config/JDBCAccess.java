package com.kmak.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StopWatch;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


/**
 * Created by Leaf.Ye on 2017/3/3.
 *
 * JDBCAccess
 *
 * JDBC 执行工具类，仅对原生方法添加了日志记录，并调整了形参列表的顺序，同时使用自定义的RowMapper替换原生的RowMapper
 */
public class JDBCAccess {

    private final Logger logger = LoggerFactory.getLogger("JDBCAccess");

    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public <T> List<T> find(String sql, RowMapper<T> rowMapper, Object... params){
        StopWatch watch = new StopWatch();
        try {
            return jdbcTemplate.query(sql,params,rowMapper);
        }finally {
            logger.debug("find,sql={},params={},elapsedTime={}",new Object[]{sql,params,watch.getTotalTimeMillis()});
        }
    }

    public <T> T findUniqueResult(String sql,RowMapper<T> rowMapper,Object... params){
        StopWatch watch = new StopWatch();
        try {
            return (T) jdbcTemplate.queryForObject(sql,params,rowMapper);
        }finally {
            logger.debug("findUniqueResult,sql={},params={},elapsedTime={}",new Object[]{sql,params,watch.getTotalTimeMillis()});
        }
    }

    public int findInteger(String sql,Object... params){
        StopWatch watch = new StopWatch();
        try {
            return jdbcTemplate.queryForInt(sql,params);
        }finally {
            logger.debug("findInteger,sql={},params={},elapsedTime={}",new Object[]{sql,params,watch.getTotalTimeMillis()});
        }
    }

    public String findString(String sql,Object... params){
        StopWatch watch = new StopWatch();
        try {
            return (String) jdbcTemplate.queryForObject(sql, params, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                    return resultSet.getString(1);
                }
            });
        }finally {
            logger.debug("findString,sql={},params={},elapsedTime={}",new Object[]{sql,params,watch.getTotalTimeMillis()});
        }
    }

    public int insert(String sql,Object... params){
        StopWatch watch = new StopWatch();
        try {
            return jdbcTemplate.update(sql,params);
        }finally {
            logger.debug("insert,sql={},params={},elapsedTime={}",new Object[]{sql,params,watch.getTotalTimeMillis()});
        }
    }

    public int update(String sql,Object... params){
        StopWatch watch = new StopWatch();
        try {
            return jdbcTemplate.update(sql,params);
        }finally {
            logger.debug("update,sql={},params={},elapsedTime={}",new Object[]{sql,params,watch.getTotalTimeMillis()});
        }
    }

    public int delete(String sql,Object... params){
        StopWatch watch = new StopWatch();
        try {
            return jdbcTemplate.update(sql,params);
        }finally {
            logger.debug("delete,sql={},params={},elapsedTime={}",new Object[]{sql,params,watch.getTotalTimeMillis()});
        }
    }

    public int[] batchExecute(String sql,List<Object[]> params){
        StopWatch watch = new StopWatch();
        try {
            return jdbcTemplate.batchUpdate(sql,params);
        }finally {
            logger.debug("batchExecute,sql={},params={},elapsedTime={}",new Object[]{sql,params,watch.getTotalTimeMillis()});
        }
    }

    public List<Map<String, Object>> queryForList(String sql){
        StopWatch watch = new StopWatch();
        try {
            return jdbcTemplate.queryForList(sql);
        }finally {
            logger.debug("queryForList,sql={},elapsedTime={}",new Object[]{sql,watch.getTotalTimeMillis()});
        }
    }
}
