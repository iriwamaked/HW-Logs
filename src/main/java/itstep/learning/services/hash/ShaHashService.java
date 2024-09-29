package itstep.learning.services.hash;

import com.google.inject.Singleton;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Singleton
public class ShaHashService implements HashService{
    @Override
    public String digest(String message) {
        try{
            StringBuilder sb = new StringBuilder();
            MessageDigest md= MessageDigest.getInstance("SHA-1");
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

    @Override
    public boolean matches(String rawPassword, String hashedPassword){
            String hashedRawPassword = digest(rawPassword);
            return hashedRawPassword.equals(hashedPassword);

    }
}

