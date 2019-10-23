package task;

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

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

public class mytest {
	static DealNumber dealNumber = new DealNumber();
	static DateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	public static void main(String[] args) {
		for(int q = 0 ; q <= 10 ; q++) {
			new Thread(()-> {
				getrun( );
			}).start();
			new Thread(()-> {
				getrun( );
			}).start();
			new Thread(()-> {
				getrun( );
			}).start();
			
		}
		 /*	new Thread(()-> {
				test(RandomUtil.randomNumbers(5).toString());
		}).start();
		new Thread(()-> {
				test(RandomUtil.randomNumbers(4).toString());
		}).start();
		new Thread(()-> {
				test(RandomUtil.randomNumbers(3).toString());
		}).start();
	
		new Thread(()-> {
			boolean flag = true;
			int a = 0;
			while (flag) {
				test(RandomUtil.randomNumbers(5).toString());
				a ++;
				System.out.println(a);
				if(a > 1000000) {
					flag = false;
				}
			}
		}).start();
	 */
		  
		 //test(RandomUtil.randomNumbers(5).toString());
		 
	}
	public static void  getrun() {
		new Thread(()-> {
			run();
	        }).start();
			new Thread(()-> {
				run();
		}).start();
	}
	public static void  run() {
		new Thread(()-> {
			test(RandomUtil.randomNumbers(5).toString());
		}).start();
		new Thread(()-> {
			test(RandomUtil.randomNumbers(5).toString());
		}).start();
		new Thread(()-> {
			test(RandomUtil.randomNumbers(5).toString());
		}).start();
		new Thread(()-> {
			test(RandomUtil.randomNumbers(5).toString());
		}).start();
	} 
	public static void test(String amount1 ) {
		/**
		 * 发起交易
		 */
		Map<String, String> map = new HashMap<String, String>();
		String appDesKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKKBI6Wds9GdgCury3vNmBNiDUQ08++jV/y7cIiFTt3dg66aKIyn3A/ZcIh3dBiIrMaOA8PtbNvUMAuYVUi24pcCAwEAAQ==";
		String url ="http://127.0.0.1:8011/deal/payTo";
		String appid = "AC1006";
		String orderid =dealNumber.GetExceOrder();
		String applydate = formatter.format(new Date());
		String notifyurl = "www.baidu.com";
		String amount =amount1;
		String passcode = "PAY1003";
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
	    		 map1.put("bankPhone", "15577326326");
	    		 map1.put("amount", divide);
	    		 HttpUtil.post("http://127.0.0.1:8011/notfiy/payH5", map1);
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
