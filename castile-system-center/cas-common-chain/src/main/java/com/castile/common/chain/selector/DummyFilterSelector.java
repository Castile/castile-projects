package com.castile.common.chain.selector;

import java.util.Collections;
import java.util.List;

/**
 * @author castile
 * @date 2024-12-01 23:25
 */
public class DummyFilterSelector implements FilterSelector {

    @Override
    public boolean matchFilter(String currFilterName) {
        return false;
    }

    @Override
    public List<String> getFilterNames() {
        return Collections.emptyList();
    }
}
