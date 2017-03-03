package com.kmak.DBUtils;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Leaf.Ye on 2017/3/3.
 * 替换在JdbcTemplate中使用的org.springframework.jdbc.core.RowMapper<T>接口,
 * 处理原生方法中ResultSet形参获取列值时的一个缺陷：获取一个并不存在的列的值时会抛出SQLException
 * 目标获取不存在列的值时，返回null
 *
 * 设计方案：将ResultSet中的列名放入Map，当查询时先到map中判断是否存在，
 * 如果存在调用ResultSet原生的getString等方法取值，反之返回null
 */
public abstract class RowMapper<T> implements ParameterizedRowMapper{

    //map存储的是结果集的列名和列号对应关系
    private Map<String,Integer> columnIndexes = new HashMap<String, Integer>();

    /**
     * 每次实际取值之前先判断列名是否在结果集中
     * @param resultSet
     * @param columnName
     * @return
     * @throws SQLException
     */
    protected int findColumn(ResultSet resultSet,String columnName) throws SQLException{
        if(columnIndexes.isEmpty()){
            buildColumnIndexes(resultSet);
        }
        Integer index = columnIndexes.get(columnName);
        if (index==null) return -1;
        return index;
    }

    /**
     * 将结果集中的列名和一个迭代数字放入map中进行映射判断
     * @param resultSet
     * @throws SQLException
     */
    private void buildColumnIndexes(ResultSet resultSet) throws SQLException{
        ResultSetMetaData metaData = resultSet.getMetaData();
        int count = metaData.getColumnCount();
        //ResultSetMetaData类的getColumnName第一列是1，所以从1开始遍历
        for (int index = 1;index<count+1;index++) {
            columnIndexes.put(metaData.getColumnName(index),index);
        }
    }

    /**
     * 对应ResultSet的getString(String columnLabel)方法,string类型的返回null
     * @param resultSet
     * @param columnName
     * @return
     * @throws SQLException
     */
    public String getString(ResultSet resultSet,String columnName) throws SQLException{
        return getString(resultSet,columnName,null);
    }

    private String getString(ResultSet resultSet,String columnName,String defaultValue) throws SQLException{
        int columnIndex = findColumn(resultSet,columnName);
        if (columnIndex>0) return resultSet.getString(columnIndex);
        return defaultValue;
    }

    /**
     * 对应ResultSet的getDate(String columnLabel)方法,时间类型的返回null
     * @param resultSet
     * @param columnName
     * @return
     * @throws SQLException
     */
    public Date getDate(ResultSet resultSet,String columnName) throws SQLException{
        return getDate(resultSet,columnName,null);
    }

    private Date getDate(ResultSet resultSet,String columnName,Date defaultValue) throws SQLException{
        int columnIndex = findColumn(resultSet,columnName);
        if(columnIndex>0) return resultSet.getDate(columnIndex);
        return defaultValue;
    }

    /**
     * 对应ResultSet的getInt(String columnLabel)方法,int类型的返回0
     * @param resultSet
     * @param columnName
     * @return
     * @throws SQLException
     */
    public int getInt(ResultSet resultSet,String columnName) throws SQLException{
        return getInt(resultSet,columnName,0);
    }

    private int getInt(ResultSet resultSet,String columnName,int defaultValue) throws SQLException{
        int columnIndex = findColumn(resultSet,columnName);
        if(columnIndex>0) return resultSet.getInt(columnIndex);
        return defaultValue;
    }

    /**
     * 对应ResultSet的getDouble(String columnLabel)方法,double类型的返回0.0
     * @param resultSet
     * @param columnName
     * @return
     * @throws SQLException
     */
    public double getDouble(ResultSet resultSet,String columnName) throws SQLException{
        return getDouble(resultSet,columnName,0.0);
    }

    private double getDouble(ResultSet resultSet,String columnName,double defaultValue) throws SQLException{
        int columnIndex = findColumn(resultSet,columnName);
        if(columnIndex>0) return resultSet.getDouble(columnIndex);
        return defaultValue;
    }

    public abstract T mapRow(ResultSet resultSet,int rowNum) throws SQLException;
}
