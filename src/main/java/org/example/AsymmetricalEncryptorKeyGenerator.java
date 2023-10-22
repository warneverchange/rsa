package org.example;

public interface AsymmetricalEncryptorKeyGenerator<PUB, PRIV> {
    void generate(int bitLength);
    PRIV getPrivateKey();
    PUB getPublicKey();
}
