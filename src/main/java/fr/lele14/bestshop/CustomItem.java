package fr.lele14.bestshop;

import org.bukkit.Material;

public class CustomItem {

    private Material material;
    private CustomCategory category;

    private int price;
    private int sell_price;

    public CustomItem(Material material, CustomCategory customCategory, int price, int sell_price) {

        this.material = material;
        this.category = customCategory;

        this.price = price;
        this.sell_price = price;
    }

    public Material getMaterial() {

        return material;
    }

    public CustomCategory getCategory() {

        return category;
    }

    public int getPrice() {

        return price;
    }

    public int getSellPrice() {

        return sell_price;
    }



}
