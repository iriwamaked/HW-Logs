package itstep.learning.services.generator;

import com.google.inject.Singleton;

import java.security.SecureRandom;
@Singleton
public class OTPGeneratorService implements StringGeneratorService{
    private final SecureRandom random = new SecureRandom();

    @Override
    public String generate(int otpLength) {
        int min = (int) Math.pow(10, otpLength - 1);  // Минимальное значение (например, 100000 для длины 6)
        int max = (int) Math.pow(10, otpLength) - 1;  // Максимальное значение (999999 для длины 6)
        int otp = random.nextInt(max - min + 1) + min; // Генерация числа в диапазоне
        return String.valueOf(otp);
    }
}
