package ru.bronuh.bhutils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.bronuh.bhutils.colors.ColorScheme;
import ru.bronuh.bhutils.colors.DefaultColorScheme;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public abstract class BhPlugin extends JavaPlugin {
	private final Logger log = getLogger();
	private final String pluginDir = getDataFolder().getPath();
	private BhConfig config;
	public ColorScheme colors = new DefaultColorScheme();

	@Override
	public abstract void onEnable();

	@Override
	public abstract void onDisable();


	// Возвращает путь до директории внутри директории плагина
	public File getDataFolder(String path){
		File out = new File(getDataFolder(),path);
		try{
			out.mkdirs();
		}catch (Exception e){
			out = getDataFolder();
			e.printStackTrace();
		}
		return out;
	}

	/**
	 * Отправляет сообщение игроку
	 * @param player поучатель ответа
	 * @param message текст сообщения
	 */
	public void sendMessage(CommandSender player, String message, TextColor color){
		player.sendMessage(Component.text(message,color));
	}

	/**
	 * Отправляет сообщение игроку
	 * @param player поучатель ответа
	 * @param message текст сообщения
	 */
	public void sendMessage(Player player, String message, TextColor color){
		player.sendMessage(Component.text(message,color));
	}

	/**
	 * Отправляет ответ зеленго цвета
	 * @param message текст сообщения
	 */
	public void broadcast(String message, TextColor color){
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.sendMessage(Component.text(message,color));
		}
	}


	/**
	 * @return внутренний конфиг плагина
	 */
	public BhConfig getCustomConfig(){
		return config;
	}


	/**
	 * Создает директории и их родителей
	 * @param dirs Требуемые директории
	 * @return
	 */
	protected List<File> setupSubdirs(String... dirs){
		List<File> outDirs = new ArrayList<>();
		for (String dir : dirs) {
			outDirs.add(getDataFolder(dir));
		}
		return outDirs;
	}

	/**
	 * Загружает конфиг из файла и записывает значения в targetConfig
	 * @param targetConfig внутренний объект конфига
	 */
	protected void loadConfig(BhConfig targetConfig){
		// Пытается получить конфиг из файла. Если файла нет - конфиг пуст
		FileConfiguration fileConfig = getConfig();

		// Назначение стандартных значений в конфиг
		prepareDefaults(fileConfig, targetConfig);

		// В случае появления недостающих параметров в конфиге - они будут дописаны
		fileConfig.options().copyDefaults(true);
		saveConfig();

		// Перенос значений из загружнного файла в экземпляр конфига
		copyToConfig(fileConfig, targetConfig);
	}


	/**
	 * Переносит значения из fileConfig во внутренний конфиг
	 *
	 * @param fileConfig
	 * @param targetConfig
	 */
	private void copyToConfig(FileConfiguration fileConfig, BhConfig targetConfig) {
		Field[] declaredFields = targetConfig.getClass().getDeclaredFields();

		for (Field field : declaredFields) {
			try {
				field.set(targetConfig, fileConfig.get(field.getName()));
			}catch (Exception e){
				log.warning("Не удалось присвоить значение полю "+field.getName()+": "+e.getMessage());
			}
		}
	}


	/**
	 * Назначает стандартные значения в fileConfig, после чего записывает в файл недостающие параметры
	 *
	 * @param fileConfig
	 * @param targetConfig
	 */
	private void prepareDefaults(FileConfiguration fileConfig, BhConfig targetConfig) {
		Field[] declaredFields = targetConfig.getClass().getDeclaredFields();
		for(Field field : declaredFields){
			try {
				fileConfig.addDefault(field.getName(), field.get(targetConfig));
			}catch (Exception e){
				log.warning("Не удалось получить значение поля "+field.getName()+": "+e.getMessage());
			}
		}
	}
}
