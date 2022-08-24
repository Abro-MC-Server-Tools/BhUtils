package ru.bronuh.bhutils.colors;

import net.kyori.adventure.text.format.TextColor;
import ru.bronuh.bhutils.colors.ColorScheme;

/**
 * Стандартный набор цветов для работы с TextComponent
 */
public class DefaultColorScheme implements ColorScheme {
	public TextColor ok(){return TextColor.color(100,255,100);}
	public TextColor warning(){return TextColor.color(255,100,100);}
	public TextColor error(){return TextColor.color(200,0,0);}
	public TextColor system(){return TextColor.color(150,150,150);}
	public TextColor info(){return TextColor.color(100,150,255);}
}
