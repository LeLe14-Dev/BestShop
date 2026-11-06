package fr.lele14.bestshop.menus;

import fr.lele14.bestshop.CustomCategory;
import fr.lele14.bestshop.Main;
import fr.lele14.bestshop.Manager;
import fr.lele14.bestshop.utils.MMAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.kyori.adventure.text.Component;

import java.util.Arrays;
import java.util.List;

public class MainInventory implements Listener {

    private static Component menuTitle;

    public static void openInventory(Player p) {

        String inventoryName = Main.getPlugin().getConfig().getString("menu-name");
        List<CustomCategory> categories = Manager.getCategories();
        menuTitle = MMAPI.makeCompatible(inventoryName);

        Inventory inv = Bukkit.createInventory(null, 3*9, MMAPI.makeCompatible(inventoryName));

        for (CustomCategory category : categories) {

            String name = category.getName();
            Material icon = category.getIcon();
            int position = category.getPosition();

            if (icon == null) {

                icon = Material.PAPER;
            }

            ItemStack item = new ItemStack(icon);
            ItemMeta meta = item.getItemMeta();

            meta.displayName(MMAPI.makeCompatible(name));
            meta.lore(MMAPI.makeListCompatible(Arrays.asList("", "<#959fab><b><italic:false>Click Gauche :</b> Accéder à la</reset>", "<#959fab><italic:false>catégorie</reset>")));

            item.setItemMeta(meta);

            inv.setItem(position - 1, item);
        }

        p.openInventory(inv);

    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        Player p = (Player) e.getWhoClicked();
        int slot = e.getRawSlot();

        if (!e.getView().title().equals(menuTitle)) return;

        e.setCancelled(true);

        for (CustomCategory category : Manager.getCategories()) {

            int position = category.getPosition();

            if (slot == position - 1) {

                CategoryInventory.openInventory(p, category);
            }

        }
    }
}
