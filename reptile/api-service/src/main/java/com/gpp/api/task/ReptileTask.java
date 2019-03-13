package com.gpp.api.task;

import com.gpp.api.service.ReptileService;
import com.gpp.api.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * http://cron.qqe2.com/
 */
@Component
@EnableAsync
public class ReptileTask {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ReptileService reptileService;

    //每分钟执行
    @Scheduled(cron = "0 0/1 * * * ?")
    public void doTaskTest() {
        //logger.info("开始测试@@@" + DateUtil.getCurrentTime());
    }

    //每小时抓取果壳科技
    @Async
    //@Scheduled(cron = "0 0/1 * * * ?")
    public void doTaskGuoke() throws Exception {
        logger.info("开始抓取Guoke@@@" + DateUtil.getCurrentTime());
        reptileService.checkGuokr();
        logger.info("抓取结束Guoke@@@" + DateUtil.getCurrentTime());
    }

    //每1小时执行
    @Async
    //@Scheduled(cron = "0 0/1 * * * ?")
    public void doTaskOther() throws Exception {
        logger.info("开始抓取@@@" + DateUtil.getCurrentTime());
        reptileService.checkMeiShis();//5
        reptileService.checkIFeng();//4
        logger.info("抓取结束@@@" + DateUtil.getCurrentTime());
    }

    //每1小时执行
    @Async
    @Scheduled(cron = "0 0/1 * * * ?")
    public void doZimeikaTask() throws Exception {
        logger.info("开始抓取Zimeika@@@" + DateUtil.getCurrentTime());
        reptileService.checkZimeika();
        logger.info("抓取结束Zimeika@@@" + DateUtil.getCurrentTime());
    }

    @Async
    @Scheduled(cron = "0 0/1 * * * ?")
    public void doZimeikaTaskVideo() throws Exception {
        logger.info("开始抓取Zimeika@@@" + DateUtil.getCurrentTime());
        reptileService.checkZimeika4Video();
        logger.info("抓取结束Zimeika@@@" + DateUtil.getCurrentTime());
    }

}