package me.rafaelauler.ss;


import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;


public class TempoGrupo extends Command {
	public TempoGrupo() {
	    super("tempogrupo", null, new String[] { "tempgroup" , "tsetrank" });
	  }
	  
  LuckPerms api = LuckPermsProvider.get();
  
  public void execute(CommandSender sender, String[] args) {
    if (!sender.hasPermission("cmd.group")) {
      sender.sendMessage(ChatColor.RED + "Voc� precisa ser Administrador ou superior para executar esse comando.");
      return;
    } 
    if (args.length < 3) {
      sender.sendMessage(ChatColor.DARK_AQUA + "Uso correto: /tempogrupo <Jogador> <Grupo> <Dias>.");
      return;
    } 
    net.luckperms.api.model.group.Group group = api.getGroupManager().getGroup(args[1]);
    if (group == null) {
      sender.sendMessage(ChatColor.RED + "Grupo n�o encontrado.");
      sender.sendMessage(ChatColor.RED + "Use default para colocar como Membro.");
      return;
    } 
    if (!sender.hasPermission("cmd.group." + args[1])) {
      sender.sendMessage(ChatColor.RED + "Voc� n�o pode setar algu�m com esse grupo");
      return;
    } 
    String target = args[0];
    ProxiedPlayer targetPlayer;
    if ((targetPlayer = ProxyServer.getInstance().getPlayer(target)) != null) {
if (targetPlayer.hasPermission("groupset.bypass")) {
	sender.sendMessage(ChatColor.RED + "Voc� n�o pode gerenciar o grupo desse Jogador!");
	sender.sendMessage(ChatColor.RED + "Ele possui um cargo acima do seu!");
    return;
}
if (targetPlayer == sender) {
	sender.sendMessage(ChatColor.RED + "Voc� n�o pode alterar seu proprio grupo!");
    return;
	
}
    }
    Integer dias = Integer.valueOf(args[2]);
    
    try {
    ProxyServer.getInstance().getPluginManager().dispatchCommand(ProxyServer.getInstance().getConsole(), "lpb user " + target + " parent clear");
    ProxyServer.getInstance().getPluginManager().dispatchCommand(ProxyServer.getInstance().getConsole(), "lpb user " + target + " parent addtemp " + args[1] + " " + dias + "d server=global");

    String textString = "�c* " + sender.getName() + " �csetou o grupo " + group.getName()
           + "�c para " + args[0] + "\n�cDura��o: (" + dias + " Dias)";
    final TextComponent text = new TextComponent(textString);
    text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/lpb user " + args[0] + " info"));
    text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("�fO Grupo setado foi: �a" + group.getName())));

    ProxyServer.getInstance().getPlayers().stream().filter(o -> o.hasPermission("wpunish.veralerta")).forEach(o -> {
        o.sendMessage(TextComponent.fromLegacyText(" "));
        o.sendMessage(text);
        o.sendMessage(TextComponent.fromLegacyText(" "));
    });
    if ((targetPlayer = ProxyServer.getInstance().getPlayer(target)) != null) {
      targetPlayer.sendMessage(ChatColor.RED + "Seu grupo foi atualizado para " + args[1] + " Dura��o: " + dias + " Dias."); 
    sender.sendMessage(ChatColor.RED + "Voc� atualizou o grupo de " + target + " para " + args[1] + " !");
    return;
  }
    }
  catch (NumberFormatException e) {
	  sender.sendMessage("Use apenas n�meros no tempo de dias!");
  }
  }
}
