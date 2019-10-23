package task;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

import com.task.entity.DealOrder;
import com.task.util.Common;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.db.Entity;
import cn.hutool.db.SqlRunner;

public class testDate {
	public static void main(String[] args) throws SQLException {
	//	DE201910120012080002	ALL201910120012080001	3	100.00	4.30	95.70	1	AC1005	201910120012078459777969	223.119.58.40	\N	\N	\N	CH100000	2019-10-12 00:12:08	2019-10-12 00:12:08	PP	1	http://47.75.253.95:10002/callback/test/testCallBack	16	NO	PAY1000	\N	\N	\N	\N
		SqlRunner runner = SqlRunner.create();
		DealOrder entity = new DealOrder();
		entity.setOrderId(DealNumber().GetDealOrder());
		entity.setActualAmount(new BigDecimal("95.70"));
		entity.setAssociatedId(DealNumber().GetAllOrder());
		entity.setCreateTime(new Date());
		entity.setStatus(1);
		entity.setSubmitTime(new Date());
		entity.setSubmitSystem("task");
		entity.setDealDescribe("这是测试数据");
		entity.setExternalOrderId(DealNumber().GetExceOrder());
		entity.setDealAmount(new BigDecimal("100.00"));
		entity.setDealFee(new BigDecimal("4.30"));
		entity.setDealType(3);
		entity.setDealChannel("CH100000");
		entity.setOrderGenerationIp("127.0.0.1");
		entity.setOrderType(1);
		entity.setDealCardId("321351654312321");
		entity.setRetain1("http://47.75.253.95:10002/callback/test/test");
		entity.setRetain4("PAY1000");
		entity.setRetain2("16");
		entity.setRetain3("NO");
		entity.setOrderStatus(1);
		Entity data1 = Entity.parse(entity);
		ArrayList<Entity> newArrayList = CollectionUtil.newArrayList(data1);
		int[] result = runner.insert(newArrayList);
		
		
	}
	public static   DealNumber DealNumber() {  
		return new DealNumber();  
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


