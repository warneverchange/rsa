package org.example.core;


public interface AsymmetricalEncryptor<T, R> extends Encryptor<T, R>{
    <K extends SecretKeyHolder<?>> AsymmetricalEncryptor<T, R> privateKey(K privateKey);
    <K extends SecretKeyHolder<?>> AsymmetricalEncryptor<T, R> publicKey(K publicKey);
}
