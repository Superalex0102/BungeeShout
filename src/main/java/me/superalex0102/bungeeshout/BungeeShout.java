package me.superalex0102.bungeeshout;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Level;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class BungeeShout extends Plugin implements Listener {
    private static BungeeShout pl;
    public static ServerInfo hub;
    public static Configuration config;
    public static ConfigurationProvider configp;
    public static File configfile;

    @Override
    public void onEnable(){
        BungeeShout.pl = this;
        loadConfig();
        this.getProxy().getLogger().log(Level.INFO, "BungeeShout is enabling!");
        this.getProxy().getPluginManager().registerCommand(this, new Shout());
        this.getProxy().getPluginManager().registerCommand(this, new ShoutReload());
        this.getProxy().getPluginManager().registerCommand(this, new ShoutBan());
        this.getProxy().getPluginManager().registerListener(this, new Shout());
        config();
        loadShoutBan();
    }

    public void onDisable(){
        this.getProxy().getLogger().log(Level.INFO, "BungeeShout is disabling!");
        saveShoutBan();
        saveConfig();
    }

    public static BungeeShout getPlugin(){
        return BungeeShout.pl;
    }

    public void loadShoutBan(){
        ShoutBan.shoutban = (List<String>) config.getList("BannedShoutBan");

    }

    public void saveShoutBan(){
        config.set("BannedShoutBan", ShoutBan.shoutban);
        saveConfig();
    }

    public void saveConfig(){
        loadConfig();
        try {
            configp.save(config, configfile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void loadConfig(){
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void config(){
        if (!getDataFolder().exists()){
            getDataFolder().mkdir();
        }
        configfile = new File(getDataFolder(), "config.yml");

        if (!configfile.exists()) {
            try {
                Files.copy(getResourceAsStream("config.yml"), configfile.toPath());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        configp = ConfigurationProvider.getProvider(YamlConfiguration.class);

        try {
            config = configp.load(configfile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}