/* 
 * Copyright (C), 2015-2017
 * File Name: @(#)App.java
 * Encoding UTF-8
 * Author: hunter@linmuxi.com
 * Version: 1.0
 * Date: 2017年11月13日
 */
package com.hunter.ktu.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * 功能描述
 * 
 * <p>
 * <a href="KafkaUtil.java"><i>View Source</i></a>
 * 
 * @author hunter@linmuxi.com
 * @version 1.0
 * @since 1.0
 * @date 2017年11月10日 下午3:48:37
 */
public class KafkaUtil {

    //@Autowired
    //private KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * kafka发送消息模板
     * 
     * @param topic 主题
     * @param value messageValue
     * @param ifPartition 是否使用分区 0是\1不是
     * @param partitionNum 分区数 如果是否使用分区为0,分区数必须大于0
     * @param role 角色:bbc app erp...
     */
    public Map<String, Object> sendMesForTemplate(String topic, Object value, String ifPartition, Integer partitionNum,
            String role) {
        KafkaTemplate<String,Object> kafkaTemplate = App.ctx.getBean(KafkaTemplate.class);
        String key = role + "-" + value.hashCode();
        if (ifPartition.equals("0")) {
            // 表示使用分区
            int partitionIndex = getPartitionIndex(key, partitionNum);
            ListenableFuture<SendResult<String, Object>> result = kafkaTemplate.send(topic, partitionIndex, key,
                    value);
            Map<String, Object> res = checkProRecord(result);
            return res;
        } else {
            ListenableFuture<SendResult<String, Object>> result = kafkaTemplate.send(topic, key, value);
            Map<String, Object> res = checkProRecord(result);
            return res;
        }
    }

    /**
     * 根据key值获取分区索引
     * 
     * @param key
     * @param partitionNum
     * @return
     */
    private int getPartitionIndex(String key, int partitionNum) {
        if (key == null) {
            Random random = new Random();
            return random.nextInt(partitionNum);
        } else {
            int result = Math.abs(key.hashCode()) % partitionNum;
            return result;
        }
    }

    /**
     * 检查发送返回结果record
     * 
     * @param res
     * @return
     */
    @SuppressWarnings("rawtypes")
    private Map<String, Object> checkProRecord(ListenableFuture<SendResult<String, Object>> res) {
        Map<String, Object> m = new HashMap<String, Object>();
        if (res != null) {
            try {
                SendResult r = res.get();// 检查result结果集检查recordMetadata的offset数据，不检查producerRecord 
                Long offsetIndex = r.getRecordMetadata().offset();
                if (offsetIndex != null && offsetIndex >= 0) {
                    m.put("code", KafkaMesConstant.SUCCESS_CODE);
                    m.put("message", KafkaMesConstant.SUCCESS_MES);
                    return m;
                } else {
                    m.put("code", KafkaMesConstant.KAFKA_NO_OFFSET_CODE);
                    m.put("message", KafkaMesConstant.KAFKA_NO_OFFSET_MES);
                    return m;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                m.put("code", KafkaMesConstant.KAFKA_SEND_ERROR_CODE);
                m.put("message", KafkaMesConstant.KAFKA_SEND_ERROR_MES);
                return m;
            } catch (ExecutionException e) {
                e.printStackTrace();
                m.put("code", KafkaMesConstant.KAFKA_SEND_ERROR_CODE);
                m.put("message", KafkaMesConstant.KAFKA_SEND_ERROR_MES);
                return m;
            }
        } else {
            m.put("code", KafkaMesConstant.KAFKA_NO_RESULT_CODE);
            m.put("message", KafkaMesConstant.KAFKA_NO_RESULT_MES);
            return m;
        }
    }

}
