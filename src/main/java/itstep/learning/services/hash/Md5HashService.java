package itstep.learning.services.hash;

import com.google.inject.Singleton;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLOutput;

@Singleton
public class Md5HashService implements HashService{
    @Override
    public String digest(String message) {
        try{
            StringBuilder sb = new StringBuilder();
            MessageDigest md= MessageDigest.getInstance("MD5");
            md.update(message.getBytes(StandardCharsets.UTF_8));
            for (byte b:md.digest()){
                sb.append(Integer.toHexString(b&0xff));
            }
            return sb.toString();
        }
        catch (NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }

    }

    public boolean matches(String rawPassword, String hashedPassword){
            String hashedRawPassword = digest(rawPassword);
            return hashedRawPassword.equals(hashedPassword);
    }
}
