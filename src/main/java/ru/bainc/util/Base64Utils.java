package ru.bainc.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class Base64Utils {

    public static String fileToBase64(String fileName) throws Exception {
        byte[] bytes = Files.readAllBytes(Paths.get(fileName));
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static boolean base64ToFile(String encoded, String fileName) throws Exception {
        Path path = Paths.get(fileName);
        byte[] bytes = Base64.getDecoder().decode(encoded.getBytes());
        try {
            Files.write(path, bytes);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public static void main(String[] args) throws Exception {

//        System.out.println(fileToBase64("C:\\Users\\BabushkinaOA\\Desktop\\test.txt"));
        System.out.println(Base64.getEncoder().encodeToString("Hello test library SpringBoot: Внутри убийцы".getBytes()));
    }
}
