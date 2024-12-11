package com.castile.common.chain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author castile
 * @date 2024-12-02 01:19
 */
@Data
@AllArgsConstructor
public class OrderRegisterModel {

    private String tradeTypeCode;

    private String bizSystemCode;

    private String orderFlowNo;
}
