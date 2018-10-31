package com.andrei1058.bedwars.shop2.quickbuy;

import com.andrei1058.bedwars.shop2.main.CategoryContent;
import org.bukkit.inventory.ItemStack;

public class QuickBuyElement {

    private int slot;
    private CategoryContent categoryContent;
    private ItemStack itemStack;
    private String namePath, lorePath;


    public int getSlot() {
        return slot;
    }

    public CategoryContent getCategoryContent() {
        return categoryContent;
    }
}
