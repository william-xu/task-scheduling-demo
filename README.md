## 简介 
这个个简单项目主要用来介绍如何在Spring Boot项目中进行任务调度安排，主要有两种方式：
1. 使用Spring框架自身的任务调度功能来实现
2. 使用Quartz框架来实现

第二种方式需要使用数据库保存任务相关信息，这里使用的是MySQL数据库。

## 软件版本 
Java版本： 1.8
SpringBoot版本: 2.1.11.RELEASE


## Spring任务：
配置了3种任务：
* 使用注解创建的，按照固定频率执行的任务： @Scheduled(fixedRate = 1000 * 15)
* 使用注解创建的，在设定时间点执行的任务：@Scheduled(cron = "0/28 * * * * ?")
* 实现SchedulingConfigurer接口创建的任务