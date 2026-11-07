package fr.lele14.bestshop;

import fr.lele14.bestshop.commands.BestShopCommand;
import fr.lele14.bestshop.commands.ShopCommand;
import fr.lele14.bestshop.menus.BuyInventory;
import fr.lele14.bestshop.menus.CategoryInventory;
import fr.lele14.bestshop.menus.MainInventory;
import fr.lele14.bestshop.utils.ChatManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static JavaPlugin plugin;

    @Override
    public void onEnable() {

        plugin = this;

        saveDefaultConfig();

        Manager.init();

        getServer().getPluginManager().registerEvents(new MainInventory(),this);
        getServer().getPluginManager().registerEvents(new CategoryInventory(), this);
        getServer().getPluginManager().registerEvents(new ChatManager(), this);
        getServer().getPluginManager().registerEvents(new BuyInventory(), this);

        var shop = getCommand("adminshop");

        if (!(shop == null)) {

            shop.setExecutor(new ShopCommand());
        }

        var main = getCommand("bestshop");

        if (!(main == null)) {

            main.setExecutor(new BestShopCommand());
            main.setTabCompleter(new BestShopCommand());
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static JavaPlugin getPlugin() {

        return plugin;
    }
}
