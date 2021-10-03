package com.rh.itheima;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MyLoadClassTest {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        MyClassLoader myClassLoader = new MyClassLoader();
        Class<?> testLoad = myClassLoader.loadClass("TestLoad");
        testLoad.newInstance();
    }
}

class MyClassLoader extends ClassLoader{
    /**
     *
     * @param name  类的名称
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String path = "C:\\Users\\Administrator\\Desktop\\myclasspath\\"+name+".class";

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Files.copy(Paths.get(path),outputStream);

            //得到字节数组
            byte[] bytes = outputStream.toByteArray();
            //byte[]  ->  *.class
            return defineClass(name,bytes,0,bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ClassNotFoundException("类文件没找到！",e);
        }
    }
}
