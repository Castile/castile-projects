package com.castile.casspringbootweb.demos.sse.cbb;


import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.joda.time.DateTime;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * 封装流信息
 *
 * @author castile
 * @date 2025-01-17 21:38
 */
@Data
public class CasEmitter extends SseEmitter {
    public CasEmitter(Long timeout) {
        super(timeout);
    }


    /**
     * 版本id
     */
    private Long versionId;

    /**
     * 用户问题
     */
    private String question;

    /**
     * 唯一消息key"
     */
    private String messageKey;

    /**
     * 当前用户
     */
    private Long currentUid;
    /**
     * 当前用户名
     */
    private String currentUserName;
    /**
     * 项目id
     */
    private Long projectId;

    /**
     * ai回答
     */
    private String aiAnswer;
    /**
     * 拓展信息
     */
    private JSONObject expand;

    /**
     * 错误信息
     */
    private JSONObject error;

    /**
     * 提问开始时间
     */
    private DateTime startTime;


    /**
     * 获取历史消息
     *
     * @return 历史消息
     */
    public JSONObject getHistory() {
        JSONObject history = new JSONObject();
        history.put("question", question);
        history.put("answer", aiAnswer);
        history.put("expand", expand);
        history.put("error", error);
        return history;
    }
}
