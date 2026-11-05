package fr.lele14.bestshop.commands;

import fr.lele14.bestshop.menus.MainInventory;
import fr.lele14.bestshop.utils.MMAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ShopCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (!(sender instanceof Player p)) {

            sender.sendMessage(MMAPI.makeCompatible("<#f22222>✘ Cette commande ne peut être executée seulement par un joueur !</reset>"));
            return true;
        }

        MainInventory.openInventory(p);

        p.sendMessage(MMAPI.makeCompatible("<color:#78f09a>✔ Vous avez ouvert l'admin shop !</reset>"));

        return true;
    }
}
