package com.castile.casspringbootweb.demos.sse.cbb;

import org.springframework.stereotype.Service;

/**
 * @author castile
 * @date 2025-01-17 22:20
 */
@Service
public class ChatHistoryService {

    public ChatHistoryEntity saveHistory(String messageKey){

        return new ChatHistoryEntity();
    }

    public void pushExpand(String messageKey) {


    }
}
