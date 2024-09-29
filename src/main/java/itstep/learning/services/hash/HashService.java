package itstep.learning.services.hash;

public interface HashService {
    String digest (String message);
    boolean matches(String rawPassword, String hashedPassword);
}


