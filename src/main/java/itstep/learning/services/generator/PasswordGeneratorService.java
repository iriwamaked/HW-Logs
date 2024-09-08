package itstep.learning.services.generator;

import com.google.inject.Singleton;

import java.security.SecureRandom;
@Singleton
public class PasswordGeneratorService implements StringGeneratorService{
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+";
    private final SecureRandom random = new SecureRandom();

    public String generate(int length) {
        StringBuilder result = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            result.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return result.toString();
    }
}
