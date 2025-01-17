package com.castile.casspringbootweb.demos.sse;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author castile
 * @date 2025-01-17 20:27
 */
@Data
public class ChatMessage {
    private String role;
    private String content;
    private String id;

    public ChatMessage(String role, String content) {
        this.role = role;
        this.content = content;
    }
}
