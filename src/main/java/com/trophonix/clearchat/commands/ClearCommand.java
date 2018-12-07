package com.trophonix.clearchat.commands;

import com.trophonix.clearchat.ClearChat;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ClearCommand extends Command {

  private ClearChat pl;

  public ClearCommand(ClearChat pl, List<String> aliases) {
    super(aliases);
    this.pl = pl;
;  }

  @Override
  public void onCommand(CommandSender sender, String[] args) {
    if (!sender.hasPermission("clearchat.clear")) {
      sender.sendMessage(pl.prefix + pl.insufficientPermissions);
      return;
    }
    String message = null;
    if (args.length > 0) {
      message = pl.prefix + ChatColor.RESET + pl.color(String.join(" ", args));
    }
    String[] defaultMessage = pl.defaultMessage.clone();
    for (int i = 0; i < defaultMessage.length; i++)
      defaultMessage[i] = pl.prefix + defaultMessage[i].replace("{player}", sender.getName());
    String[] privilegedMessage = pl.privilegedMessage.clone();
    for (int i = 0; i < privilegedMessage.length; i++)
      privilegedMessage[i] = pl.prefix + privilegedMessage[i].replace("{player}", sender.getName());
    for (Player player : Bukkit.getOnlinePlayers()) {
      if (!player.hasPermission("clearchat.immune")) {
        for (int i = 0; i < pl.emptyLines; i++) player.sendMessage(" ");
      }
      if (player.hasPermission("clearchat.privileged")) {
        if (message != null) player.sendMessage(message);
        player.sendMessage(privilegedMessage);
      } else {
        if (message != null) {
          player.sendMessage(message);
        }  else {
          player.sendMessage(defaultMessage);
        }
      }
    }
  }

}
