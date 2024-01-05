package dev.wplugins.waze.gerementions.punish.dao;


import dev.slickcollections.kiwizin.bungee.Bungee;
import dev.wplugins.waze.gerementions.Main;
import dev.wplugins.waze.gerementions.database.Database;
import dev.wplugins.waze.gerementions.database.MySQLDatabase;
import dev.wplugins.waze.gerementions.enums.punish.PunishType;
import dev.wplugins.waze.gerementions.enums.reason.Reason;
import dev.wplugins.waze.gerementions.punish.Punish;
import dev.wplugins.waze.gerementions.punish.service.PunishService;
import dev.wplugins.waze.gerementions.punish.service.impl.PunishServiceImpl;
import dev.wplugins.waze.gerementions.thread.PunishThread;
import lombok.Getter;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class PunishDao {

    private final PunishThread thread;
    @Getter
    private final PunishService punishService;
    @Getter
    private final List<Punish> lastHourPunishes;

    public PunishDao() {
        this.punishService = new PunishServiceImpl();
        this.lastHourPunishes = new ArrayList<>();
        this.thread = Main.getInstance().getPunishThread();
    }

    public Punish createPunish(String targetName, String stafferName, Reason reason, String proof, String type) {
        Punish punish = Punish.builder().id(UUID.randomUUID().toString().substring(0, 6)).playerName(targetName).stafferName(stafferName).reason(reason).type(type).proof(proof).date(new Date().getTime()).expire((reason.getTime() != 0 ? (System.currentTimeMillis() + reason.getTime()) : 0)).build();
        CompletableFuture.runAsync(() -> {
            while (getPunishService().getPunishes().stream().anyMatch(p -> p.getId().equals(punish.getId()))) {
                punish.setId(UUID.randomUUID().toString().substring(0, 6));
            }
            punishService.create(punish);
            lastHourPunishes.add(punish);
            Database.getInstance().execute("INSERT INTO `wPunish` VALUES (?, ?, ?, ?, ?, ?, ?, ?)", punish.getId(), punish.getPlayerName(), punish.getStafferName(), punish.getReason().name(), punish.getType(), punish.getProof(), punish.getDate(), punish.getExpire());

        }, thread);
        return punish;
    }

    public void loadPunishes() {
        try {
            Statement statement = MySQLDatabase.getInstance().getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM wPunish");
            while (resultSet.next()) {
                punishService.create(Punish.builder().id(resultSet.getString("id")).playerName(resultSet.getString("playerName")).stafferName(resultSet.getString("stafferName")).reason(Reason.valueOf(resultSet.getString("reason"))).proof(resultSet.getString("proof")).date(resultSet.getLong("date")).expire(resultSet.getLong("expires")).build());
            }
            if (!statement.isClosed()) {
                statement.close();
            }
            Main.getInstance().getLogger().info("§ePunições ativa com sucesso.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void disablePunish(String id) {
        CompletableFuture.runAsync(() -> {
            Database.getInstance().execute("DELETE FROM `wPunish` WHERE id = ?", id);
            punishService.remove(id);
            Main.getInstance().getLogger().info("Punish #" + id + " deletado com sucesso");
        }, thread);
    }
    /*public void unPunish(String id, ReasonRevogar reason) {
        try {
            Statement statement = MySQLDatabase.getInstance().getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT unpunish FROM ZyraPunish");
            while (resultSet.next()) {
                punishService.create(Punish.builder().id(resultSet.getString("id")).playerName(resultSet.getString("playerName")).stafferName(resultSet.getString("stafferName")).reason(Reason.valueOf(resultSet.getString("reason"))).proof(resultSet.getString("proof")).date(resultSet.getLong("date")).expire(resultSet.getLong("expires")).build());
            }
            if (!statement.isClosed()) {
                statement.close();
            }
            Main.getInstance().getLogger().info("§ePunições ativa com sucesso.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
    public void clearPunishes(String player) {
        getPunishService().getPunishes().stream().filter(punish -> punish.getPlayerName().equals(player)).filter(punish -> punish.getExpire() > 0 && (System.currentTimeMillis() >= punish.getExpire())).forEach(punish -> disablePunish(punish.getId()));
    }

    public Stream<Punish> isBanned(String player) {
        return punishService.getPunishes().stream().filter(punish -> punish.getPlayerName().equals(player)).filter(punish -> punish.getReason().getPunishType() == PunishType.TEMPBAN || punish.getReason().getPunishType() == PunishType.BAN).filter(Punish::isLocked);
    }

    public Stream<Punish> isMuted(String player) {
        return punishService.getPunishes().stream().filter(punish -> punish.getPlayerName().equals(player)).filter(punish -> punish.getReason().getPunishType() == PunishType.TEMPMUTE || punish.getReason().getPunishType() == PunishType.MUTE).filter(Punish::isLocked);
    }
}
