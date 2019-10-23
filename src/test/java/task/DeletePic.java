package task;

import java.io.File;
import java.util.Calendar;

public class DeletePic {
	public static void main(String[] args) {
		 
        /*if(null!=args){
            if(args.length!=3){
                System.out.println("传入的日期格式 依次传入 年 月  日 用空格分开");
                return;
            }else{
                Y=Integer.parseInt(args[0]);
                M=Integer.parseInt(args[1]);
                D=Integer.parseInt(args[2]);
            }
        }*/
        traverseFolder2("E:/img/");
 
    }
    public static void traverseFolder2(String path) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                System.out.println("文件夹是空的!");
                System.out.println(file.getPath());
                if(file.getPath()!=path){//防止删根目录
                    file.delete();
                }
                return;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        // System.out.println("文件�?:" + file2.getAbsolutePath());
                        traverseFolder2(file2.getAbsolutePath());
                    } else {
                        System.out.println("文件:" + file2.getAbsolutePath());
                        Calendar cal = Calendar.getInstance();
                        long time = file2.lastModified();
                        cal.setTimeInMillis(time);
                        System.out.println("修改时间:" +
                                cal.getTime().toLocaleString());
                        
                        Calendar calendar = Calendar.getInstance();
                		/* HOUR_OF_DAY 指示一天中小时 */
                		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);
                		/* MINUTE 指示一天中的某分 */
                		calendar.set(Calendar.MINUTE,(calendar.get(Calendar.MINUTE) - 20));
                        if (cal.getTimeInMillis() < calendar.getTimeInMillis()) {
                        	file2.delete();
                        	
                        }
 
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
    }



}
