package ru.bainc.test;

import java.util.Base64;

public class Test
{
    public static String encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }
    public static String encodeWithoutPadding(byte[] bytes) {
        return Base64.getEncoder()
                .withoutPadding()
                .encodeToString(bytes);
    }
    public static byte[] decode(String s) {
        return Base64.getDecoder().decode(s);
    }
    public static void main(String[] args) {
        String s = "Hello, World!";
        String encoded = encodeWithoutPadding(s.getBytes());
        String decoded = new String(decode(encoded));
        System.out.println(encoded);
        System.out.println(decoded);
    }
}
