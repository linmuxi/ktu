package com.hunter.ktu.core;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    static KafkaUtil kafkaUtil = null;
    static ClassPathXmlApplicationContext ctx = null;
    static {
        ctx = new ClassPathXmlApplicationContext("applicationContext-kafka.xml");
        kafkaUtil = ctx.getBean(KafkaUtil.class);
    }

    public static void main(String[] args) {
        initParam(args);
        MainFrame.run();
    }

    /**
     * 函数的目的/功能
    */
    private static void initParam(String[] args) {
        if (args != null && args.length > 0) {
            LOGGER.info("开始加载初始化参数{}" ,args);
            Properties prop = new Properties();
            try {
                prop.load(new FileInputStream(new File(args[0]).getAbsolutePath()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            Map<String, Object> map = CustomizedPropertyUtil.ctxPropertiesMap;
            for (Object k : prop.keySet()) {
                Object v = prop.get(k);
                String key = String.valueOf(k), value = String.valueOf(v);
                map.put(key, value);
            }
            LOGGER.info("加载初始化参数完成,{}", CustomizedPropertyUtil.ctxPropertiesMap);
        }
    }
}
