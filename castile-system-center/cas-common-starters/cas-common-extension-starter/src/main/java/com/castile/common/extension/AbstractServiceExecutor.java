package com.castile.common.extension;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author castile
 * @date 2024-12-01 00:23
 */
public abstract class AbstractServiceExecutor implements ServiceExecutor {
    @Override
    public <S> void executeVoid(Class<S> clazz, String bizName, Consumer<S> consumer) {
        // 通过业务场景和class选择服务
        S service = selectService(bizName, clazz);
        // 调用服务
        consumer.accept(service);
    }

    @Override
    public <R, S> R execute(Class<S> clazz, String bizName, Function<S, R> function) {
        // 通过业务场景和class选择服务
        S service = selectService(bizName, clazz);
        // 调用服务
        return function.apply(service);
    }

    /**
     * 选择服务接口
     * @param bizName 业务场景
     * @param clazz 服务类型
     * @return 返回对应真实的服务对象
     * @param <S> 服务泛型
     */
    abstract <S> S selectService(String bizName, Class<S> clazz);
}
