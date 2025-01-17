package com.castile.casspringbootweb.demos.sse.cbb;

import com.castile.casspringbootweb.demos.sse.ChatMessage;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * sse发送器管理器
 *
 * @author castile
 * @date 2025-01-17 21:42
 */
public class SseManager {
    /**
     * timeout 30min
     */
    private static final Long DEFAULT_TIME_OUT = 30 * 60 * 1000L;


    /**
     * 订阅表
     */
    private static final Map<String, CasEmitter> EMITTER_MAP = new ConcurrentHashMap<>();

    public static final String MSG_DATA_PREFIX = "data:";
    public static final String MSG_EVENT_PREFIX = "event:";

    /**
     * 获取消息发送器
     *
     * @param messageKey 本次回答的消息key
     * @return
     */
    public static CasEmitter getEmitter(String messageKey) {
        if (null == messageKey || "".equals(messageKey)) {
            return null;
        }

        CasEmitter emitter = EMITTER_MAP.get(messageKey);
        if (null == emitter) {
            emitter = new CasEmitter(DEFAULT_TIME_OUT);
            EMITTER_MAP.put(messageKey, emitter);
        }

        return emitter;
    }

    /**
     * 发送消息
     *
     * @param messageKey
     * @throws IOException
     */
    public static void sendMsg(String messageKey, String msg) throws IOException {
        CasEmitter emitter = EMITTER_MAP.get(messageKey);
        if (null != emitter) {
            emitter.send(CasEmitter.event().name("answer").data(new ChatMessage("test", msg)));

//            emitter.send(new ChatMessage("test", msg));
        }
    }

    /**
     * 发送事件
     * @param messageKey
     * @param event
     * @throws IOException
     */
    public static void sendEvent(String messageKey, String event) throws IOException {
        CasEmitter emitter = EMITTER_MAP.get(messageKey);
        if (null != emitter){
            emitter.send(CasEmitter.event().name(event));
        }
    }

    /**
     * 关闭发送器
     *
     * @param messageKey 本次回答的消息key
     */
    public static void closeEmitter(String messageKey) {
        CasEmitter emitter = EMITTER_MAP.get(messageKey);
        if (null != emitter) {
            try{
                emitter.send(SseEmitter.event().name("stop").data(""));
                emitter.complete();
                EMITTER_MAP.remove(messageKey);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
