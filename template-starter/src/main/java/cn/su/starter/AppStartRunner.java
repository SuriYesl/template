package cn.su.starter;

import cn.su.core.util.SystemUtil;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 启动之后需要执行的任务
 */
@Component
public class AppStartRunner implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(AppStartRunner.class);

    @Override
    public void run(ApplicationArguments args) {
        String[] sourceArgs = args.getSourceArgs();
        logger.info("进程PID:" + SystemUtil.PID);
        logger.info("totalMemory:" + (SystemUtil.RUNTIME.totalMemory() >> 20) + "MB");
        logger.info("maxMemory:" + (SystemUtil.RUNTIME.maxMemory() >> 20) + "MB");
        logger.info("freeMemory:" + (SystemUtil.RUNTIME.freeMemory() >> 20) + "MB");
        logger.info("启动参数:");
        for (String arg : sourceArgs) {
            logger.info(arg);
        }
        logger.info("----------------------无敌分割线----------------------");
        initBoClassField();
    }

    private void initBoClassField() {
        logger.info("======================start to cache class fields======================");
        long startTime = System.currentTimeMillis();
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .forPackages("cn.su.dao.entity")
                .addScanners(new SubTypesScanner()));
        //try {
        //    Set<Class<? extends BaseBo>> subTypes = reflections.getSubTypesOf(BaseBo.class);
        //    for (Class boClass : subTypes) {
        //        SqlSpellUtil.getClassFieldArray(boClass);
        //        SqlSpellUtil.getClassFields(boClass);
        //    }
        //} catch (Exception e) {
        //    e.printStackTrace();
        //    throw new BusinessException(e);
        //}
        logger.info("======================cache class fields end, use time: " + (System.currentTimeMillis() - startTime) + "ms======================");
    }
}
