package trefis.moderntour;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import trefis.moderntour.commands.TourCommand;
import trefis.moderntour.events.Events;
import trefis.moderntour.sql.Database;
import trefis.moderntour.sql.SQLWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class Main extends JavaPlugin {
    public boolean isTourStarted = false;
    public boolean displayJoinMessage = true;
    public boolean autoCreativeTourOwner = true;
    public String partyOwner = "";
    public List<UUID> party = new ArrayList<>();
    public String version;
    public String description;
    public UUID currentPlayer;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        PluginDescriptionFile pdf = this.getDescription();
        this.version = pdf.getVersion();
        this.description = pdf.getDescription();

        Utils.log("");
        Utils.log("&6░█▀▄▀█ █▀▀█ █▀▀▄ █▀▀ █▀▀█ █▀▀▄ ▀▀█▀▀ █▀▀█ █──█ █▀▀█");
        Utils.log("&6░█░█░█ █──█ █──█ █▀▀ █▄▄▀ █──█ ─░█── █──█ █──█ █▄▄▀");
        Utils.log("&6░█──░█ ▀▀▀▀ ▀▀▀─ ▀▀▀ ▀─▀▀ ▀──▀ ─░█── ▀▀▀▀ ─▀▀▀ ▀─▀▀");
        Utils.log("");
        Utils.log("&aMade with &c<3 &aby &6trefis &bv." + this.version);
        Utils.log("");

        loadConfigVariables();
        initializeDatabase();
        loadParty();

        Optional.ofNullable(getCommand("tour"))
                .ifPresent(c -> c.setExecutor(new TourCommand(this)));
        getServer().getPluginManager().registerEvents(new Events(this), this);
    }

    @Override
    public void onDisable() {
        if (this.isTourStarted) {
            this.getConfig().set("isTourStarted", true);
            this.getConfig().set("partyOwner", this.partyOwner);
            saveConfig();
        }
        Utils.log("[ModernTour] Disabled.");
        super.onDisable();
    }

    private void loadConfigVariables() {
        if (this.getConfig().contains("isTourStarted")) {
            this.isTourStarted = this.getConfig().getBoolean("isTourStarted");
            this.partyOwner = this.getConfig().getString("partyOwner");
        }
        if (this.getConfig().contains("displayJoinMessage")) {
            this.displayJoinMessage = this.getConfig().getBoolean("displayJoinMessage");
        }
        if (this.getConfig().contains("autoCreativeTourOwner")) {
            this.autoCreativeTourOwner = this.getConfig().getBoolean("autoCreativeTourOwner");
        }
    }

    private void initializeDatabase() {
        String database = this.getConfig().getString("database");
        String user = this.getConfig().getString("user");
        String password = this.getConfig().getString("password");

        if (database == null || user == null || password == null) {
            Utils.log("&c[ModernTour] Error: Cannot connect to a database. Check you config.yml. Plugin will be disabled.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        if (!SQLWorker.getConnection(database, user, password)) {
            Utils.log("&c[ModernTour] Error: Unable to connect to a database. Check you config.yml. Plugin will be disabled.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    private void loadParty() {
        this.party.addAll(Database.request.getParty());
    }
}
