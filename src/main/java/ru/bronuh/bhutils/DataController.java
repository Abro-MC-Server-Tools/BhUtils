package ru.bronuh.bhutils;

import com.google.gson.Gson;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class DataController {

	/**
	 * Сохраняет объект в файл
	 * @param obj Сохраняемый объект
	 * @param file путь к файлу
	 */
	public static void save(Object obj, File file){
		Gson gson = new Gson();

		try{
			String content = gson.toJson(obj);
			if(file.exists()){
				Files.writeString(file.toPath(), content, StandardOpenOption.TRUNCATE_EXISTING);
			}else {
				Files.writeString(file.toPath(), content, StandardOpenOption.CREATE);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @param obj сохраняемый объект
	 * @param file путь к файлу
	 * @param type тип объекта. Требуется в случае работы с дженериками.
	 */
	public static void save(Object obj, File file, Type type){
		Gson gson = new Gson();

		try{
			String content = gson.toJson(obj, type);
			if(file.exists()){
				Files.writeString(file.toPath(), content, StandardOpenOption.TRUNCATE_EXISTING);
			}else {
				Files.writeString(file.toPath(), content, StandardOpenOption.CREATE);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Загружает объект из файла
	 * @param file путь до файла
	 * @param T тип объекта. Для получения типа дженерика используйте new TypeToken<T>() {}.getType();
	 * @return объект типа T
	 * @param <T> тип объекта
	 */
	public static <T> T load(File file, Type T){
		Gson gson = new Gson();
		try {
			return gson.fromJson(Files.readString(file.toPath()),T);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Получает строку по http
	 * @param urlString ссылка
	 * @return строка-ответ
	 */
	public static String httpGet(String urlString){
		try{
			StringBuilder result = new StringBuilder();
			URL url = new URL(urlString);
			URLConnection conn = url.openConnection();
			InputStream is = conn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			for (String line; (line = reader.readLine()) != null; ) {
				result.append(line).append(System.lineSeparator());
			}
			return result.toString();
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
