package me.hannsi.melyclient.util.system.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileUtil {
    public static void writeString(String text, File file) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(text);
        fileWriter.close();
    }

    public static String readString(File file) throws FileNotFoundException {
        if (!file.exists()) {
            return null;
        }

        String text = null;

        Scanner reader;
        reader = new Scanner(file);
        while (reader.hasNextLine()) {
            text = reader.nextLine();
        }
        reader.close();

        return text;
    }

    public static boolean createDirectory(String path) {
        File file = new File(path);

        return file.mkdirs();
    }
}
