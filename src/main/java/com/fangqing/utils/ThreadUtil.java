/*
 * Copyright 2016 Zhongan.com All right reserved. This software is the
 * confidential and proprietary information of Zhongan.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Zhongan.com.
 */
package com.fangqing.utils;

import java.util.concurrent.ThreadFactory;

/**
 * @功能        线程工具类
 *
 * @author zhangfangqing 
 * @date 2016年8月2日 
 * @time 下午2:19:54
 */
public class ThreadUtil {

    /**
     * 创建一个带name的ThreadFactory
     * 
     * @param run
     * @param name
     * @return
     */
    public static ThreadFactory getThreadFactory(final Runnable run, final String name) {
        return new ThreadFactory() {
            public Thread newThread(Runnable r) {
                r = run;
                return new Thread(r, name);
            }
        };
    }
}
