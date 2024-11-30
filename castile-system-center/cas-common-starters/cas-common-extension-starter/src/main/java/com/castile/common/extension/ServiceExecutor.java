package com.castile.common.extension;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author castile
 * @date 2024-12-01 00:18
 */
public interface ServiceExecutor {

    /**
     * 执行无返回值的服务
     *
     * @param clazz 服务class
     * @param bizName 业务场景
     * @param consumer 服务触发
     * @param <S> 服务泛型
     */
    <S> void executeVoid(Class<S> clazz, String bizName, Consumer<S> consumer);

    /**
     * 执行有返回值的服务
     * @param clazz 服务class
     * @param bizName 业务场景
     * @param function 服务触发方法
     * @return R返回值
     * @param <R> 返回值泛型
     * @param <S> 服务泛型
     */
    <R, S> R execute(Class<S> clazz, String bizName, Function<S, R> function);
}
