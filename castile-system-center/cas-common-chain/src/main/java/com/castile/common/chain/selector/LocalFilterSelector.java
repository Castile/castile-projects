package com.castile.common.chain.selector;

import java.util.ArrayList;
import java.util.List;

/**
 * @author castile
 * @date 2024-12-01 23:25
 */
public class LocalFilterSelector implements FilterSelector{
    private List<String> filters = new ArrayList<>();

    @Override
    public boolean matchFilter(String currFilterName) {
        return filters.stream().anyMatch(s->s.equals(currFilterName));
    }

    public LocalFilterSelector() {
    }

    public LocalFilterSelector(List<String> filters) {
        this.filters = filters;
    }

    @Override
    public List<String> getFilterNames() {
        return filters;
    }

    public void addFilter(String filterName) {
        filters.add(filterName);
    }

    public void addFilters(List<String> filtersList) {
        filters.addAll(filtersList);
    }
}
