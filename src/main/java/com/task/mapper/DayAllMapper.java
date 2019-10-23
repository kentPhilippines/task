package com.task.mapper;

import com.task.entity.DayAll;
import com.task.entity.DayAllExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface DayAllMapper {
    int countByExample(DayAllExample example);
    int deleteByExample(DayAllExample example);
    int deleteByPrimaryKey(Integer id);
    int insert(DayAll record);
    int insertSelective(DayAll record);
    List<DayAll> selectByExample(DayAllExample example);
    DayAll selectByPrimaryKey(Integer id);
    int updateByExampleSelective(@Param("record") DayAll record, @Param("example") DayAllExample example);
    int updateByExample(@Param("record") DayAll record, @Param("example") DayAllExample example);
    int updateByPrimaryKeySelective(DayAll record);
    int updateByPrimaryKey(DayAll record);
}