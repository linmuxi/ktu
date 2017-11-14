/* 
 * Copyright (C), 2015-2017
 * File Name: @(#)App.java
 * Encoding UTF-8
 * Author: hunter@linmuxi.com
 * Version: 1.0
 * Date: 2017年11月13日
 */
package com.hunter.ktu.core;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.ProducerListener;

/** 
 * 功能描述
 * 
 * <p>
 * <a href="KafkaProducerListener.java"><i>View Source</i></a>
 * 
 * @author hunter@linmuxi.com
 * @version 1.0
 * @since 1.0 
 * @date 2017年11月10日 下午4:17:49
 */
public class KafkaProducerListener implements ProducerListener<String, Object>{
    protected final Logger LOGGER = LoggerFactory.getLogger("KafkaProducerListener");

    /**
     * {@inheritDoc}
     */
    public void onSuccess(String topic, Integer partition, String key, Object value, RecordMetadata recordMetadata) {
        LOGGER.info("Kafka消息发送成功,topic:{},partition:{},key:{},value:{},recordMetadata:{}",topic,partition,key,value,recordMetadata);
    }

    /**
     * {@inheritDoc}
     */
    public void onError(String topic, Integer partition, String key, Object value, Exception exception) {
        LOGGER.info("Kafka消息发送失败,topic:{},partition:{},key:{},value:{}",topic,partition,key,value,exception);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isInterestedInSuccess() {
        return true;
    }

}
