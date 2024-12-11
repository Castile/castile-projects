package com.castile.common.chain.selector;

import java.util.List;

/**
 * 过滤器选择
 *
 * @author castile
 * @date 2024-12-01 23:23
 */
public interface FilterSelector {
    /**
     * 是否匹配当前的filter
     *
     * @param currFilterName filter名称
     * @return true or false
     */
    boolean matchFilter(String currFilterName);

    /**
     * 获取所有的Filter name
     * @return
     */
    List<String> getFilterNames();
}
