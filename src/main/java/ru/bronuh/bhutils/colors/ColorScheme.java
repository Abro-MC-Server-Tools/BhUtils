package ru.bronuh.bhutils.colors;

import net.kyori.adventure.text.format.TextColor;

public interface ColorScheme {
	public TextColor ok();
	public TextColor warning();
	public TextColor error();
	public TextColor system();
	public TextColor info();
}
