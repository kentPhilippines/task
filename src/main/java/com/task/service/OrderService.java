package com.task.service;

import java.util.Date;
import java.util.List;

import com.task.entity.DealOrder;

public interface OrderService {

	void updataOrderStatus(Integer second);

	/**
	 * <p>根据时间查询交易数据</p>
	 * @param defaultStartDate
	 * @param defaultEndDate
	 * @return
	 */
	List<DealOrder> findYesToDayDealDate(Date parse, Date parse1);

}
