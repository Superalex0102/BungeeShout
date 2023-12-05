package me.superalex0102.bungeeshout;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.HashMap;
import java.util.Map;

public class Shout extends Command implements Listener {

    Map<String, Long> cooldown = new HashMap<>();


    public Shout() {
        super("shout", "bungeeshout.shout");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (args.length > 0){
            StringBuffer stb = new StringBuffer();
            for(String st : args){
                stb.append(st + " ");
            }
            runShout((ProxiedPlayer) sender, stb.toString());

        } else {
            sender.sendMessage(ChatColor.RED + "You need to specify a message!");
        }
    }

    public void runShout(ProxiedPlayer p, String args){

        final long COOLDOWN = BungeeShout.config.getLong("cooldowntime") * 1000;

        if (ShoutBan.shoutban.contains(p.getName().toUpperCase())){
            p.sendMessage(ChatColor.RED + "You have been banned from using /shout");
            return;
        }

        Long l = cooldown.get(p.getName());

        if(l == null || System.currentTimeMillis() > l.longValue()){

            if (p.hasPermission("bungeeshout.staff")){
                String format = BungeeShout.config.getString("staff-format").replace("%server%", p.getServer().getInfo().getName()).replace("%playername%", p.getName()).replace("%msg%", args);
                String formatColor = ChatColor.translateAlternateColorCodes('&', format);
                for(ProxiedPlayer pl : BungeeShout.getPlugin().getProxy().getPlayers()){
                    if(pl instanceof ProxiedPlayer){
                        pl.sendMessage(formatColor);
                    }
                }
            } else {
                cooldown.put(p.getName(), System.currentTimeMillis() + COOLDOWN);
                String rawFormat = BungeeShout.config.getString("regular-format").replace("%server%", p.getServer().getInfo().getName()).replace("%msg%", "").replace("%playername%", p.getName());
                String rawFormatColor = ChatColor.translateAlternateColorCodes('&', rawFormat);
                for(ProxiedPlayer pl : BungeeShout.getPlugin().getProxy().getPlayers()){
                    if(pl instanceof ProxiedPlayer){
                        pl.sendMessage(rawFormatColor + args);
                    }
                }
            }


        } else {
            p.sendMessage(ChatColor.RED + "You may shout every " + COOLDOWN/1000 + " seconds!");
        }
    }

    @EventHandler
    public void onChatEvent(ChatEvent e){
        if (e.getMessage().startsWith("!")){
            String args = e.getMessage().replace("!", "");
            runShout((ProxiedPlayer) e.getSender(), args);
            e.setCancelled(true);
        }
    }
}
