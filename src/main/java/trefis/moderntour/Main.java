package trefis.moderntour;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import trefis.moderntour.sql.SQLWorker;

import java.io.IOException;


public class Main extends JavaPlugin {
    public boolean isTourStarted = false;
    public boolean displayJoinMessage = true;
    public boolean autoCreativeTourOwner = true;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        PluginDescriptionFile pdf = this.getDescription();

        log("");
        log("&6░█▀▄▀█ █▀▀█ █▀▀▄ █▀▀ █▀▀█ █▀▀▄ ▀▀█▀▀ █▀▀█ █──█ █▀▀█");
        log("&6░█░█░█ █──█ █──█ █▀▀ █▄▄▀ █──█ ─░█── █──█ █──█ █▄▄▀");
        log("&6░█──░█ ▀▀▀▀ ▀▀▀─ ▀▀▀ ▀─▀▀ ▀──▀ ─░█── ▀▀▀▀ ─▀▀▀ ▀─▀▀");
        log("");
        log("&aMade with &c<3 &aby &6trefis &bv." + pdf.getVersion());
        log("");

        loadConfigVariables();
        initializeDatabase();
    }

    @Override
    public void onDisable() {
        if (this.isTourStarted) {
            this.getConfig().set("isTourStarted", true);

            try {
                this.getConfig().save(this.getFile());
            } catch (IOException e) {
                this.log("&c[ModernTour] Error: Unable to save config.");
            }
        }
        log("[ModernTour] Disabled.");
        super.onDisable();
    }

    public void log(String message) {
        Bukkit.getConsoleSender().sendMessage(message.replace("&", "§"));
    }

    public void loadConfigVariables() {
        if (this.getConfig().contains("isTourStarted")) {
            this.isTourStarted = this.getConfig().getBoolean("isTourStarted");
        }
        if (this.getConfig().contains("displayJoinMessage")) {
            this.displayJoinMessage = this.getConfig().getBoolean("displayJoinMessage");
        }
        if (this.getConfig().contains("autoCreativeTourOwner")) {
            this.autoCreativeTourOwner = this.getConfig().getBoolean("autoCreativeTourOwner");
        }
    }

    public void initializeDatabase() {
        String database = this.getConfig().getString("database");
        String user = this.getConfig().getString("user");
        String password = this.getConfig().getString("password");

        if (database == null || user == null || password == null) {
            this.log("&c[ModernTour] Error: Cannot connect to a database. Check you config.yml. Plugin will be disabled.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        if (!SQLWorker.getConnection(this, database, user, password)) {
            this.log("&c[ModernTour] Error: Unable to connect to a database. Check you config.yml. Plugin will be disabled.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }
}