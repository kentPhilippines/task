package com.task.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.task.entity.ResultDeal;
import com.task.util.Common;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
@Component
@Configuration //  
@EnableScheduling //  
public class test {
	static DealNumber dealNumber = new DealNumber();
	static DateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	@Scheduled(cron = "0/2 * * * * ?")
	public void tt() {
		new Thread(()-> {
			test1();
	        }).start();
		new Thread(()-> {
			test1();
		}).start();
	}
	public void test1() {
		/**
		 * 发起交易
		 */
		Map<String, String> map = new HashMap<String, String>();
		String appDesKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIS67ixkE+CU2jCgxadhiYFuCU9A9qLQK84HIJObgb7extbAFvh1Q81dJ5FPCkOsqU59lL5WZ52jxIC6Ie2HxLsCAwEAAQ==";
		String url ="http://47.91.218.103:45466/deal/payTo";
		String appid = "AC1000";
		String orderid =dealNumber.GetExceOrder();
		String applydate = formatter.format(new Date());
		String notifyurl = "www.baidu.com";
		String amount =RandomUtil.randomNumbers(5).toString();
		String passcode = "PAY1004";
		String rsasign = "";
		String sign = "appid"+appid + "orderid"+orderid + "amount"+amount + appDesKey;
		rsasign =  md5(sign);
		map.put("appid", appid);//版本号
		map.put("orderid", orderid);//订单号
		map.put("notifyurl", notifyurl);
		map.put("applydate",applydate);
		map.put("amount",amount);
		map.put("passcode",passcode);
		map.put("rsasign",rsasign);
		String param = createParam(map);
		System.out.println(param);
		String result = submitPost(url, param);
		System.out.println(result);
		/**
		 * 生成交易订单
		 */
		JSONObject parseObj = JSONUtil.parseObj(result);
	    ResultDeal bean = JSONUtil.toBean(parseObj, ResultDeal.class);
	    if("00".equals(bean.getCod())) {
	    	HttpUtil.get(bean.getReturnUrl());
	    	/**
	    	 * 交易成功生成交易流水
	    	 */
	    		 BigDecimal bigInteger = new BigDecimal(amount);
	    		 BigDecimal divide = bigInteger.divide(new BigDecimal("100"));
	    		 System.out.println("交易金额："+divide);
	    		 Map<String,Object> map1 = new HashMap<String,Object>();
	    		 map1.put("bankPhone", "17868557458");
	    		 map1.put("amount", divide);
	    		 HttpUtil.post("http://47.91.218.103:45466/notfiy/payH5", map1);
	    }
	
	}
	private static String createParam(Map<String, String> map) {
		try {
			if (map == null || map.isEmpty()) {
				return null;
			}
			//对参数名按照ASCII升序排序
			Object[] key = map.keySet().toArray();
			Arrays.sort(key);
			//生成加密原串  
			StringBuffer res = new StringBuffer(128);
			for (int i = 0; i < key.length; i++) {
				res.append(key[i] + "=" + map.get(key[i]) + "&");
			}

			String rStr = res.substring(0, res.length() - 1);
			System.out.println("请求接口加密原串 = " + rStr);
			return rStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String submitPost(String url, String params) {
		StringBuffer responseMessage = null;
		java.net.HttpURLConnection connection = null;
		java.net.URL reqUrl = null;
		OutputStreamWriter reqOut = null;
		InputStream in = null;
		BufferedReader br = null;
		int charCount = -1;
		try {
			responseMessage = new StringBuffer(128);
			reqUrl = new java.net.URL(url);
			connection = (java.net.HttpURLConnection) reqUrl.openConnection();
			connection.setReadTimeout(50000);
			connection.setConnectTimeout(100000);
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			reqOut = new OutputStreamWriter(connection.getOutputStream(),"UTF-8");
			reqOut.write(params);
			reqOut.flush();

			in = connection.getInputStream();
			br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			while ((charCount = br.read()) != -1) {
				responseMessage.append((char) charCount);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (reqOut != null) {
					reqOut.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return responseMessage.toString();
	}
	public    static String md5(String str) {
		  MessageDigest md5 = null;  
		  try{  
		     md5 = MessageDigest.getInstance("MD5");  
		  }catch (Exception e){  
		    System.out.println(e.toString());  
		    e.printStackTrace();  
		    return "";  
		  }  
		  char[] charArray = str.toCharArray();  
		  byte[] byteArray = new byte[charArray.length];
		  for (int i = 0; i < charArray.length; i++)  
		    byteArray[i] = (byte) charArray[i];  
		  byte[] md5Bytes = md5.digest(byteArray);  
		  StringBuffer hexValue = new StringBuffer();  
		  for (int i = 0; i < md5Bytes.length; i++){  
		    int val = ((int) md5Bytes[i]) & 0xff;  
		    if (val < 16)  
		      hexValue.append("0");  
		    hexValue.append(Integer.toHexString(val));  
		  }  
		  return hexValue.toString();
		}

}
class DealNumber  {
	// 使用单例模式，不允许直接创建实例
	DealNumber() {}
	// 创建一个空实例对象，类需要用的时候才赋值
	private  DealNumber instance = null;
	// 单例模式--懒汉模式
	public synchronized DealNumber getInstance() {
	    if (instance == null) {
	    	instance = new DealNumber();
	    }
	    return instance;
	}
	// 全局自增数
	private  int count = 1;
	// 格式化的时间字符串
	private  final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	// 获取当前时间年月日时分秒毫秒字符串
	private String getNowDateStr() {
	    return sdf.format(new Date());
	}
	// 记录上一次的时间，用来判断是否需要递增全局数
	private  String now = null;
	//定义锁对象
	private final  ReentrantLock lock = new ReentrantLock();
	//调用的方法
	private String GetRandom(final String haed){
		String Newnumber=null;
		String dateStr=getNowDateStr();
		lock.lock();//加锁
		//判断是时间是否相同
		if (dateStr.equals(now)) {
			try {
	   		 if (count >= 10000)
	            {
	                count = 1;
	            }
	       	 if (count<10) {
	       		 Newnumber = haed + getNowDateStr()+"000"+count;
	   		}else if (count<100) {
	   			Newnumber = haed + getNowDateStr()+"00"+count;
	   		}else if(count<1000){
	   			 Newnumber = haed + getNowDateStr()+"0"+count;
	   		}else  {
	   			 Newnumber = haed + getNowDateStr()+count;
	   		}
	            count++;
			} catch (Exception e) {
			}finally{
				lock.unlock();
			}
		}else{
			count=1;
			now =getNowDateStr();
			try {
				 if (count >= 10000)
	                {
	                    count = 1;
	                }
	           	 if (count<10) {
	           		 Newnumber = haed + getNowDateStr()+"000"+count;
	       		}else if (count<100) {
	       			Newnumber = haed + getNowDateStr()+"00"+count;
	       		}else if(count<1000){
	       			 Newnumber = haed + getNowDateStr()+"0"+count;
	       		}else  {
	       			 Newnumber = haed + getNowDateStr()+count;
	       		}
	                count++;
			} catch (Exception e) {
			}finally{
				lock.unlock();
			}
		}
	     return Newnumber;//返回的值
	}
	
	/**
	 * <p>交易订单</p>
	 * @return
	 */
	public String GetDealOrder(){
		return GetRandom(Common.ORDERDEAL);
	}
	/**
	 * <p>代付订单</p>
	 * @return
	 */
	public  String GetWitOrder(){
		return GetRandom(Common.ORDERWIT);
	}
	/**
	 * <p>流水订单</p>
	 * @return
	 */
	public  String GetRunOrder(){
		return GetRandom(Common.ORDERRUN);
	}
	/**
	 * <p>全局订单</p>
	 * @return
	 */
	public  String GetAllOrder(){
		return GetRandom(Common.ORDERALL);
	}
	
	/**
	 * <p>异常订单</p>
	 * @return
	 */
	public  String GetExceOrder() {
		return GetRandom(Common.ORDEREXCE);
	}
	/**
	 * <p>代付记录</p>
	 * @return
	 */
	public  String GetRecordOrder() {
		return GetRandom(Common.ORDERRECORD);
	}
}
