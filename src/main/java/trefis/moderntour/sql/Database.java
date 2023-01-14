package trefis.moderntour.sql;

import trefis.moderntour.Utils;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Database {
    public static Database request = new Database();

    public List<UUID> getParty() {
        List<UUID> uuids = new ArrayList<>();
        ResultSet resultSet = SQLWorker.executeQuery("SELECT uuid FROM mt_data WHERE role='party';");
        try {
            if (resultSet != null) {
                while (resultSet.next()) {
                    uuids.add(UUID.fromString(resultSet.getString(1)));
                }
                resultSet.close();
            }
        } catch (Exception e) {
            Utils.log("&c[ModernTour] Unable to get data from ResultSet of getParty request. Error: " + e.getMessage());
        }
        return uuids;
    }

    public List<UUID> getTouredPlayers() {
        ArrayList<UUID> uuids = new ArrayList<>();
        ResultSet resultSet = SQLWorker.executeQuery("SELECT uuid FROM mt_data WHERE role='toured'");
        try {
            if (resultSet != null) {
                while (resultSet.next()) {
                    uuids.add(UUID.fromString(resultSet.getString(1)));
                }
                resultSet.close();
            }
        } catch (Exception e) {
            Utils.log("&c[ModernTour] Unable to get data from ResultSet of getTouredPlayers request. Error: " + e.getMessage());
        }
        return uuids;
    }

    public List<UUID> getTourPlayers() {
        ArrayList<UUID> uuids = new ArrayList<>();
        ResultSet resultSet = SQLWorker.executeQuery("SELECT uuid FROM mt_data WHERE role='queue'");
        try {
            if (resultSet != null) {
                while (resultSet.next()) {
                    uuids.add(UUID.fromString(resultSet.getString(1)));
                }
                resultSet.close();
            }
        } catch (Exception e) {
            Utils.log("&c[ModernTour] Unable to get data from ResultSet of getTourPlayers request. Error: " + e.getMessage());
        }
        return uuids;
    }

    public void tourAddPlayer(UUID uuid) {
        try {
            SQLWorker.executeQuery("INSERT INTO mt_data (uuid) VALUES ('" + uuid + "')");
        } catch (Exception e) {
            Utils.log("&c[ModernTour] Unable to execute tourAddPlayer request. Error: " + e.getMessage());
        }
    }

    public void tourRemovePlayer(UUID uuid) {
        try {
            SQLWorker.executeQuery("UPDATE mt_data SET role='left' WHERE uuid = '" + uuid + "' AND role='queue'");
        } catch (Exception e) {
            Utils.log("&c[ModernTour] Unable to execute tourAddPlayer request. Error: " + e.getMessage());
        }
    }
}
