package fr.lele14.bestshop.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InventoryUtils {

    /**
     * Vérifie si le joueur a au moins un certain nombre d'un item donné.
     */
    public static boolean hasAmount(Player player, Material material, int requiredAmount) {
        int count = 0;

        // On parcourt l'inventaire principal du joueur
        for (ItemStack item : player.getInventory().getStorageContents()) {
            if (item != null && item.getType() == material) {
                count += item.getAmount();
                // Si on a déjà atteint le montant requis, pas besoin de continuer à chercher
                if (count >= requiredAmount) {
                    return true;
                }
            }
        }

        return count >= requiredAmount;
    }
}