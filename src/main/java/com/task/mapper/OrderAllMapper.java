package com.task.mapper;

import com.task.entity.OrderAll;
import com.task.entity.OrderAllExample;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface OrderAllMapper {
    int countByExample(OrderAllExample example);
    int deleteByExample(OrderAllExample example);
    int deleteByPrimaryKey(@Param("id") Integer id, @Param("createTime") Date createTime);
    int insert(OrderAll record);
    int insertSelective(OrderAll record);
    List<OrderAll> selectByExample(OrderAllExample example);
    OrderAll selectByPrimaryKey(@Param("id") Integer id, @Param("createTime") Date createTime);
    int updateByExampleSelective(@Param("record") OrderAll record, @Param("example") OrderAllExample example);
    int updateByExample(@Param("record") OrderAll record, @Param("example") OrderAllExample example);
    int updateByPrimaryKeySelective(OrderAll record);
    int updateByPrimaryKey(OrderAll record);
}