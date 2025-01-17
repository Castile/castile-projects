package com.castile.casspringbootweb.demos.sse.cbb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author castile
 * @date 2025-01-17 22:20
 */
@Service
@Slf4j
public class ChatHistoryService {

    public ChatHistoryEntity saveHistory(String messageKey){
        log.info("Saving history for {}", messageKey);
        return new ChatHistoryEntity();
    }

    public void pushExpand(String messageKey) {
        log.info("Push expand message: {}", messageKey);
    }
}
