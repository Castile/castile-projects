package com.castile.casspringbootweb.demos.sse.cbb;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.castile.casspringbootweb.demos.sse.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author castile
 * @date 2025-01-17 21:42
 */
@Slf4j
@Service
public class SseService {
    private static final String CM_URL = "http://127.0.0.1:7009/stream_json_data";
    @Autowired
    private ChatHistoryService chatHistoryService;

    public void chatTransfer(ChatMessage chatMessage) {
        String messageKey = chatMessage.getId();
        CasEmitter emitter = SseManager.getEmitter(messageKey);

        // 正式参数
        JSONObject params = new JSONObject(true);
//        params.put("versionId", emitter.getVersionId().toString());
//        params.put("userName", emitter.getCurrentUserName());
//        params.put("messageKey", emitter.getMessageKey());
//        params.put("message", emitter.getQuestion());
//        params.put("chatHistory", emitter.getHistory());
//        params.put("callbackUrl", "/xxxchat/question/callback");

        InputStream sseInputStream =
                SSEClient.getSseInputStream(CM_URL, params, SSEClient.DEFAULT_TIMEOUT);
        try {
            StringBuilder answer = new StringBuilder();
//            SseManager.sendEvent(messageKey, "messageKey");
//            SseManager.sendMsg(messageKey, messageKey);
            SseManager.sendEvent(messageKey, "answer");
            AtomicReference<Boolean> sdkError = new AtomicReference<>(false);
            SSEClient.readStream(sseInputStream, line -> {
                log.info("messageKey:{}, chatTransfer:{}", emitter.getMessageKey(), line);
                String message = "";
                if (sdkError.get()) {
                    String errorStr = line.split(SseManager.MSG_DATA_PREFIX)[1].trim();
                    if (StringUtils.isNotBlank(errorStr)) {
                        // 做一些错误处理
                        message = "算法未知错误，请稍后再试";
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("error", message);
                        emitter.setError(jsonObject);
                    }
                } else if (line.contains(SseManager.MSG_DATA_PREFIX)) {
                    message = line.split(SseManager.MSG_DATA_PREFIX)[1].trim();
                } else if (line.contains(SseManager.MSG_EVENT_PREFIX)) {
                    sdkError.set(true);
                } else {
                    message = "";
                }
                if (StringUtils.isNotBlank(message)) {
                    answer.append(message.replaceAll("\"", ""));
//                    log.info("messageKey:{}, message is :{}", emitter.getMessageKey(), message);
                    SseManager.sendMsg(messageKey, message);
                }
            });
//            emitter.setAiAnswer(answer.toString());
//
//            System.out.println("回答是： " + emitter.getAiAnswer());

            // 保存当前问答消息，自行实现
            ChatHistoryEntity message = chatHistoryService.saveHistory(messageKey);
//            SseManager.sendEvent(messageKey, "endTime");
//            SseManager.sendMsg(messageKey, DateUtil.formatDateTime(message.getEndTime()));
//            SseManager.sendEvent(messageKey, "expand");
            chatHistoryService.pushExpand(messageKey);
        } catch (IllegalStateException | IOException e) {
            log.error("push msg error", e);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            // 消息发送完或者出现异常的话，存储当前的消息，然后关闭流
            try {
                chatHistoryService.saveHistory(messageKey);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                SseManager.closeEmitter(messageKey);
                log.info("closing emitter: {}", messageKey);
            }
        }

    }
}
