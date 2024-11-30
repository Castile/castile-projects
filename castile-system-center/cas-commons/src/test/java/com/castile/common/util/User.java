package com.castile.common.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author castile
 * @date 2024-11-30 22:19
 */
@Builder
@Data
@AllArgsConstructor
public class User implements Serializable {
    private String id;
    private String name;
    private String email;
    private String phone;
}
