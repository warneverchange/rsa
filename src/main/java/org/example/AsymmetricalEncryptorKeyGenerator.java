package org.example;

public interface AsymmetricalEncryptorKeyGenerator<T> {
    void generate(int bitLength);
    T getPrivateKey();
    T getPublicKey();
}
