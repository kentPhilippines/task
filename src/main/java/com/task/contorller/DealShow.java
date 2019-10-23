package com.task.contorller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.task.entity.Count;
import com.task.entity.DealOrder;
import com.task.service.OrderService;

import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Entity;
import cn.hutool.db.Session;
import cn.hutool.db.SqlRunner;

@Controller
@RequestMapping("/data1234569")
public class DealShow {
	@Autowired
	private OrderService orderServiceImpl;
	Logger log = LoggerFactory.getLogger(DealShow.class);
	private static Date time;
	//默认数据源
	static Map<String,String> mapCount = new HashMap<String,String>();
	static Map<String,String> mapCountSu = new HashMap<String,String>();
	static DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@RequestMapping("/statistics")
	@Transactional
	public void show(HttpServletRequest request, HttpServletResponse response) throws ParseException, SQLException, IOException {
		String parameter = request.getParameter("day");
		int today = -1;
		if(StrUtil.isNotBlank(parameter)) {
			today = 0 - Integer.valueOf(parameter);
		}
		mapCount = new HashMap<String,String>();
		mapCountSu = new HashMap<String,String>();
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dNow = new Date(); //当前时间
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(dNow);//把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, today); //设置为前一天
		dBefore = calendar.getTime(); //得到前一天的时间
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
		String defaultStartDate = sdf.format(dBefore); //格式化前一天
		time = dBefore;
		defaultStartDate = defaultStartDate+" 00:00:00";
		String defaultEndDate = defaultStartDate.substring(0,10)+" 23:59:59";
		response.getWriter().write("前一天的起始时间是：" + defaultStartDate);
		response.getWriter().write( "<br>");
		response.getWriter().write( "<br>");
		response.getWriter().write( "<br>");
		response.getWriter().write("前一天的结束时间是：" + defaultEndDate);
		response.getWriter().write( "<br>");
		response.getWriter().write( "<br>");
		response.getWriter().write( "<br>");
		Date parse = formatter.parse(defaultStartDate);
		Date parse1 = formatter.parse(defaultEndDate);
		List<DealOrder> findBy = orderServiceImpl.findYesToDayDealDate(parse, parse1);
		int size = findBy.size();
		response.getWriter().write("单日数据："+size);
		response.getWriter().write( "<br>");
		response.getWriter().write( "<br>");
		response.getWriter().write( "<br>");
		if(size >0) {
		List<DealOrder> orderSuList = new ArrayList<DealOrder>();
		int orderSu = 0;//成功笔数
		BigDecimal orderSuAmount = new BigDecimal("0");//成功交易金额
		BigDecimal orderAmount = new BigDecimal("0");//交易金额
		Map<String,BigDecimal> productMapAll = new HashMap<String, BigDecimal>();//产品类型划分交易量
		Map<String,BigDecimal> productMap = new HashMap<String, BigDecimal>();//产品类型划分交易量
		Map<String,BigDecimal> channelMapAll = new HashMap<String, BigDecimal>();//渠道类型划分交易量
		Map<String,BigDecimal> channelMap = new HashMap<String, BigDecimal>();//渠道类型划分交易量
		Map<String,BigDecimal> appIdMapAll = new HashMap<String, BigDecimal>();//用户号类型划分交易量
		Map<String,BigDecimal> appIdMap = new HashMap<String, BigDecimal>();//用户号类型划分交易量
		Map<String,BigDecimal> appIdAndChannelMapAll = new HashMap<String, BigDecimal>();//用户号和渠道类型划分交易量
		Map<String,BigDecimal> appIdAndChannelMap = new HashMap<String, BigDecimal>();//用户号和渠道类型成功划分交易量
		Map<String,BigDecimal> appIdAndChannelMapCount = new HashMap<String, BigDecimal>();//用户号和渠道类型成功划分交易量
		Map<String,BigDecimal> appIdAndProductMapAll = new HashMap<String, BigDecimal>();//用户号和产品类型划分交易量
		Map<String,BigDecimal> appIdAndProductMap = new HashMap<String, BigDecimal>();//用户号和产品类型成功划分交易量
		Map<String,BigDecimal> channelAndProductMapAll = new HashMap<String, BigDecimal>();//渠道和产品类型划分交易量
		Map<String,BigDecimal> channelAndProductMap = new HashMap<String, BigDecimal>();//渠道和产品类型成功划分交易量
		Map<String,BigDecimal> appIdAndChannelAndProductMapAll = new HashMap<String, BigDecimal>();//渠道和产品类型成功划分交易量
		Map<String,BigDecimal> appIdAndChannelAndProductMap = new HashMap<String, BigDecimal>();//渠道和产品类型成功划分交易量
		for(DealOrder order : findBy) {
			//1处理中2成功3未收到回调4失败
			if(order.getOrderStatus().equals(2)) {
				orderSu ++;
				orderSuAmount = orderSuAmount.add(order.getDealAmount() );
				//以产品类型划分
				productMap = addMap(productMap,order.getRetain4().toString(),order.getDealAmount(),true);
				//以渠道类型划分
				
				channelMap= addMap(channelMap,order.getDealChannel().toString(),order.getDealAmount(),true);
				//以交易用户进行划分
				appIdMap = addMap(appIdMap,order.getOrderAccount().toString(),order.getDealAmount(),true) ;
				appIdAndChannelMap = addMap(appIdAndChannelMap,order.getDealChannel().toString()+order.getOrderAccount().toString(),order.getDealAmount(),true);
				appIdAndChannelMapCount = addMap(appIdAndChannelMapCount,order.getDealChannel().toString()+order.getOrderAccount().toString(),order.getDealAmount(),true);
				appIdAndProductMap = addMap(appIdAndProductMap,order.getRetain4().toString()+order.getOrderAccount().toString(),order.getDealAmount(),true);
				channelAndProductMap = addMap(channelAndProductMap,order.getDealChannel().toString()+order.getRetain4().toString(),order.getDealAmount(),true);
				appIdAndChannelAndProductMap = addMap(appIdAndChannelAndProductMap,order.getDealChannel().toString()+order.getRetain4().toString()+order.getOrderAccount().toString(),order.getDealAmount(),true);
			}
			productMapAll = addMap(productMapAll,order.getRetain4().toString(),order.getDealAmount(),false);
			//以渠道类型划分
			channelMapAll= addMap(channelMapAll,order.getDealChannel().toString(),order.getDealAmount(),false);
			//以交易用户进行划分
			appIdMapAll = addMap(appIdMapAll,order.getOrderAccount().toString(),order.getDealAmount(),false);
			appIdAndChannelMapAll = addMap(appIdAndChannelMapAll,order.getDealChannel().toString()+order.getOrderAccount().toString(),order.getDealAmount(),false);
			appIdAndProductMapAll = addMap(appIdAndProductMapAll,order.getRetain4().toString()+order.getOrderAccount().toString(),order.getDealAmount(),false);
			channelAndProductMapAll = addMap(channelAndProductMapAll,order.getDealChannel().toString()+order.getRetain4().toString(),order.getDealAmount(),false);
			appIdAndChannelAndProductMapAll = addMap(appIdAndChannelAndProductMapAll,order.getDealChannel().toString()+order.getRetain4().toString()+order.getOrderAccount().toString(),order.getDealAmount(),false);
			orderAmount = orderAmount.add(order.getDealAmount());
		}
		forMap(productMapAll,true);
		forMap(productMap,false);
		forMap(channelMapAll,true);
		forMap(channelMap,false);
		forMap(appIdMapAll,true);
		forMap(appIdMap,false);
		forMap(appIdAndChannelMapAll,true);
		forMap(appIdAndChannelMap,false);
		forMap(appIdAndProductMapAll,true);
		forMap(appIdAndProductMap,false);
		forMap(channelAndProductMapAll,true);
		forMap(channelAndProductMap,false);
		forMap(appIdAndChannelAndProductMapAll,true);
		forMap(appIdAndChannelAndProductMap,false);
		response.getWriter().write( "<br>");
		response.getWriter().write( "<br>");
		response.getWriter().write( "<br>");
		response.getWriter().write("【交易总金额："+orderAmount+" ，"
				+ "成功交易金额："+orderSuAmount+" ，"
				+ "交易总笔数："+findBy.size()+" ，"
				+ "交易成功总笔数："+orderSu+" ，"
				+ "交易时间："+sdf.format(dBefore)+" ， "
				+ "交易类型划分：所有交易"
						+ "】");
		response.getWriter().write( "<br>");
		response.getWriter().write( "<br>");
		response.getWriter().write( "<br>");
		List<Object> forMapDouble = forMapDouble(productMapAll,productMap,response);
		List<Object> forMapDouble2 = forMapDouble(channelMapAll,channelMap,response);
		List<Object> forMapDouble3 = forMapDouble(appIdMapAll,appIdMap,response);
		List<Object> forMapDouble4 = forMapDouble(appIdAndChannelMapAll,appIdAndChannelMap,response);
		List<Object> forMapDouble5 = forMapDouble(appIdAndProductMapAll,appIdAndProductMap,response);
		List<Object> forMapDouble6 = forMapDouble(channelAndProductMapAll,channelAndProductMap,response);
		List<Object> forMapDouble7 = forMapDouble(appIdAndChannelAndProductMapAll,appIdAndChannelAndProductMap,response);
		}
	}
	
	public static   Map<String,BigDecimal> addMap(Map<String,BigDecimal> map , String Key , BigDecimal Value,boolean flag) {
		BigDecimal productAmount = map.get(Key);
		if(null != productAmount) {
			BigDecimal add = productAmount.add(Value);
			map.put(Key, add);
			//  计算成功和失败的次数
		}
		 else 
			map.put(Key, Value);
		if(flag) {
			String string = mapCountSu.get(Key);
			Integer count = Integer.valueOf(StrUtil.isBlank(string)?"0":string);
			count++;
			mapCountSu.put(Key, count.toString());
		} else {
			String string = mapCount.get(Key);
			Integer count = Integer.valueOf(StrUtil.isBlank(string)?"0":string);
			count++;
			mapCount.put(Key, count.toString());
		 }
		return map;
	}
	
	public static void forMap(Map<String,BigDecimal> map ,Boolean flag ) {
		 Set<String> keySet = map.keySet();
		 for( String key : keySet) {
			 BigDecimal bigDecimal = map.get(key);
			 String a = flag?"所有":"成功";
			// response.getWriter().write("字段："+key + "，字段值："+bigDecimal+"，数据状态："+a+"数据" );
		 }
	}
	
	
	public List<Object> forMapDouble(Map<String,BigDecimal> map , Map<String,BigDecimal> mapSu ,HttpServletResponse response ) throws IOException {
		SimpleDateFormat sdfd=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
		 Set<String> keySet = map.keySet();
		List<Object> list = new ArrayList<>();
		 for(String key  : keySet) {
			 BigDecimal bigDecimal = map.get(key);
			 BigDecimal bigDecimalSu = mapSu.get(key);
			 String countSu = mapCountSu.get(key);
			 String count = mapCount.get(key);
			 response.getWriter().write("【数据粒度key："+key+"，所有数据金额："+bigDecimal+"，成功数据金额："+  bigDecimalSu+"，交易所有次数："+count+"，成功交易次数："+countSu+"】");
			 response.getWriter().write( "<br>");
				response.getWriter().write( "<br>");
				response.getWriter().write( "<br>");
			 Count countBean = new Count();
			 countBean.setAmountAll(null == bigDecimal ?"0":bigDecimal.toString());
			 countBean.setAmountSu(null == bigDecimalSu ?"0":bigDecimalSu.toString());
			 countBean.setCountAll(StrUtil.isBlank(count)?"0":count.toString());
			 countBean.setCountSu(StrUtil.isBlank(countSu)?"0":countSu.toString());
			 countBean.setKey(key);
			 String format = sdfd.format(time);
			 countBean.setTime(format);
			 Entity entity = Entity.create("statistics")
					.set("key", countBean.getKey())
					.set("amount", countBean.getAmountAll())
					.set("amountSu", countBean.getAmountSu())
					.set("dealCount", countBean.getCountAll())
			 		.set("dealCountSu", countBean.getCountSu())
			 		.set("time", countBean.getTime())
			 		;
			 String string = entity.toString();
			 list.add(entity);
		 }
		return list;
	}

}
