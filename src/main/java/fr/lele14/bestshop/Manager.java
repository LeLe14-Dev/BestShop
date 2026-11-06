package fr.lele14.bestshop;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class Manager {

    private static final List<CustomCategory> categories = new ArrayList<>();

    public static void init() {

        categories.clear();

        ConfigurationSection categorySection = Main.getPlugin().getConfig().getConfigurationSection("categories");

        if (!(categorySection == null)) {

            for (String category : categorySection.getKeys(false)) {

                String name = Main.getPlugin().getConfig().getString("categories." + category + ".name");
                String mName = Main.getPlugin().getConfig().getString("categories." + category + ".icon");

                int position = Main.getPlugin().getConfig().getInt("categories." + category + ".position");

                CustomCategory c = new CustomCategory(name, category, mName, position);
                categories.add(c);
            }
        }

        ConfigurationSection itemSection = Main.getPlugin().getConfig().getConfigurationSection("items");

        if (!(itemSection == null)) {

            for (String lastCategory : itemSection.getKeys(false)) {

                int category = Integer.parseInt(lastCategory);

                String materialName = Main.getPlugin().getConfig().getString("items." + category + ".material");
                Material material = Material.valueOf(materialName);

                String categoryID = Main.getPlugin().getConfig().getString("items." + category + ".category");
                CustomCategory categoryObject = null;

                for (CustomCategory potentialCategory : categories) {

                    String potentialCategoryID = potentialCategory.getConfigID();

                    if (potentialCategoryID.equalsIgnoreCase(categoryID)) {

                        categoryObject = potentialCategory;
                    }
                }

                int price = Main.getPlugin().getConfig().getInt("items." + category + ".purchase-price");
                int sell_price = Main.getPlugin().getConfig().getInt("items." + category + ".selling-price");

                if (!(categoryObject == null)) {

                    CustomItem item = new CustomItem(material, categoryObject, price, sell_price);
                    categoryObject.addItem(item);
                }
            }
        }
    }

    public static List<CustomCategory> getCategories() {

        return categories;
    }
}
