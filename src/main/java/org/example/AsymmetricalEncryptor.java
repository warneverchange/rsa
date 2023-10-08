package org.example;


public interface AsymmetricalEncryptor<T, R, S> extends Encryptor<T, R>{
    AsymmetricalEncryptor<T, R, S> privateKey(SecretKeyHolder<S> privateKey);
    AsymmetricalEncryptor<T, R, S> publicKey(SecretKeyHolder<S> publicKey);

    S getPublicKey();
    S getPrivateKey();
}
