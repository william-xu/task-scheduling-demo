package org.xwl.task.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.support.CronSequenceGenerator;

import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;

@NacosPropertySource(dataId = "task-scheduling-demo", groupId="demo", type=ConfigType.YAML, autoRefreshed = true)
@EnableScheduling
@Configuration
public class TaskConfig {	
	@NacosValue(value = "${quartz.cust.custCronExpr: \"0/35 * * * ?\"}", autoRefreshed = true)
	private String custCronExpr;
	
	public String getCustCronExpr() {
		return custCronExpr;
	}

	public void setCustCronExpr(String custCronExpr) {
		System.out.println("custCronExpr changed from " + this.custCronExpr +  " to " + custCronExpr);
		this.custCronExpr = custCronExpr;
	}

	/**
	 * 通过实现SchedulingConfigurer接口创建定时任务
	 */
	@Bean
	public SchedulingConfigurer simpleTask() {
		Runnable runnable = 
			() -> System.out.println(" Scheduled Task executing every 20 seconds in 1 minute [0, 20, 40] :: "
				+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		Trigger trigger = 
			(triggerContext) -> {
				return new CronSequenceGenerator("*/20 * * * * ?").next(new Date());
			};
		return (taskRegistrar) -> taskRegistrar.addTriggerTask(runnable, trigger);
	}

	/**
	 * 定时表达式的方式实现简单定时任务
	 */
	@Scheduled(cron = "0/28 * * * * ?")
	public void hello2() {
		System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
				+ " ---- Cron Task per 28 second in 1 minute [0, 28, 56]");
	}

	/**
	 * 固定频率的方式，下面是每15秒执行一次
	 */
	@Scheduled(fixedRate = 1000 * 15)
	public void hello1() {
		System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
				+ " ---- Simple Task per 15 second");
	}
	
}
