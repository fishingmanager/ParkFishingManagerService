package com.fishing.namtran.fishingmanagerservice.adapters.original_sortable;

/**
 * Created by miguel on 05/03/2016.
 */
public class ItemSortable {
    public String text;
    public int order;   // -1 = desc, 0 = none, 1 = asc

    public ItemSortable(String text) {
        this.text = text;
    }
}
