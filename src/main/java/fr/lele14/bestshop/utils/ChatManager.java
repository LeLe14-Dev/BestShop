
package fr.lele14.bestshop.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import io.papermc.paper.event.player.AsyncChatEvent;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class ChatManager implements Listener {

    private static final Map<UUID, Consumer<String>> callbacks = new ConcurrentHashMap<>();
    private static final Map<UUID, String> values = new ConcurrentHashMap<>();

    public static void openChat(Player p, Consumer<String> callback) {

        p.closeInventory();

        callbacks.put(p.getUniqueId(), callback);

        p.showTitle(Title.title(
                Component.text("Entrez une valeur :")
                        .color(NamedTextColor.GREEN),
                Component.empty()
        ));

        p.sendMessage(Component.text("Écris combien tu veux acheter."));
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {

        Player p = event.getPlayer();
        UUID uuid = p.getUniqueId();

        Consumer<String> callback = callbacks.remove(uuid);

        if (callback == null) return;

        event.setCancelled(true);

        String message = PlainTextComponentSerializer.plainText()
                .serialize(event.message());

        values.put(uuid, message);

        Bukkit.getScheduler().runTask(
                Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("BestShop")),
                () -> callback.accept(message)
        );
    }

    public static String getValue(Player p) {
        return values.get(p.getUniqueId());
    }
}