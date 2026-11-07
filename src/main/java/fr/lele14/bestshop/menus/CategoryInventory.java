package fr.lele14.bestshop.menus;

import fr.lele14.bestshop.CustomCategory;
import fr.lele14.bestshop.CustomItem;
import fr.lele14.bestshop.utils.ChatManager;
import fr.lele14.bestshop.utils.MMAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;


public class CategoryInventory implements Listener {

    private static Component menuTitle;
    private static List<CustomItem> itemsList;

    public static void openInventory(Player p, CustomCategory category) {

        String lastInventoryName = category.getName();
        Component inventoryName = MMAPI.makeCompatible(lastInventoryName);
        menuTitle = inventoryName;

        Inventory inv = Bukkit.createInventory(null,3 * 9, inventoryName);

        List<CustomItem> items = category.getItems();
        itemsList = items;

        int index = 0;

        for (CustomItem currentItem : items) {

            Material material = currentItem.getMaterial();
            int price = currentItem.getPrice();
            int sell_price = currentItem.getSellPrice();

            if (material == null) {

                material = Material.PAPER;
            }

            ItemStack itemStack = new ItemStack(material);
            ItemMeta meta = itemStack.getItemMeta();

            if (price == 0 && sell_price != 0) {
                // Cas 1 : Pas achetable (Prix = 0, Vente normale)
                meta.lore(MMAPI.makeListCompatible(Arrays.asList("", "<#44a6db><b><italic:false>Prix :</b> <color:#f22222>✘ Pas Achetable</reset>", "<#44a6db><b><italic:false>Vente :</b> " + sell_price + "</reset><#44a6db><italic:false>$</reset>", "", "<#959fab><b><italic:false>Click Gauche :</b> Acheter</reset>", "<#959fab><b><italic:false>Click Droit :</b> Vendre</reset>")));

            } else if (price != 0 && sell_price == 0) {
                // Cas 2 : Pas vendable (Prix normal, Vente = 0)
                meta.lore(MMAPI.makeListCompatible(Arrays.asList("", "<#44a6db><b><italic:false>Prix :</b></reset> " + price + "<#44a6db><italic:false>$</reset>", "<#44a6db><b><italic:false>Vente :</b> <color:#f22222>✘ Pas Vendable</reset>", "", "<#959fab><b><italic:false>Click Gauche :</b> Acheter</reset>", "<#959fab><b><italic:false>Click Droit :</b> Vendre</reset>")));

            } else {
                // Cas 3 : Les deux prix sont normaux (ou les deux sont à 0)
                meta.lore(MMAPI.makeListCompatible(Arrays.asList("", "<#44a6db><b><italic:false>Prix :</b></reset> " + price + "<#44a6db><italic:false>$</reset>", "<#44a6db><b><italic:false>Vente :</b></reset> " + sell_price + "<#44a6db><italic:false>$</reset>", "", "<#959fab><b><italic:false>Click Gauche :</b> Acheter</reset>", "<#959fab><b><italic:false>Click Droit :</b> Vendre</reset>")));
            }

            itemStack.setItemMeta(meta);
            inv.addItem(itemStack);

            currentItem.setPosition(index);

            index++;
        }

        p.openInventory(inv);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        Player p = (Player) e.getWhoClicked();
        int slot = e.getRawSlot();
        ClickType type = e.getClick();

        if (!e.getView().title().equals(menuTitle)) return;

        e.setCancelled(true);

        for (CustomItem currentItem : itemsList) {

            int position = currentItem.getPosition();

            if (slot == position) {

                if (type.isLeftClick() && currentItem.getPrice() != 0) {

                    ChatManager.openChat(p, value -> {

                        BuyInventory.openInventory(p, currentItem);
                    });
                }

                /*
                if (type.isRightClick()) {

                    // Menu d'Achat
                }

                 */
            }
        }
    }
}
