package org.example;

public class SecretKeyHolder <T>{
    private final T secretKey;

    private SecretKeyHolder(T secretKey) {
        this.secretKey = secretKey;
    }

    public static <T> SecretKeyHolder<T> of(T secretKey) {
        return new SecretKeyHolder<T>(secretKey);
    }

    public T value() {
        return secretKey;
    }
}
