package com.andrei1058.bedwars.shop;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CategoryContent {

    private ItemStack itemStack;
    private int slot;
    private String name;
    private ContentAction contentAction;
    private static List<CategoryContent> categoryContents = new ArrayList<>();
    private ShopCategory shopCategory;

    public CategoryContent(ItemStack itemStack, int slot, String name){
        this.itemStack = itemStack;
        this.slot = slot;
        this.name = name;
    }

    public void setShopCategory(ShopCategory shopCategory) {
        this.shopCategory = shopCategory;
    }

    public ShopCategory getShopCategory() {
        return shopCategory;
    }

    public void setContentAction(ContentAction contentAction) {
        this.contentAction = contentAction;
    }

    public int getSlot() {
        return slot;
    }

    public String getName() {
        return name;
    }

    public ContentAction getContentAction() {
        return contentAction;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public static CategoryContent getByName(String name){
        for (CategoryContent sc : categoryContents){
            if (sc.getName().equalsIgnoreCase(name)){
                return sc;
            }
        }
        return null;
    }
}
