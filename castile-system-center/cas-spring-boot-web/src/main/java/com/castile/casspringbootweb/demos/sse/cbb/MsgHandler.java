package com.castile.casspringbootweb.demos.sse.cbb;


import java.io.IOException;

/**
  * 消息处理接口
  *
 * @author castile
 * @date 2025-01-17 21:37
 */
public interface MsgHandler {

    void handle(String msg) throws IOException;
}
