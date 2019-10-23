package com.task.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.task.entity.DealOrder;
import com.task.entity.DealOrderExample;
import com.task.entity.DealOrderExample.Criteria;
import com.task.mapper.DealOrderMapper;
import com.task.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	DealOrderMapper dealOrderDao;
	
	@Override
	public void updataOrderStatus(Integer second) {
		dealOrderDao.updataOrderStatus(second);
		}

	@Override
	public List<DealOrder> findYesToDayDealDate(Date parse, Date parse1) {
		DealOrderExample example = new DealOrderExample();
		Criteria criteria = example.createCriteria();
		criteria.andCreateTimeBetween(parse, parse1);
		List<DealOrder> selectByExample = dealOrderDao.selectByExample(example);
		return selectByExample;
	}
}
