package task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class beforeOneHourToNowDate {
	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		/* HOUR_OF_DAY 指示一天中小时 */
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);
		/* MINUTE 指示一天中的某分 */
		calendar.set(Calendar.MINUTE,(calendar.get(Calendar.MINUTE) - 20));
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("一个小时前的时间：" + df.format(calendar.getTime()));
		System.out.println("当前的时间：" + df.format(new Date()));
	}

}
