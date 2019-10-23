package com.task.mapper;

import com.task.entity.Account;
import com.task.entity.AccountExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface AccountMapper {
    int countByExample(AccountExample example);
    int deleteByExample(AccountExample example);
    int deleteByPrimaryKey(Integer id);
    int insert(Account record);
    int insertSelective(Account record);
    List<Account> selectByExample(AccountExample example);
    Account selectByPrimaryKey(Integer id);
    int updateByExampleSelective(@Param("record") Account record, @Param("example") AccountExample example);
    int updateByExample(@Param("record") Account record, @Param("example") AccountExample example);
    int updateByPrimaryKeySelective(Account record);
    int updateByPrimaryKey(Account record);
    /**
     * <p>修改账户冻结金额</p>
     * @param string
     * @return
     */
	int updataByAccountMoney(String string);
}