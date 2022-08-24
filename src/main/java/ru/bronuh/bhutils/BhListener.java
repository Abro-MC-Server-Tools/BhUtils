package ru.bronuh.bhutils;

import org.bukkit.event.Listener;

import java.util.logging.Logger;

public abstract class BhListener implements Listener {
	protected BhPlugin plugin;
	protected Logger log;
	public BhListener(BhPlugin plugin){
		this.plugin = plugin;
		log = plugin.getLogger();
	}


}
