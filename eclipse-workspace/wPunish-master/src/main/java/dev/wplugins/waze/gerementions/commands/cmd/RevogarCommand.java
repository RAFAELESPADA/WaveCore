package dev.wplugins.waze.gerementions.commands.cmd;

import dev.wplugins.waze.gerementions.Main;
import dev.wplugins.waze.gerementions.commands.Commands;
import dev.wplugins.waze.gerementions.punish.Punish;
import dev.wplugins.waze.gerementions.punish.dao.PunishDao;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class RevogarCommand extends Commands {

    public RevogarCommand() {
        super("revogar");
    }

    private static PunishDao punishDao;
    private static Punish punish;

    @Override
    public void perform(CommandSender sender, String[] args) {
        String target = args[0];
        if (!sender.hasPermission("wpunish.despunir")) {
            sender.sendMessage(TextComponent.fromLegacyText("§cVocê não tem permissão para isso."));
            return;
        }
        if (args.length != 1) {
            sender.sendMessage(TextComponent.fromLegacyText("§cUtilize /revogar <jogador>."));
            return;

        } else if (sender.hasPermission("wpunish.admin") || !sender.hasPermission("wpunish.ajudante")) {
            if (punishDao.getPunishService().getPunishes().stream().anyMatch(punish -> punish.getPlayerName().equalsIgnoreCase(target)) || punishDao.getPunishService2().getPunishes().stream().anyMatch(punish -> punish.getPlayerName().equalsIgnoreCase(target))) {
                punishDao.getPunishService().getPunishes().stream().filter(punish -> punish.getPlayerName().equalsIgnoreCase(target)).forEach(punish -> {
                    punishDao.getPunishService2().getPunishes().stream().filter(punish2 -> punish.getPlayerName().equalsIgnoreCase(target)).forEach(punish2 -> {
                        sender.sendMessage(TextComponent.fromLegacyText("\n§eMotivos de revogação de punição disponíveis:"));
                        TextComponent text = new TextComponent("\n§fAplicada ao jogador incorreto\n");
                        text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wiwueioa " + punish.getId()));
                        text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wiwueioa " + punish2.getId()));
                        text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("Clique para revogar a punição.")));
                        TextComponent text1 = new TextComponent("§fMotivo de punição incorreto\n");
                        text1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wiwueioa " + punish.getId()));
                        text1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wiwueioa " + punish.getId()));
                        text1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("Clique para revogar a punição.")));
                        TextComponent text2 = new TextComponent("§fProva incorreta\n");
                        text2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wiwueioa " + punish.getId()));
                        text2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wiwueioa " + punish.getId()));
                        text2.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("Clique para revogar a punição.")));
                        TextComponent text3 = new TextComponent("§fPunição aplicada injustamente\n");
                        text3.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wiwueioa " + punish.getId()));
                        text3.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wiwueioa " + punish.getId()));
                        text3.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("Clique para revogar a punição.")));
                        TextComponent text4 = new TextComponent("§fRevisão aceita\n");
                        text4.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wiwueioa " + punish.getId()));
                        text4.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wiwueioa " + punish.getId()));
                        text4.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("Clique para revogar a punição.")));
                        sender.sendMessage(text, text1, text2, text3, text4);
                    });
                });
            } else {
                sender.sendMessage(TextComponent.fromLegacyText("§fJogador exemplar! Sem quaisquer punições ativas."));
            }
        } else if (punish.getStafferName().equals(sender.getName()) || sender.hasPermission("wpunish.checkpunir")) {
            if (punishDao.getPunishService().getPunishes().stream().anyMatch(punish -> punish.getPlayerName().equalsIgnoreCase(target))   || punishDao.getPunishService2().getPunishes().stream().anyMatch(punish -> punish.getPlayerName().equalsIgnoreCase(target))) {
                punishDao.getPunishService().getPunishes().stream().filter(punish -> punish.getPlayerName().equalsIgnoreCase(target)).forEach(punish -> {
                    punishDao.getPunishService2().getPunishes().stream().filter(punish2 -> punish.getPlayerName().equalsIgnoreCase(target)).forEach(punish2 -> {
                        sender.sendMessage(TextComponent.fromLegacyText("\n§eMotivos de revogação de punição disponíveis:"));
                        TextComponent text = new TextComponent("\n§fAplicada ao jogador incorreto\n");
                        text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wiwueioa " + punish.getId()));
                        text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wiwueioa " + punish2.getId()));
                        text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("Clique para revogar a punição.")));
                        TextComponent text1 = new TextComponent("§fMotivo de punição incorreto\n");
                        text1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wiwueioa " + punish.getId()));
                        text1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wiwueioa " + punish2.getId()));
                        text1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("Clique para revogar a punição.")));
                        TextComponent text2 = new TextComponent("§fProva incorreta\n");
                        text2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wiwueioa " + punish.getId()));
                        text2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wiwueioa " + punish2.getId()));
                        text2.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("Clique para revogar a punição.")));
                        sender.sendMessage(text, text1, text2);
                    });
                });
            } else {
                sender.sendMessage(TextComponent.fromLegacyText("§fJogador exemplar! Sem quaisquer punições ativas."));
            }
        }
    }
    static {
        punishDao = Main.getInstance().getPunishDao();
    }
}
