package com.castile.cas.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Reactor 是一个运行在 Java8 之上满足 Reactice 规范的响应式框架，它提供了一组响应式风格的 API
 * @author castile
 * @date 2024-12-14 19:25
 */
public class TestReactor {

    public static void main(String[] args) {
        //创建Flux序列，并声明指定数据流
        Flux<Integer> integerFlux = Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9);
        // 订阅Flux序列，只有进行订阅后才回触发数据流，不订阅就什么都不会发生
        integerFlux.subscribe(System.out::println);

        Flux<Integer> arrayFlux = Flux.fromArray(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        arrayFlux.subscribe(System.out::print);
        System.out.println();

        Flux.fromIterable(Arrays.asList(1,2,3,4,5,6,7,8,9)).subscribe(System.out::print);
        System.out.println();
    }

    @Test
    void testGenerateAndCreate(){
        Flux.generate(synchronousSink -> {
            synchronousSink.next("hello");
            synchronousSink.complete();
        }).subscribe(System.out::println);

        final Random random = new Random();
        Flux.generate(ArrayList::new, (list, sink)->{
            int value = random.nextInt(100);
            list.add(value);
            sink.next(value);
            if( list.size() ==10 )
                sink.complete();
            return list;
        }).subscribe(System.out::println) ;

        Flux.create(sink -> {
            for(int i = 0; i < 10; i ++)
                sink.next(i);
            sink.complete();
        }).subscribe(System.out::println);
    }

    @Test
    void testFlux() throws IOException {
        Flux.just("Hello", "World").subscribe(System.out::println);
        Flux.fromArray(new Integer[]{1, 2, 3}).subscribe(System.out::println);
        Flux.empty().subscribe(System.out::println);
        Flux.range(1, 10).subscribe(System.out::println);
        Flux.interval(Duration.of(10, ChronoUnit.SECONDS)).subscribe(System.out::println);
        Flux.interval(Duration.of(1000, ChronoUnit.MILLIS)).subscribe(System.out::println);

        System.in.read();
    }

}



