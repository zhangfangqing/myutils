package com.fangqing.utils;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Spring上下文工具
 * 
 * @author qiujianfeng
 * @date 2013-8-26
 */
public class ApplicationContextUtil implements ApplicationContextAware {

    /**
     * 上下文
     */
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext contex) throws BeansException {
        this.context = contex;

    }

    /**
     * 通过bean名称获取实例
     * 
     * @param beanName bean名称
     * @return bean实例
     */
    public static Object getBeanByName(String beanName) {
        return context.getBean(beanName);
    }

    /**
     * getByType
     * 
     * @param cls
     * @return
     */
    public static <T> T getBeanByType(Class<T> cls) {
        return context.getBean(cls);
    }

    /**
     * getByType,返回多个
     * 
     * @param cls
     * @return
     */
    public static <T> Map<String, T> getBeansByType(Class<T> cls) {
        return context.getBeansOfType(cls);
    }

}
