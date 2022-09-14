package ru.bainc.util;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZMethod;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import static org.apache.commons.compress.archivers.sevenz.SevenZMethod.LZMA2;

public class SevenZCompress {
//    public static void compress(String outputZipFile, String sourceFolder) {
//        File file = new File(sourceFolder); //sourceFolder- источник заполнитель, outputZipFile-выходной 7зип файл
//        if (file.exists()) {
//            try (SevenZOutputFile out = new SevenZOutputFile(new File(outputZipFile))) {
//                addToArchiveCompression(out, file, ".");
//                System.out.println("Files sucessfully compressed");
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        } else {
//            System.out.println("Folder does not exist");
//        }
//    }

    public static void compress(String name, File...files) {
        try (SevenZOutputFile out = new SevenZOutputFile(new File(name))) {//name-выходний зипФайл
            out.setContentCompression(SevenZMethod.LZMA2); // выбрала метод компрессии
            for(File file: files){
                addToArchiveCompression(out, file, ".");}
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static void addToArchiveCompression(SevenZOutputFile out, File file, String dir) throws IOException {
        String name = dir + File.separator + file.getName();
        if (file.isFile()) {
            SevenZArchiveEntry entry = out.createArchiveEntry(file, name);
            out.putArchiveEntry(entry);

            FileInputStream in = new FileInputStream(file);
            byte[] b = new byte[1024];
            int count = 0;
            while ((count = in.read(b)) > 0) {
                out.write(b, 0, count);
            }
            out.closeArchiveEntry();
            in.close();
            System.out.println("File added: " + file.getName());
        } else if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children != null) {
                for (File child : children) {
                    addToArchiveCompression(out, child, name);
                }
            }
            System.out.println("Directory added: " + file.getName());
        } else {
            System.out.println(file.getName() + " is not supported");
        }
    }

    public static boolean copyFile7zOutBookTemp(File source, File destination) {

        File srcFile = new File(String.valueOf(source));
        File destFile = new File(String.valueOf(destination));
        try {
            FileUtils.copyFile(srcFile, destFile);
            System.out.println("File successfully copied in Java");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void cleanBookTemp(File TempDir) { // имя директории, которую нужно очистить
        for (File file : TempDir.listFiles()) {
            if (!file.isDirectory()) {
                file.delete();
            }
        }
    }
}

