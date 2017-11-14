/* 
 * Copyright (C), 2015-2017
 * File Name: @(#)App.java
 * Encoding UTF-8
 * Author: hunter@linmuxi.com
 * Version: 1.0
 * Date: 2017年11月13日
 */
package com.hunter.ktu.core;

/**
 * 功能描述
 * 
 * <p>
 * <a href="KafkaMesConstant.java"><i>View Source</i></a>
 * 
 * @author hunter@linmuxi.com
 * @version 1.0
 * @since 1.0
 * @date 2017年11月10日 下午4:26:17
 */
public class KafkaMesConstant {
    public static final String SUCCESS_CODE = "00000";
    public static final String SUCCESS_MES = "成功";

    /* kakfa-code */
    public static final String KAFKA_SEND_ERROR_CODE = "30001";
    public static final String KAFKA_NO_RESULT_CODE = "30002";
    public static final String KAFKA_NO_OFFSET_CODE = "30003";

    /* kakfa-mes */
    public static final String KAFKA_SEND_ERROR_MES = "发送消息超时,联系相关技术人员";
    public static final String KAFKA_NO_RESULT_MES = "未查询到返回结果,联系相关技术人员";
    public static final String KAFKA_NO_OFFSET_MES = "未查到返回数据的offset,联系相关技术人员";

}
