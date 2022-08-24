package ru.bronuh.bhutils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public abstract class BhExecutor implements CommandExecutor {

	protected Logger log;
	protected BhPlugin plugin;

	public BhExecutor(BhPlugin plugin){
		this.plugin = plugin;
		log = plugin.getLogger();
	}
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		return false;
	}


}
