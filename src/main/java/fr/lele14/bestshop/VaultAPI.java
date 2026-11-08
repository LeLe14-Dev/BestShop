package fr.lele14.bestshop;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class VaultAPI {

    private static Economy economy = null;

    /**
     * Initialise l'économie au démarrage du plugin (à appeler dans onEnable()).
     */
    public static boolean setupEconomy(JavaPlugin plugin) {
        if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = plugin
                .getServer()
                .getServicesManager()
                .getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return true;
    }

    /**
     * Vérifie si l'économie est active.
     */
    public static boolean isEnabled() {
        return economy != null;
    }

    /**
     * Ajoute de l'argent à un joueur.
     */
    public static boolean addMoney(OfflinePlayer player, double amount) {
        if (!isEnabled() || amount < 0) return false;

        EconomyResponse response = economy.depositPlayer(player, amount);
        return response.transactionSuccess();
    }

    /**
     * Retire de l'argent à un joueur.
     */
    public static boolean removeMoney(OfflinePlayer player, double amount) {
        if (!isEnabled() || amount < 0) return false;

        if (!hasEnough(player, amount)) {
            return false;
        }

        EconomyResponse response = economy.withdrawPlayer(player, amount);
        return response.transactionSuccess();
    }

    /**
     * Récupère le solde d'un joueur.
     */
    public static double getBalance(OfflinePlayer player) {
        if (!isEnabled()) return 0.0;
        return economy.getBalance(player);
    }

    /**
     * Vérifie si un joueur a assez d'argent.
     */
    public static boolean hasEnough(OfflinePlayer player, double amount) {
        if (!isEnabled()) return false;
        return economy.has(player, amount);
    }
}