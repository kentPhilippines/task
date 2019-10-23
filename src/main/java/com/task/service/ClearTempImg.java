package com.task.service;

import java.io.File;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * <p>删除图片定时处理类</p>
 * @author K
 *
 */
@Component
@Configuration  
@EnableScheduling 
public class ClearTempImg { 
	static Logger log = LoggerFactory.getLogger(ClearTempImg.class);
	@Scheduled(cron = "30 10 * * * ?")//每小时10分30秒触发
	public void deleteImg() {
		deleteImg("/img/");
	}
	public void deleteImg(String path) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
            	log.info("文件夹是空的!");
            	log.info(file.getPath());
                if(file.getPath()!=path){//防止删根目录
                    file.delete();
                }
                return;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {// 是否存在文件
                    	deleteImg(file2.getAbsolutePath());
                    } else {
                    	log.info("文件:" + file2.getAbsolutePath());
                        Calendar cal = Calendar.getInstance();
                        long time = file2.lastModified();
                        cal.setTimeInMillis(time);
                    	log.info("修改时间:" +
                                cal.getTime().toLocaleString());
                        Calendar calendar = Calendar.getInstance();
                		/* HOUR_OF_DAY 指示一天中小时 */
                		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);
                		/* MINUTE 指示一天中的某分 */
                		calendar.set(Calendar.MINUTE,(calendar.get(Calendar.MINUTE) - 20));
                        if (cal.getTimeInMillis() < calendar.getTimeInMillis()) {
                        	log.info("删除文件名称："+file2.getAbsolutePath());
                        	file2.delete();
                        }
 
                    }
                }
            }
        } else {
        	log.info("文件不存在!");
        }
	}
}
