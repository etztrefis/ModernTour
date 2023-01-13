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
}
