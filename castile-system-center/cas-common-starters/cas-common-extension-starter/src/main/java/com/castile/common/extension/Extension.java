package com.castile.common.extension;

import java.lang.annotation.*;

/**
 * 扩展注解
 *
 * @author castile
 * @date 2024-12-01 00:31
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Extension {
    String bizName() default "";

    String version() default "";

    String description() default "";
}
