package com.realdolmen.chiro.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

/**
 * BeanPostProcessor which injects a slf4j Logger instance into the bean.
 * Adapted from http://memorynotfound.com/spring-inject-logger-annotation-example/
 * <p>
 * Searches for KrinkelLogger annotation and generates the correct logger instance.
 * <p>
 *
 * @see KrinkelLogger
 */
@Component
public class LoggerInjector implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String name) throws BeansException {
        ReflectionUtils.doWithFields(bean.getClass(), field -> {
            // Make the field accessible if defined private
            ReflectionUtils.makeAccessible(field);

            if (field.getAnnotation(KrinkelLogger.class) != null) {
                Logger log = LoggerFactory.getLogger(bean.getClass());
                field.set(bean, log);
            }
        });

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String name) throws BeansException {
        return bean;
    }
}
