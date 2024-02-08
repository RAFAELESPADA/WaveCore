package me.rafaelauler.ss;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ReplyCommand extends Command {
  private final TellCommand tellCommand;
  
  public ReplyCommand(TellCommand tellCommand) {
    super("r", null, new String[] { "reply", "responder" });
    this.tellCommand = tellCommand;
  }
  
  public void execute(CommandSender sender, String[] args) {
    if (!(sender instanceof ProxiedPlayer)) {
      sender.sendMessage("§cEsse comando só pode ser executado por jogadores.");
      return;
    } 
    ProxiedPlayer player = (ProxiedPlayer)sender;
    ProxiedPlayer lastRecipient = this.tellCommand.getLastMessageRecipient(player);
    if (lastRecipient == null) {
      sender.sendMessage("§cVocê não tem jogadores para responder.");
      return;
    } 
    if (args.length == 0) {
      sender.sendMessage("§cComando incorreto. /r <mensagem>");
      return;
    } 
    String message = String.join(" ", (CharSequence[])args);
    lastRecipient.sendMessage(TextComponent.fromLegacyText("§c§lTELL RECEBIDO §7" + player.getName() + ": §f" + message));
    sender.sendMessage(TextComponent.fromLegacyText("§c§lTELL ENVIADO §7" + lastRecipient.getName() + ": §f" + message));
  }
}

