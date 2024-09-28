package itstep.learning.ioc;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DbModule extends AbstractModule {
    private Connection currentConnection = null;
    private Driver mysqlDriver;
    @Provides
    private Connection getConnection(){
        //если раньше не подключалось
        if(currentConnection==null){
            Map<String,String> ini=new HashMap<>();

            //делаем отдельный трай для чтения файла
            //достаем InputStream, достаем
            try(InputStream iniStream=this
                    .getClass().getClassLoader().getResourceAsStream("db.ini")){
                //прочитали
                String iniContent = readStream(iniStream);
                //делим на строки
                String[] lines = iniContent.split("\n");
                for (String line:lines){
                    //в первой строке у нас несколько знаков равно, если просто делить, сделает это не правильно
                    //поэтому ставим границу лимит (2), первый значок делим - второй - нет
                    String[] parts = line.split("=", 2);
                    if(parts.length==2){
                        //если вокруг значка равно будут пробелы или перевод строки едет через \n\n
                        //то эти значки могут прилепляться, trim убирают это
                        ini.put(parts[0].trim(), parts[1].trim());
                    }
                }
            }
            catch (IOException ex){
                System.err.println(ex.getMessage());
            }

            try{
                mysqlDriver = new com.mysql.cj.jdbc.Driver();
                DriverManager.registerDriver(mysqlDriver);
                currentConnection = DriverManager.getConnection(
                        ini.get("connectionString"),
                        ini.get("user"),
                        ini.get("password"));

            }
            catch (SQLException ex){
                System.err.println(ex.getMessage());
                return null;
            }
        }
        return currentConnection;
    }

    private String readStream(InputStream inputStream) throws IOException{
        ByteArrayOutputStream byteBuilder = new ByteArrayOutputStream();
        byte[] buffer=new byte[1024*16];
        int length;
        while ((length=inputStream.read(buffer))!=-1){
            byteBuilder.write(buffer,0,length);
        }
        return byteBuilder.toString(StandardCharsets.UTF_8.name());
    }

    public void close(){
        try{
            if(currentConnection!=null){
                currentConnection.close();
            }
            if(mysqlDriver!=null) {
                DriverManager.deregisterDriver(mysqlDriver);
            }
        }
        catch (Exception ignore){}
    }
}
