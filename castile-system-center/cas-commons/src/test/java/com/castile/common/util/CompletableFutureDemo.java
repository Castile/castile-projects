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

    @Test
    void testYield(){
        int day = 6;
        String dayType = switch (day) {
            case 1, 2, 3, 4, 5 -> {
                System.out.println("Weekday");
                yield "Weekday"; // 使用 yield 返回值
            }
            case 6, 7 -> {
                System.out.println("Weekend");
                yield "Weekend";
            }
            default -> {
                System.out.println("Invalid day");
                yield "Unknown";
            }
        };
        System.out.println("Day type: " + dayType);
    }
}
