package com.castile.common.util;

import cn.hutool.core.lang.Filter;
import cn.hutool.core.lang.Pair;
import cn.hutool.core.lang.mutable.MutablePair;
import cn.hutool.json.JSONObject;
import cn.hutool.json.ObjectMapper;
import org.junit.jupiter.api.Test;

/**
 * @author castile
 * @date 2024-11-30 22:13
 */
class JacksonUtilsTest {

    @Test
    public void json2Bean() {
        ObjectMapper mapper = ObjectMapper.of(User.builder().id("10001").email("email@email.com").phone("15678788989").name("tony").build());

        JSONObject jsonObject = new JSONObject();
        mapper.map(jsonObject, new Filter<MutablePair<String, Object>>() {
            @Override
            public boolean accept(MutablePair<String, Object> pair) {
                Pair<String, Object> stringObjectPair =
                        pair.get();

                System.out.println(stringObjectPair.getKey());
                if (pair.getKey().equals("name")) {
                    pair.setValue("tom");
                }
                return true;
            }
        });

        System.out.println(jsonObject);

    }
}