package org.example;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.Objects;
import java.util.stream.Stream;

public class RsaEncryptor implements AsymmetricalEncryptor<BigInteger, BigInteger, Pair<BigInteger, BigInteger>> {
    private SecretKeyHolder<Pair<BigInteger, BigInteger>> privateKey;
    private SecretKeyHolder<Pair<BigInteger, BigInteger>> publicKey;

    private RsaEncryptor() {
        this.privateKey = null;
        this.publicKey = null;
    }

    public static RsaEncryptor create() {
        return new RsaEncryptor();
    }

    public RsaEncryptor configure() {
        return this;
    }


    public Stream<BigInteger> encrypt(@NotNull Stream<BigInteger> inputStream) {
        Objects.requireNonNull(publicKey);
        Objects.requireNonNull(inputStream);
        var E = getPublicKey().getLeft();
        var N = getPublicKey().getRight();
        return inputStream.map(value -> value.modPow(E, N));
    }

    public Stream<BigInteger> decrypt(Stream<BigInteger> inputStream) {
        Objects.requireNonNull(inputStream);
        var D = getPrivateKey().getLeft();
        var N = getPrivateKey().getRight();
        return inputStream.map(value -> value.modPow(D, N));
    }

    @Override
    public RsaEncryptor privateKey(SecretKeyHolder<Pair<BigInteger, BigInteger>> privateKey) {
        Objects.requireNonNull(privateKey);
        this.privateKey = privateKey;
        return this;
    }

    @Override
    public RsaEncryptor publicKey(SecretKeyHolder<Pair<BigInteger, BigInteger>> publicKey) {
        Objects.requireNonNull(publicKey);
        this.publicKey = publicKey;
        return this;
    }

    @Override
    public Pair<BigInteger, BigInteger> getPublicKey() {
        return publicKey.value();
    }

    @Override
    public Pair<BigInteger, BigInteger> getPrivateKey() {
        return privateKey.value();
    }

}
