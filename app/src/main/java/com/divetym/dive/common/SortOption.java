package com.divetym.dive.common;

/**
 * Created by kali_root on 5/24/2017.
 */

public class SortOption {

    Order order;
    Sort sort;

   public enum Sort{
        ASC, DESC
    }
    public enum Order{
        date, price, group_size, rating
    }

    public SortOption(Order order, Sort sort) {
        this.order = order;
        this.sort = sort;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }
}
