package fr.lele14.bestshop;

import org.bukkit.Material;

public class CustomItem {

    private final Material material;
    private final CustomCategory category;

    private final int price;
    private final int sell_price;

    private int position;

    public CustomItem(Material material, CustomCategory customCategory, int price, int sell_price) {

        this.material = material;
        this.category = customCategory;

        this.price = price;
        this.sell_price = sell_price;
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

    public void setPosition(int position) {

        this.position = position;
    }

    public int getPosition() {

        return position;
    }
}
