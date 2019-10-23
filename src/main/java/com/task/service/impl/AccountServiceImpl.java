package com.task.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.task.entity.DayAll;
import com.task.entity.DayAllExample;
import com.task.mapper.AccountMapper;
import com.task.mapper.DayAllMapper;
import com.task.mapper.DealOrderMapper;
import com.task.mapper.OrderAllMapper;
import com.task.service.AccountService;
import com.task.util.Common;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	DealOrderMapper dealOrderDao;
	@Autowired
	OrderAllMapper orderAllDao;
	@Autowired
	AccountMapper accountDao;
	@Autowired
	DayAllMapper dayAllDao;
	@Override
	public void updataAccountAmount() {
		/**
		 * <p>修改邏輯為</p>
		 * 1,如果今天為工作日,修改所有T1和D1 凍結的金錢到  可取現餘額字段
		 * 2,如果今天為非工作日 修改D1的餘額到 可取現 字段
		 */
		DayAllExample example = new DayAllExample();
		com.task.entity.DayAllExample.Criteria createCriteria = example.createCriteria();
		createCriteria.andReqDateEqualTo(new Date());
		List<DayAll> selectByExample = dayAllDao.selectByExample(example);
		DayAll day = new DayAll();
		if(CollUtil.isNotEmpty(selectByExample)) {
			day = CollUtil.getFirst(selectByExample);
			if(ObjectUtil.isNull(day.getReqdayType())) {//查询不到今天是否为工作时间时
				day.setReqdayType(Common.DAY_ALL_WORK);//就当今天为工作日
			}
		}
		int updataByAccountMoney ; 
		if(Common.DAY_ALL_WORK.equals(day.getReqdayType())) {//工作日  清空T1 和  D1的数据,
			 updataByAccountMoney = accountDao.updataByAccountMoney("YES");
		}else{//非工作日   清空D1數據
			 updataByAccountMoney = accountDao.updataByAccountMoney(null);
		};
		// TODO 這裏要記錄修改資金的日志操作  目前沒有時間先不要寫
		//記錄日志
		}

}
