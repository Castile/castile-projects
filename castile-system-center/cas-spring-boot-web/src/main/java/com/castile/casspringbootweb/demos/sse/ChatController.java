package com.castile.casspringbootweb.demos.sse;

import cn.hutool.core.util.IdUtil;
import com.castile.casspringbootweb.demos.sse.cbb.CasEmitter;
import com.castile.casspringbootweb.demos.sse.cbb.ChatHistoryService;
import com.castile.casspringbootweb.demos.sse.cbb.SseManager;
import com.castile.casspringbootweb.demos.sse.cbb.SseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author castile
 * @date 2025-01-17 20:28
 */
@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private SseService sseService;

    private Map<String, String> msgMap = new ConcurrentHashMap<>();
    @Autowired
    private ChatHistoryService chatHistoryService;

    /**
     * send meaaage
     *
     * @param msg
     * @return
     */
    @ResponseBody
    @PostMapping("/sendMsg")
    public String sendMsg(String msg) {
        String msgId = IdUtil.simpleUUID();
        msgMap.put(msgId, msg);
        SseManager.getEmitter(msgId);
        return msgId;
    }

    /**
     * conversation
     *
     * @param msgId mapper with sendmsg
     * @return
     */
    @GetMapping(value = "/conversation/{msgId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter conversation(@PathVariable("msgId") String msgId) {
        SseEmitter emitter = new SseEmitter();


//        String msg = msgMap.remove(msgId);
        //mock chatgpt response
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    ChatMessage chatMessage = new ChatMessage("test", new String(i + ""));
                    emitter.send(chatMessage);
                    Thread.sleep(1000);
                }
                emitter.send(SseEmitter.event().name("stop").data(""));
                emitter.complete(); // close connection
            } catch (IOException | InterruptedException e) {
                emitter.completeWithError(e); // error finish
            }
        }).start();

        return emitter;
    }

    @GetMapping(value = "/chatQuery/{msgId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Object chatQuery(@PathVariable("msgId") String msgId){

        CasEmitter emitter = SseManager.getEmitter(msgId);
        // 组装消息/请求，发送给算法服务或者大模型
        String message = msgMap.get(msgId);
        emitter.setMessageKey(msgId);
        ChatMessage chatMessage = new ChatMessage("admain", message);
        chatMessage.setId(msgId);
        msgMap.remove(msgId);

        // 此处需要异步执行， 不能阻塞下面的返回
        CompletableFuture.runAsync(()->{
            sseService.chatTransfer(chatMessage);
        });
        // 立即返回
        return SseManager.getEmitter(msgId);
    }
}
