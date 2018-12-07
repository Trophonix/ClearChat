package com.trophonix.clearchat.commands;

import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Command {

  private List<String> aliases;

  public Command(List<String> aliases) {
    this.aliases = aliases.stream().map(String::toLowerCase).collect(Collectors.toList());
  }

  public boolean isAlias(String command) {
    return aliases.contains(command.toLowerCase());
  }

  public List<String> getAliases() {
    return aliases;
  }

  public abstract void onCommand(CommandSender sender, String[] args);

}
