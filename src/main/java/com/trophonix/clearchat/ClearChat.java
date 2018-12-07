package com.trophonix.clearchat;

import com.trophonix.clearchat.commands.ClearCommand;
import com.trophonix.clearchat.commands.Command;
import com.trophonix.clearchat.commands.CommandHandler;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ClearChat extends JavaPlugin {

  public String[] defaultMessage;
  public String[] privilegedMessage;
  public String prefix;
  public int emptyLines;
  public String insufficientPermissions;

  private CommandHandler commandHandler;

  @Override
  public void onEnable() {
    saveDefaultConfig();
    getConfig().options().copyHeader(true).copyDefaults(true);
    saveConfig();
    commandHandler = new CommandHandler(this);
    reload();
  }

  public void reload() {
    super.reloadConfig();
    commandHandler.clear();
    Command clearCommand = new ClearCommand(this, getStringList("commandAliases", Arrays.asList("clearchat", "clear")));
    commandHandler.add(clearCommand);
    defaultMessage = getStringList("defaultMessage", "&bChat has been cleared!").stream().map(this::color).toArray(String[]::new);
    privilegedMessage = getStringList("privilegedMessage", "&bChat has been cleared {player}!").stream().map(this::color).toArray(String[]::new);
    prefix = color(getConfig().getString("prefix", "&7ClearChat >> "));
    emptyLines = getConfig().getInt("emptyLines", 250);
    insufficientPermissions = color(getConfig().getString("insufficientPermissions", "&cInsufficient permissions!"));
  }

  private List<String> getStringList(String key, List<String> fallback) {
    List<String> list = getConfig().getStringList(key);
    if (list == null || list.isEmpty()) {
      return fallback;
    }
    return list;
  }

  private List<String> getStringList(String key, String fallback) {
    return getStringList(key, Collections.singletonList(fallback));
  }

  public String color(String str) {
    return ChatColor.translateAlternateColorCodes('&', str);
  }

}
