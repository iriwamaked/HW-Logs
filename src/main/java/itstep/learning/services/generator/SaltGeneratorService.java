package itstep.learning.services.generator;

import com.google.inject.Singleton;

import java.security.SecureRandom;
import java.util.Base64;
@Singleton
public class SaltGeneratorService implements StringGeneratorService{
    private final SecureRandom random = new SecureRandom();

    public String generate(int length) {
        byte[] salt = new byte[length];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
}
