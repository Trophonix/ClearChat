package com.trophonix.clearchat.commands;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandHandler implements Listener {

  private List<Command> commands = new ArrayList<>();

  public CommandHandler(Plugin pl) {
    pl.getServer().getPluginManager().registerEvents(this, pl);
  }

  public void add(Command command) {
    commands.add(command);
  }

  public void clear() {
    commands.clear();
  }

  @EventHandler
  public void onCommand(PlayerCommandPreprocessEvent event) {
    String[] cmd = event.getMessage().substring(1).split("\\s+");
    String[] args = new String[cmd.length - 1];
    System.arraycopy(cmd, 1, args, 0, cmd.length - 1);
    if (cmd.length > 0) {
      commands.stream()
              .filter(command -> command.isAlias(cmd[0]))
              .findFirst().ifPresent(command -> {
        command.onCommand(event.getPlayer(), args);
        event.setCancelled(true);
      });
    }
  }

  @EventHandler
  public void onConsoleCommand(ServerCommandEvent event) {
    System.out.println(event.getCommand());
    String[] cmd = event.getCommand().split("\\s+");
    String[] args = new String[cmd.length - 1];
    System.arraycopy(cmd, 1, args, 0, cmd.length - 1);
    if (cmd.length > 0) {
      commands.stream()
              .filter(command -> command.isAlias(cmd[0]))
              .findFirst().ifPresent(command -> {
        command.onCommand(event.getSender(), args);
        event.setCancelled(true);
      });
    }
  }

}
