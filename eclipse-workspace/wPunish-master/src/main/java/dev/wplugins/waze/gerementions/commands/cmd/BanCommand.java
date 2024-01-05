package dev.wplugins.waze.gerementions.commands.cmd;

import dev.slickcollections.kiwizin.utils.StringUtils;
import dev.wplugins.waze.gerementions.Main;
import dev.wplugins.waze.gerementions.commands.Commands;
import dev.wplugins.waze.gerementions.enums.punish.PunishType;
import dev.wplugins.waze.gerementions.enums.reason.Reason;
import dev.wplugins.waze.gerementions.punish.Punish;
import dev.wplugins.waze.gerementions.punish.dao.PunishDao;
import dev.wplugins.waze.gerementions.util.Util;
import dev.wplugins.waze.gerementions.util.Webhook;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.stream.Stream;

public class BanCommand extends Commands {
    public BanCommand() {
        super("ban", "vban", "banip", "ban-ip");
    }

    public static Punish punish;
    private String webhookURL = "https://discord.com/api/webhooks/1181247723660394546/jif9m6zF5-Gylit7op4vZGX2CyEP0JiBkCKxwOOVjrvv4_JtRZsjPXUjzly6Ffi94KDg";
    @Override
    public void perform(CommandSender sender, String[] args) {
        if (!sender.hasPermission("role.gerente")) {
            sender.sendMessage(TextComponent.fromLegacyText("§cSomente Gerente ou superior podem executar este comando."));
            return;
        }

        if (args.length < 2) {
            sender.sendMessage(TextComponent.fromLegacyText("§cUtilize \"/ban <user> <duração> [motivo]"));
            return;
        }
        String target = args[0];
        if (impossibleToBan(target)) {
            sender.sendMessage(TextComponent.fromLegacyText("§cVocê não pode banir esse usuário"));
            return;
        }


        ProxyServer.getInstance().getPlayers().stream().filter(player -> player.hasPermission("utils.punir.use")).forEach(player -> {
            player.sendMessage(TextComponent.fromLegacyText("§c- " + args[0] + " §cfoi banido por " + sender.getName() +
                    "\n§c- Motivo: Violação das Diretrizes" +
                    "\n§c- Duração: Permanente\n"));
        });
        PunishDao punish = new PunishDao();
        apply(punish.createPunish(target, sender.getName(), Reason.valueOf("DIRETI"), null, PunishType.BAN.name()), ProxyServer.getInstance().getPlayer(target), sender.getName());
        Webhook webhook = new Webhook(webhookURL);
        webhook.addEmbed(
                new Webhook.EmbedObject()
                        .setDescription("Um usuário foi punido do servidor.")
                        .setThumbnail("https://mc-heads.net/avatar/" + target + "/500")
                        .setColor(Color.decode("#00A8FF"))
                        .addField("Usuário:", target, true)
                        .addField("Motivo:", "Violação das Diretrizes", true)
                        .addField("Duração:", "Eterna", false)
                        .addField("Tipo:", "Banimento", true)
                        .addField("Expira em:", "Nunca", true)
                        .addField("Provas:", "Nenhuma", true)
        );
        try {
            webhook.execute();
        } catch (IOException e) {
            Main.getInstance().getLogger().severe(e.getStackTrace().toString());
        }
    }
    private static void apply(Punish punish, ProxiedPlayer target, String staffer) {
        final String textString;
        final Reason reason = punish.getReason();
        final String proof = (punish.getProof() == null ? "Nenhuma" : punish.getProof());
        String webhookURL = "https://discord.com/api/webhooks/1181247723660394546/jif9m6zF5-Gylit7op4vZGX2CyEP0JiBkCKxwOOVjrvv4_JtRZsjPXUjzly6Ffi94KDg";
        switch (reason.getPunishType()) {
            case BAN:
                textString = "§c* " + punish.getPlayerName() + " §cfoi banido." +
                        "\n§c* Motivo: " + reason.getText() + " (Permanente)";

                break;
            case MUTE:
                textString = "§c* " + punish.getPlayerName() + " §cfoi silenciado por " + staffer +
                        "\n§c* Motivo: " + reason.getText() + " - " + proof +
                        "\n§c* Duração: Permanente\n";
                break;
            case TEMPBAN:
                textString = "§c* " + punish.getPlayerName() + " §cfoi banido." +
                        "\n§c* Motivo: " + reason.getText() + " (" + Util.fromLong(punish.getExpire()) + ")";

                break;
            case TEMPMUTE:
                textString = "\n§c* " + punish.getPlayerName() + " §cfoi silenciado por " + staffer +
                        "\n§c* Motivo: " + reason.getText() + " - " + proof +
                        "\n§c* Duração: " + Util.fromLong(punish.getExpire());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + reason.getPunishType());
        }
        final TextComponent text = new TextComponent(textString);
        text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/checkpunir " + punish.getPlayerName()));
        text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§fClique para mais informações.")));

        ProxyServer.getInstance().getPlayers().stream().filter(o -> o.hasPermission("utils.punir.use")).forEach(o -> {
            o.sendMessage(TextComponent.fromLegacyText(" "));
            o.sendMessage(text);
            o.sendMessage(TextComponent.fromLegacyText(" "));
        });
        SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        if (target != null) {
            target.sendMessage(TextComponent.fromLegacyText(" "));
            target.sendMessage(text);
            target.sendMessage(TextComponent.fromLegacyText(" "));

            if (reason.getPunishType() == PunishType.TEMPBAN) {
                target.disconnect(TextComponent.fromLegacyText("§c§lREDEFAINT\n\n§cVocê foi banido da rede\n" +
                        "\n§cMotivo: " + reason.getText() + " - " + proof +
                        "\n§cDuração: " + Util.fromLong(punish.getExpire()) +
                        "\n§cID da punição: §e#" + punish.getId() +
                        "\n\n§cAcha que a punição foi aplicada injustamente?\n§cFaça uma revisão em §ehttps://discord.gg/QQMtWKFnah"));
                return;
            }
            if (reason.getPunishType() == PunishType.BAN) {
                target.disconnect(TextComponent.fromLegacyText("§c§lREDEFAINT\n\n§cVocê foi banido da rede\n" +
                        "\n§cMotivo: " + reason.getText() + " - " + proof +
                        "\n§cDuração: Permanente" +
                        "\n§cID da punição: §e#" + punish.getId() +
                        "\n\n§cAcha que a punição foi aplicada injustamente?\n§cFaça uma revisão em §ehttps://discord.gg/QQMtWKFnah"));
            }

        }

    }
    private static boolean impossibleToBan(String nickName) {
        return Stream.of("EvilNaraku", "WazeSxD").anyMatch(s -> s.equalsIgnoreCase(nickName));
    }
}
