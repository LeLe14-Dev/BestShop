package fr.lele14.bestshop;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class CustomCategory {

    private final String name;
    private final String configID;

    private Material icon;
    private final String iName;

    private final int position;

    private final List<CustomItem> items = new ArrayList<>();

    public CustomCategory(String name, String configID, String mName, int position) {

        this.name = name;
        this.configID = configID;
        this.position = position;

        Material material = Material.matchMaterial(mName);
        this.iName = mName;

        if (!(material == null)) {

            this.icon = material;
        }
    }

    public String getName() {

        return name;
    }

    public String getConfigID() {

        return configID;
    }

    public Material getIcon() {

        return icon;
    }

    public String getIconName() {

        return iName;
    }

    public int getPosition() {

        return position;
    }

    public List<CustomItem> getItems() {

        return items;
    }

    public CustomCategory getObject() {

        return this;
    }

    public void addItem(CustomItem item) {

        items.add(item);
    }
}
