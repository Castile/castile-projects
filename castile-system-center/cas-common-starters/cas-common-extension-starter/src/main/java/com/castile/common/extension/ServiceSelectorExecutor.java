package com.castile.common.extension;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import javax.annotation.PostConstruct;
import javax.management.ServiceNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 基于注解的方式选择服务，从而执行
 *
 * @author castile
 * @date 2024-12-01 00:28
 */
@Component
public class ServiceSelectorExecutor extends AbstractServiceExecutor implements ApplicationContextAware, SmartInitializingSingleton {
    private ApplicationContext applicationContext;
    private Map<String, Object> extBeans = new HashMap<>();

    @Override
    <S> S selectService(String bizName, Class<S> clazz) {
        if (!extBeans.containsKey(bizName)){
            throw new IllegalStateException(String.format("No such service '%s'", bizName));
        }
        return (S) extBeans.get(bizName);
    }

//    @PostConstruct
//    void init(){
//        afterSingletonsInstantiated();
//    }
    @Override
    public void afterSingletonsInstantiated() {
        // 在初始化后查询所有使用@Extension注解标注的服务bean
        Map<String, Object> beansWithAnnotation = this.applicationContext.getBeansWithAnnotation(Extension.class);
        beansWithAnnotation.entrySet().forEach(entry -> {
            Class<?> aClass = entry.getValue().getClass();
            if (AopUtils.isAopProxy(entry.getValue())) {
                aClass = ClassUtils.getUserClass(aClass);
            }
            Extension annotation = AnnotationUtils.findAnnotation(aClass, Extension.class);
            Object object = extBeans.put(annotation.bizName(), entry.getValue());

            if (Objects.nonNull(object)) {
                throw new IllegalStateException("Duplicate extension '" + annotation.bizName() + "' found");
            }
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
