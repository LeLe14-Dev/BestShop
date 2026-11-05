package fr.lele14.bestshop.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.ArrayList;
import java.util.List;

public class MMAPI {

    public static Component makeCompatible(String msg) {

        return MiniMessage.miniMessage().deserialize(msg);

    }

    public static List<Component> makeListCompatible(List<String> msg) {

        List<Component> args = new ArrayList<>();

        for (String i : msg) {

            args.add(MMAPI.makeCompatible(i));
        }

        return args;
    }
}
