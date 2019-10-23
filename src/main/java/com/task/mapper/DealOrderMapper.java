package com.task.mapper;

import com.task.entity.DealOrder;
import com.task.entity.DealOrderExample;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface DealOrderMapper {
    int countByExample(DealOrderExample example);
    int deleteByExample(DealOrderExample example);
    int deleteByPrimaryKey(@Param("id") Integer id, @Param("createTime") Date createTime);
    int insert(DealOrder record);
    int insertSelective(DealOrder record);
    List<DealOrder> selectByExampleWithBLOBs(DealOrderExample example);
    List<DealOrder> selectByExample(DealOrderExample example);
    DealOrder selectByPrimaryKey(@Param("id") Integer id, @Param("createTime") Date createTime);
    int updateByExampleSelective(@Param("record") DealOrder record, @Param("example") DealOrderExample example);
    int updateByExampleWithBLOBs(@Param("record") DealOrder record, @Param("example") DealOrderExample example);
    int updateByExample(@Param("record") DealOrder record, @Param("example") DealOrderExample example);
    int updateByPrimaryKeySelective(DealOrder record);
    int updateByPrimaryKeyWithBLOBs(DealOrder record);
    int updateByPrimaryKey(DealOrder record);
    /**
     * <p>更新交易订单状态</p>
     * @param second
     */
	void updataOrderStatus(Integer second);
}