package org.example;


public interface AsymmetricalEncryptor<T, R, PUB, PRIV> extends Encryptor<T, R>{
    AsymmetricalEncryptor<T, R, PUB, PRIV> privateKey(SecretKeyHolder<PRIV> privateKey);
    AsymmetricalEncryptor<T, R, PUB, PRIV> publicKey(SecretKeyHolder<PUB> publicKey);

    PUB getPublicKey();
    PRIV getPrivateKey();
}
