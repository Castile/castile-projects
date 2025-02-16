package com.castile.common.util;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

/**
 * @author castile
 * @date 2025-02-16 上午11:58
 */
public class CompletableFutureDemo {

    @Test
    void create(){
        // 零依赖
        CompletableFuture<String> completedFuture = CompletableFuture.completedFuture("hello");
        completedFuture.thenAccept(System.out::println);

        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            SmallTool.printTimeAndThread("hello");
            return "hello";
        });

        stringCompletableFuture.thenAccept(System.out::println);
    }

}
