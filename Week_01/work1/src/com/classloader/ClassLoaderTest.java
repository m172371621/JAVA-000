package com.classloader;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

public class ClassLoaderTest extends ClassLoader {

    public static void main(String[] args) {
        try {
            Class<?> hello = new ClassLoaderTest().findClass("Hello");
            Object obj = hello.newInstance();
            Method method = hello.getMethod("hello");
            method.invoke(obj);
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) {
        URL resource = this.getClass().getResource("Hello.xlass");
        String path = resource.getPath();
        File file = new File(path);
        byte[] bytes = null;
        try {
            bytes = getByts(new FileInputStream(file));
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) (255 - bytes[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.defineClass(name, bytes, 0, bytes.length);
    }

    private byte[] getByts(FileInputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int byte_size = 1024;
        byte[] bytes = new byte[byte_size];
        int size = 0;
        while ((size = in.read(bytes,0,byte_size)) != -1) {
            out.write(bytes, 0, size);
        }
        return out.toByteArray();
    }
}
