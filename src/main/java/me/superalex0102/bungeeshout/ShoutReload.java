package me.superalex0102.bungeeshout;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class ShoutReload extends Command {

    public ShoutReload(){
        super("shoutreload", "bungeeshout.reload");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        BungeeShout.getPlugin().saveConfig();
        BungeeShout.getPlugin().loadConfig();
        sender.sendMessage(ChatColor.GREEN + "You reloaded the BungeeShout configuration!");
    }

}
