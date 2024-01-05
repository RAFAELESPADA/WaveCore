package dev.wplugins.waze.gerementions.commands.cmd;

import dev.wplugins.waze.gerementions.Main;
import dev.wplugins.waze.gerementions.commands.Commands;
import dev.wplugins.waze.gerementions.punish.Punish;
import dev.wplugins.waze.gerementions.punish.dao.PunishDao;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CheckPunirCommand extends Commands {
    public CheckPunirCommand() {
        super("despunir", "checkpunir");
    }

    private static PunishDao punishDao;
    private static Punish punish;
    SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy 'às' HH:mm");
    @Override
    public void perform(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(TextComponent.fromLegacyText("§cApenas jogadores podem utilizar este comando."));
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;
        if (!player.hasPermission("utils.checkpunir.use")) {
            player.sendMessage(TextComponent.fromLegacyText("§fComando desconhecido."));
            return;
        }

        if (args.length != 1) {
            player.sendMessage(TextComponent.fromLegacyText("§cUtilize /checkpunir <jogador>."));
            return;
        }
        String target = args[0];
        if (sender.hasPermission("utils.checkpunir.use.ajudante")) {
            if (punishDao.getPunishService().getPunishes().stream().anyMatch(punish -> punish.getPlayerName().equalsIgnoreCase(target))) {
                sender.sendMessage(TextComponent.fromLegacyText("\n§a\uD83D\uDFE9 Ativo §c\uD83D\uDFE5 Finalizado \n"));
                punishDao.getPunishService().getPunishes().stream().filter(punish -> punish.getPlayerName().equalsIgnoreCase(target)).forEach(punish -> {
                    List<String> components = new ArrayList<>();
                    TextComponent component = new TextComponent((punish.isLocked() ? "§a§l[§a"+ SDF.format(punish.getDate()) + "§a§l] " : "§c§l[§c"+ SDF.format(punish.getDate()) + "§c§l] "));
                    for (BaseComponent baseComponent : TextComponent.fromLegacyText(punish.isLocked() ? "§a§l[§a" + punish.getReason().getText() + "§a§l]" : "§c§l[§c" + punish.getReason().getText() + "§c§l]")) {
                        component.addExtra(baseComponent);
                        baseComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§6ID: §7#"+ punish.getId() + "\n§6Nick: §7" + punish.getPlayerName() + "\n\n§6Tipo: §7" + punish.getReason().getPunishType().getText() + "\n§6Duração: §7" + (punish.getReason().getTime() > 0 ? System.currentTimeMillis() + punish.getReason().getTime() : "permanentemente") + "\n§6Data de início: §7" + SDF.format(punish.getDate()) + "\n§6Data de fim: §7" + SDF.format(System.currentTimeMillis() + punish.getReason().getTime()) + "\n§6Categoria: §7" + punish.getReason().getText() + "\n§6Motivo: §7" + punish.getReason().getText() + "\n§6Perpétua: §7" + (punish.getReason().getTime() > 0 ? "Não" : "Sim") + "\n§6Prova: §7" + (punish.getProof() == null ? "§7Sem prova" : punish.getProof()))));
                    }
                    for (BaseComponent baseComponents : TextComponent.fromLegacyText(" §f§l[§fRevogar§f§l]")) {
                        component.addExtra(baseComponents);
                        baseComponents.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§cApenas o autor da punição ou um administrador podem remover esta punição.")));
                        baseComponents.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/revogar " + punish.getPlayerName()));
                    }
                    for (BaseComponent baseComponente : TextComponent.fromLegacyText(" §f§l[§fProva§f§l]")) {
                        component.addExtra(baseComponente);
                        baseComponente.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(punish.getProof() == null ? "§fSem prova" : "§fClique para abrir: §7" + punish.getProof())));
                        baseComponente.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, punish.getProof()));
                    }
                    sender.sendMessage(component);
                });
            } else {
                sender.sendMessage(TextComponent.fromLegacyText("\n§fJogador exemplar! Sem quaisquer punições ativas."));
            }
        } else if (sender.hasPermission("role.gerente") || !sender.hasPermission("utils.checkpunir.use.ajudante")) {
            if (punishDao.getPunishService().getPunishes().stream().anyMatch(punish -> punish.getPlayerName().equalsIgnoreCase(target))) {
                sender.sendMessage(TextComponent.fromLegacyText("\n§a\uD83D\uDFE9 Ativo §c\uD83D\uDFE5 Finalizado \n"));
                punishDao.getPunishService().getPunishes().stream().filter(punish -> punish.getPlayerName().equalsIgnoreCase(target)).forEach(punish -> {
                    List<String> components = new ArrayList<>();
                    TextComponent component = new TextComponent((punish.isLocked() ? "§a§l[§a"+ SDF.format(punish.getDate()) + "§a§l] " : "§c§l[§c"+ SDF.format(punish.getDate()) + "§c§l] "));
                    for (BaseComponent baseComponent : TextComponent.fromLegacyText(punish.isLocked() ? "§a§l[§a" + punish.getReason().getText() + "§a§l]" : "§c§l[§c" + punish.getReason().getText() + "§c§l]")) {
                        component.addExtra(baseComponent);
                        baseComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§6ID: §7#"+ punish.getId() + "\n§6Nick: §7" + punish.getPlayerName() + "\n\n§6Tipo: §7" + punish.getReason().getPunishType().getText() + "\n§6Duração: §7" + (punish.getReason().getTime() > 0 ? System.currentTimeMillis() + punish.getReason().getTime() : "permanentemente") + "\n§6Data de início: §7" + SDF.format(punish.getDate()) + "\n§6Data de fim: §7" + SDF.format(System.currentTimeMillis() + punish.getReason().getTime()) + "\n§6Categoria: §7" + punish.getReason().getText() + "\n§6Motivo: §7" + punish.getReason().getText() + "\n§6Perpétua: §7" + (punish.getReason().getTime() > 0 ? "Não" : "Sim") + "\n§6Prova: §7" + (punish.getProof() == null ? "§7Sem prova" : punish.getProof()))));
                    }
                    for (BaseComponent baseComponents : TextComponent.fromLegacyText(" §f§l[§fRevogar§f§l]")) {
                        component.addExtra(baseComponents);
                        baseComponents.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§cClique para revogar essa punição.")));
                        baseComponents.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/revogar " + punish.getPlayerName()));
                    }
                    for (BaseComponent baseComponente : TextComponent.fromLegacyText(" §f§l[§fProva§f§l]")) {
                        component.addExtra(baseComponente);
                        baseComponente.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(punish.getProof() == null ? "§fSem prova" : "§fClique para abrir: §7" + punish.getProof())));
                        baseComponente.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, punish.getProof()));
                    }
                    sender.sendMessage(component);
                });
            } else {
                sender.sendMessage(TextComponent.fromLegacyText("\n§fJogador exemplar! Sem quaisquer punições ativas."));
            }
        } else if (!punish.getStafferName().equals(sender.getName()) || sender.hasPermission("utils.checkpunir.use.ajudante")) {
            if (punishDao.getPunishService().getPunishes().stream().anyMatch(punish -> punish.getPlayerName().equalsIgnoreCase(target))) {
                sender.sendMessage(TextComponent.fromLegacyText("\n§a\uD83D\uDFE9 Ativo §c\uD83D\uDFE5 Finalizado \n"));
                punishDao.getPunishService().getPunishes().stream().filter(punish -> punish.getPlayerName().equalsIgnoreCase(target)).forEach(punish -> {
                    List<String> components = new ArrayList<>();
                    TextComponent component = new TextComponent((punish.isLocked() ? "§a§l[§a"+ SDF.format(punish.getDate()) + "§a§l] " : "§c§l[§c"+ SDF.format(punish.getDate()) + "§c§l] "));
                    for (BaseComponent baseComponent : TextComponent.fromLegacyText(punish.isLocked() ? "§a§l[§a" + punish.getReason().getText() + "§a§l]" : "§c§l[§c" + punish.getReason().getText() + "§c§l]")) {
                        component.addExtra(baseComponent);
                        baseComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§6ID: §7#"+ punish.getId() + "\n§6Nick: §7" + punish.getPlayerName() + "\n\n§6Tipo: §7" + punish.getReason().getPunishType().getText() + "\n§6Duração: §7" + (punish.getReason().getTime() > 0 ? System.currentTimeMillis() + punish.getReason().getTime() : "permanentemente") + "\n§6Data de início: §7" + SDF.format(punish.getDate()) + "\n§6Data de fim: §7" + (punish.getReason().getTime() < 0 ? "Nunca" : SDF.format(System.currentTimeMillis() + punish.getReason().getTime())) + "\n§6Categoria: §7" + punish.getReason().getText() + "\n§6Motivo: §7" + punish.getReason().getText() + "\n§6Perpétua: §7" + (punish.getReason().getTime() > 0 ? "Não" : "Sim") + "\n§6Prova: §7" + (punish.getProof() == null ? "§7Sem prova" : punish.getProof()))));
                    }
                    for (BaseComponent baseComponents : TextComponent.fromLegacyText(" §f§l[§fRevogar§f§l]")) {
                        component.addExtra(baseComponents);
                        baseComponents.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§cApenas o autor da punição ou um administrador podem remover esta punição.")));
                        baseComponents.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/revogar " + punish.getPlayerName()));
                    }
                    for (BaseComponent baseComponente : TextComponent.fromLegacyText(" §f§l[§fProva§f§l]")) {
                        component.addExtra(baseComponente);
                        baseComponente.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(punish.getProof() == null ? "§fSem prova" : "§fClique para abrir: §7" + punish.getProof())));
                        baseComponente.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, punish.getProof()));
                    }
                    sender.sendMessage(component);
                });
            } else {
                sender.sendMessage(TextComponent.fromLegacyText("\n§fJogador exemplar! Sem quaisquer punições ativas."));
            }
        } else if (punish.getStafferName().equals(sender.getName()) || sender.hasPermission("utils.checkpunir.use.ajudante")) {
            if (punishDao.getPunishService().getPunishes().stream().anyMatch(punish -> punish.getPlayerName().equalsIgnoreCase(target))) {
                sender.sendMessage(TextComponent.fromLegacyText("\n§a\uD83D\uDFE9 Ativo §c\uD83D\uDFE5 Finalizado \n"));
                punishDao.getPunishService().getPunishes().stream().filter(punish -> punish.getPlayerName().equalsIgnoreCase(target)).forEach(punish -> {
                    List<String> components = new ArrayList<>();
                    TextComponent component = new TextComponent((punish.isLocked() ? "§a§l[§a"+ SDF.format(punish.getDate()) + "§a§l] " : "§c§l[§c"+ SDF.format(punish.getDate()) + "§c§l] "));
                    for (BaseComponent baseComponent : TextComponent.fromLegacyText(punish.isLocked() ? "§a§l[§a" + punish.getReason().getText() + "§a§l]" : "§c§l[§c" + punish.getReason().getText() + "§c§l]")) {
                        component.addExtra(baseComponent);
                        baseComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§6ID: §7#"+ punish.getId() + "\n§6Nick: §7" + punish.getPlayerName() + "\n\n§6Tipo: §7" + punish.getReason().getPunishType().getText() + "\n§6Duração: §7" + (punish.getReason().getTime() > 0 ? System.currentTimeMillis() + punish.getReason().getTime() : "permanentemente") + "\n§6Data de início: §7" + SDF.format(punish.getDate()) + "\n§6Data de fim: §7" + (punish.getReason().getTime() < 0 ? "Nunca" : SDF.format(System.currentTimeMillis() + punish.getReason().getTime())) + "\n§6Categoria: §7" + punish.getReason().getText() + "\n§6Motivo: §7" + punish.getReason().getText() + "\n§6Perpétua: §7" + (punish.getReason().getTime() > 0 ? "Não" : "Sim") + "\n§6Prova: §7" + (punish.getProof() == null ? "§7Sem prova" : punish.getProof()))));
                    }
                    for (BaseComponent baseComponents : TextComponent.fromLegacyText(" §f§l[§fRevogar§f§l]")) {
                        component.addExtra(baseComponents);
                        baseComponents.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§cClique para revogar essa punição.")));
                        baseComponents.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/revogar " + punish.getPlayerName()));
                    }
                    for (BaseComponent baseComponente : TextComponent.fromLegacyText(" §f§l[§fProva§f§l]")) {
                        component.addExtra(baseComponente);
                        baseComponente.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(punish.getProof() == null ? "§fSem prova" : "§fClique para abrir: §7" + punish.getProof())));
                        baseComponente.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, punish.getProof()));
                    }
                    sender.sendMessage(component);
                });
            } else {
                sender.sendMessage(TextComponent.fromLegacyText("\n§fJogador exemplar! Sem quaisquer punições ativas."));
            }
        }
        player.sendMessage(TextComponent.fromLegacyText(" "));
    }

    static {
        punishDao = Main.getInstance().getPunishDao();
    }
}

