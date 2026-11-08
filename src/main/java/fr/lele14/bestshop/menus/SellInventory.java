package fr.lele14.bestshop.menus;

import fr.lele14.bestshop.CustomItem;
import fr.lele14.bestshop.VaultAPI;
import fr.lele14.bestshop.utils.ChatManager;
import fr.lele14.bestshop.utils.InventoryUtils;
import fr.lele14.bestshop.utils.MMAPI;
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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SellInventory implements Listener {

    private static final Map<UUID, Integer> values = new HashMap<>();
    private static final Map<UUID, CustomItem> items = new HashMap<>();
    private static final Map<UUID, Long> cooldowns = new HashMap<>();

    public static void openInventory(Player p, CustomItem item) {

        items.put(p.getUniqueId(), item);

        Inventory inv = Bukkit.createInventory(null, 3*9, MMAPI.makeCompatible("<#44a6db><b><italic:false>Menu de Vente</b></reset>"));

        String lastValue = ChatManager.getValue(p);
        int value = 0;
        values.put(p.getUniqueId(), value);

        try {

            value = Integer.parseInt(lastValue);
            values.put(p.getUniqueId(), value);

        } catch (NumberFormatException e) {

            p.sendMessage(MMAPI.makeCompatible("<#f22222>✘ La valeur saisie n'est pas un nombre entier !</reset>"));
            return;
        }

        ItemStack item1 = new ItemStack(Material.REDSTONE_LAMP); // 11
        ItemMeta meta1 = item1.getItemMeta();

        meta1.displayName(MMAPI.makeCompatible("<#f22222><b><italic:false>Retirer</b></reset>"));
        meta1.lore(MMAPI.makeListCompatible(Arrays.asList("", "<#959fab><b><italic:false>Click Gauche :</b></reset> <#f22222><italic:false>-1</reset>", "<#959fab><b><italic:false>Shift + Click Gauche :</b></reset> <#f22222><italic:false>-10</reset>")));

        item1.setItemMeta(meta1);
        inv.setItem(11, item1);

        // -

        ItemStack item2 = new ItemStack(Material.EMERALD_BLOCK); // 11
        ItemMeta meta2 = item2.getItemMeta();

        meta2.displayName(MMAPI.makeCompatible("<#78f09a><b><italic:false>Ajouter</b></reset>"));
        meta2.lore(MMAPI.makeListCompatible(Arrays.asList("", "<#959fab><b><italic:false>Click Gauche :</b></reset> <#78f09a><italic:false>+1</reset>", "<#959fab><b><italic:false>Shift + Click Gauche :</b></reset> <#78f09a><italic:false>+10</reset>")));

        item2.setItemMeta(meta2);
        inv.setItem(15, item2);

        // -

        ItemStack item3 = new ItemStack(Material.GOLD_BLOCK); // 11
        ItemMeta meta3 = item3.getItemMeta();

        meta3.displayName(MMAPI.makeCompatible("<#E8A36B><b><italic:false>Informations :</b></reset>"));
        meta3.lore(MMAPI.makeListCompatible(Arrays.asList("", "<#E8A36B><b><italic:false>Nombre :</b> " + value + "</reset>", "<#E8A36B><b><italic:false>Prix :</b> " + items.get(p.getUniqueId()).getSellPrice() * value + "$</reset>")));

        item3.setItemMeta(meta3);
        inv.setItem(13, item3);

        // -

        ItemStack item4 = new ItemStack(item.getMaterial()); // 11
        ItemMeta meta4 = item4.getItemMeta();

        meta4.displayName(MMAPI.makeCompatible("<#78f09a><b><italic:false>Vendre</b></reset>"));
        meta4.lore(MMAPI.makeListCompatible(Arrays.asList("", "<#959fab><b><italic:false>Click Gauche :</b> Vendre l'item / le block</reset>")));

        item4.setItemMeta(meta4);
        inv.setItem(26, item4);

        p.openInventory(inv);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        Player p = (Player) e.getWhoClicked();
        int slot = e.getRawSlot();
        ClickType type = e.getClick();

        int value = values.getOrDefault(p.getUniqueId(), 0);
        Inventory inventory = e.getView().getTopInventory();

        if (!e.getView().title().equals(MMAPI.makeCompatible("<#44a6db><b><italic:false>Menu de Vente</b></reset>"))) return;

        e.setCancelled(true);

        long current = System.currentTimeMillis();
        if (cooldowns.containsKey(p.getUniqueId()) && current - cooldowns.get(p.getUniqueId()) < 150) {
            return; // Ignore les clics qui surviennent moins de 150 millisecondes après le précédent
        }
        cooldowns.put(p.getUniqueId(), current);

        if (slot != 11 && slot != 15 && slot!= 26) return;

        if (slot == 11 && type == ClickType.LEFT) {

            value = Math.max(1, value - 1);
            values.put(p.getUniqueId(), value);

            ItemStack item3 = new ItemStack(Material.GOLD_BLOCK); // 11
            ItemMeta meta3 = item3.getItemMeta();

            meta3.displayName(MMAPI.makeCompatible("<#E8A36B><b><italic:false>Informations :</b></reset>"));
            meta3.lore(MMAPI.makeListCompatible(Arrays.asList("", "<#E8A36B><b><italic:false>Nombre :</b> " + value + "</reset>", "<#E8A36B><b><italic:false>Prix :</b> " + items.get(p.getUniqueId()).getSellPrice() * value + "$</reset>")));

            item3.setItemMeta(meta3);
            inventory.setItem(13, item3);

        } else if (slot == 11 && type == ClickType.SHIFT_LEFT && e.getAction() == org.bukkit.event.inventory.InventoryAction.MOVE_TO_OTHER_INVENTORY) {

            value = Math.max(1, value - 10);
            values.put(p.getUniqueId(), value);

            ItemStack item3 = new ItemStack(Material.GOLD_BLOCK); // 11
            ItemMeta meta3 = item3.getItemMeta();

            meta3.displayName(MMAPI.makeCompatible("<#E8A36B><b><italic:false>Informations :</b></reset>"));
            meta3.lore(MMAPI.makeListCompatible(Arrays.asList("", "<#E8A36B><b><italic:false>Nombre :</b> " + value + "</reset>", "<#E8A36B><b><italic:false>Prix :</b> " + items.get(p.getUniqueId()).getSellPrice() * value + "$</reset>")));

            item3.setItemMeta(meta3);
            inventory.setItem(13, item3);
        }

        if (slot == 15 && type == ClickType.LEFT) {

            value++;
            values.put(p.getUniqueId(), value);

            ItemStack item3 = new ItemStack(Material.GOLD_BLOCK); // 11
            ItemMeta meta3 = item3.getItemMeta();

            meta3.displayName(MMAPI.makeCompatible("<#E8A36B><b><italic:false>Informations :</b></reset>"));
            meta3.lore(MMAPI.makeListCompatible(Arrays.asList("", "<#E8A36B><b><italic:false>Nombre :</b> " + value + "</reset>", "<#E8A36B><b><italic:false>Prix :</b> " + items.get(p.getUniqueId()).getSellPrice() * value + "$</reset>")));

            item3.setItemMeta(meta3);
            inventory.setItem(13, item3);
        }
        else if (slot == 15 && type == ClickType.SHIFT_LEFT && e.getAction() == org.bukkit.event.inventory.InventoryAction.MOVE_TO_OTHER_INVENTORY) {

            value += 10;
            values.put(p.getUniqueId(), value);

            ItemStack item3 = new ItemStack(Material.GOLD_BLOCK); // 11
            ItemMeta meta3 = item3.getItemMeta();

            meta3.displayName(MMAPI.makeCompatible("<#E8A36B><b><italic:false>Informations :</b></reset>"));
            meta3.lore(MMAPI.makeListCompatible(Arrays.asList("", "<#E8A36B><b><italic:false>Nombre :</b> " + value + "</reset>", "<#E8A36B><b><italic:false>Prix :</b> " + items.get(p.getUniqueId()).getSellPrice() * value + "$</reset>")));

            item3.setItemMeta(meta3);
            inventory.setItem(13, item3);
        }

        if (slot == 26 && type == ClickType.LEFT) {

            p.closeInventory();

            if (!InventoryUtils.hasAmount(p, items.get(p.getUniqueId()).getMaterial(), value)) {

                p.sendMessage(MMAPI.makeCompatible("<#f22222>✘ Vous n'avez pas les items que vous souhaitez vendre !</reset>"));
                return;
            }

            ItemStack itemStack = new ItemStack(items.get(p.getUniqueId()).getMaterial(), value);

            VaultAPI.addMoney(p, items.get(p.getUniqueId()).getSellPrice() * value);
            p.getInventory().remove(itemStack);

            p.sendMessage(MMAPI.makeCompatible("<#78f09a>✔ Vous avez vendu <b>x" + value + " " + items.get(p.getUniqueId()).getMaterial() + "</b> pour <b>" + items.get(p.getUniqueId()).getSellPrice() * value + "</b>$ !</reset>"));

            // On retire la monney
        }
    }
}