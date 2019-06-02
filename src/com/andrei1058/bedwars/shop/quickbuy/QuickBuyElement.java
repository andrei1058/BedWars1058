package com.andrei1058.bedwars.shop.quickbuy;

import com.andrei1058.bedwars.shop.ShopManager;
import com.andrei1058.bedwars.shop.main.CategoryContent;
import com.andrei1058.bedwars.shop.main.ShopCategory;

public class QuickBuyElement {

    private int slot;
    private CategoryContent categoryContent;
    private boolean loaded = false;


    public QuickBuyElement(String path, int slot){
        this.categoryContent = ShopCategory.getCategoryContent(path, ShopManager.getShop());
        if (this.categoryContent != null) this.loaded = true;
        this.slot = slot;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public int getSlot() {
        return slot;
    }

    public CategoryContent getCategoryContent() {
        return categoryContent;
    }
}
