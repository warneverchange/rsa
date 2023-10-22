package org.example.core;

public interface AsymmetricalEncryptorKeyGenerator<TPub, TPriv> {
    void generate(int bitLength);
    TPriv getPrivateKey();
    TPub getPublicKey();
}
