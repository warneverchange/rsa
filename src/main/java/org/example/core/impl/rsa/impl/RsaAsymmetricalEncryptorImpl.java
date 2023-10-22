package org.example.core.impl.rsa.impl;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.example.core.AsymmetricalEncryptor;
import org.example.core.SecretKeyHolder;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.Objects;
import java.util.stream.Stream;

public class RsaAsymmetricalEncryptorImpl implements AsymmetricalEncryptor<BigInteger, BigInteger> {
    private Pair<BigInteger, BigInteger> privateKey;
    private Pair<BigInteger, BigInteger> publicKey;

    private RsaAsymmetricalEncryptorImpl() {
        this.privateKey = null;
        this.publicKey = null;
    }

    public static RsaAsymmetricalEncryptorImpl create() {
        return new RsaAsymmetricalEncryptorImpl();
    }

    public RsaAsymmetricalEncryptorImpl configure() {
        return this;
    }


    public Stream<BigInteger> encrypt(@NotNull Stream<BigInteger> inputStream) {
        Objects.requireNonNull(publicKey);
        Objects.requireNonNull(inputStream);
        var E = publicKey.getLeft();
        var N = publicKey.getRight();
        return inputStream.map(value -> value.modPow(E, N));
    }

    public Stream<BigInteger> decrypt(Stream<BigInteger> inputStream) {
        Objects.requireNonNull(inputStream);
        var D = privateKey.getLeft();
        var N = privateKey.getRight();
        return inputStream.map(value -> value.modPow(D, N));
    }

    @Override
    public <K extends SecretKeyHolder<?>> AsymmetricalEncryptor<BigInteger, BigInteger> privateKey(K privateKey) {
        Objects.requireNonNull(privateKey);
        if (privateKey.value() instanceof Pair pair
                && pair.getLeft() instanceof BigInteger leftValue
                && pair.getRight() instanceof BigInteger rightValue) {
            this.privateKey = new ImmutablePair<>(leftValue, rightValue);
            return this;
        }
        throw new IllegalArgumentException("Argument must have type Pair<BigInteger, BigInteger>");
    }

    @Override
    public <K extends SecretKeyHolder<?>> AsymmetricalEncryptor<BigInteger, BigInteger> publicKey(K publicKey) {
        Objects.requireNonNull(publicKey);
        if (publicKey.value() instanceof Pair pair
                && pair.getLeft() instanceof BigInteger leftValue
                && pair.getRight() instanceof BigInteger rightValue) {
            this.publicKey = new ImmutablePair<>(leftValue, rightValue);
            return this;
        }
        throw new IllegalArgumentException("Argument must have type Pair<BigInteger, BigInteger>");
    }
}
