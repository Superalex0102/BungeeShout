package me.superalex0102.bungeeshout;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class ShoutBan extends Command {

    public static List<String> shoutban = new ArrayList<>();

    public ShoutBan() {
        super("shoutban", "bungeeshout.shoutban");
    }

    public void execute(final CommandSender sender, final String[] args) {
        if (args.length == 1){
            if (!shoutban.contains(args[0].toUpperCase())){
                shoutban.add(args[0].toUpperCase());
                sender.sendMessage(ChatColor.GREEN + "You added " + args[0] + " to the shout banlist");
                BungeeShout.getPlugin().saveShoutBan();
            } else if (shoutban.contains(args[0].toUpperCase())) {
                shoutban.remove(args[0].toUpperCase());
                sender.sendMessage(ChatColor.RED + "You removed " + args[0] + " from the shout banlist");
                BungeeShout.getPlugin().saveShoutBan();
            } else {
                sender.sendMessage(ChatColor.RED + "Something went wrong!");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "/shoutban <player>");
        }
    }

}
