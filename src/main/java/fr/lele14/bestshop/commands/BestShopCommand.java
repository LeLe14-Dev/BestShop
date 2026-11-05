package fr.lele14.bestshop.commands;

import fr.lele14.bestshop.Main;
import fr.lele14.bestshop.Manager;
import fr.lele14.bestshop.utils.MMAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BestShopCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (!(args.length == 1)) {

            sender.sendMessage(MMAPI.makeCompatible("<#f22222>✘ La commande a été mal utilisée ! Essayez plutôt la syntaxe suivante : /bs reload.</reset>"));
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {

            Main.getPlugin().reloadConfig();
            Manager.init();
            sender.sendMessage(MMAPI.makeCompatible("<#78f09a>✔ La configuration a été re-chargée !</reset>"));
        }

        if (args[0].equalsIgnoreCase("help")) {

            sender.sendMessage(MMAPI.makeCompatible("<#f22222>✘ Le développeur du plugin n'a pas configuré d'aide !</reset>"));
        }


        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (args.length == 1) {
            List<String> completions = new ArrayList<>();
            List<String> options = List.of("reload", "help");

            for (String option : options) {
                if (option.toLowerCase().startsWith(args[0].toLowerCase())) {
                    completions.add(option);
                }
            }

            return completions;
        }

        return List.of();
    }
}
