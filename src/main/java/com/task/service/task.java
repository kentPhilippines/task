package com.task.service;

import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@Component
@Configuration
@EnableScheduling
public class task {
	Logger log = LoggerFactory.getLogger(task.class);
	@Autowired
	private OrderService orderServiceImpl;
	@Autowired
	AccountService accountServiceImpl;
	@Autowired
	/**
	 * <p>
	 * 定時任務
	 * </p>
	 * <li>1,修改賬戶每日资金冻结</li>
	 * <li>2,重新發送賬戶回調通知</li>
	 * <li>3,修改订单为失败 4分钟之前的订单修改为失败</li>
	 */
	/**
	 * <p>
	 * 5秒钟修改一次订单状态
	 * </p>
	 */
	@Scheduled(cron = "0/5 * * * * ?")
	private void orderNotify() {
		Integer second = 300;
		log.info("=================【修改订单状态为未收到回调，获取时间为: " + second + "【秒】】===============");
		orderServiceImpl.updataOrderStatus(second);
	}
	/**
	 * <p>
	 * 凌晨6 点修改账户冻结余额
	 * </p>
	 */
	@Scheduled(cron = "0 0 6 * * ?")
	private void account() {
		accountServiceImpl.updataAccountAmount();
		log.info("=================【修改賬戶凍結金額: " + LocalDateTime.now() + "】===============");
	}
}
