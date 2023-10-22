package org.example.impl.gamal;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.example.AsymmetricalEncryptor;
import org.example.SecretKeyHolder;
import org.example.utils.BigIntegerUtils;

import java.math.BigInteger;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Stream;

public class ElGamalEncryptor implements AsymmetricalEncryptor<BigInteger, Pair<BigInteger, BigInteger>, Triple<BigInteger, BigInteger, BigInteger>, Pair<BigInteger, BigInteger>> {
    private SecretKeyHolder<Triple<BigInteger, BigInteger, BigInteger>> publicKey;
    private SecretKeyHolder<Pair<BigInteger, BigInteger>> privateKey;

    private Random random;
    private ElGamalEncryptor() {

    }
    public static ElGamalEncryptor create() {
        return new ElGamalEncryptor();
    }
    @Override
    public ElGamalEncryptor privateKey(SecretKeyHolder<Pair<BigInteger, BigInteger>> privateKey) {
        Objects.requireNonNull(privateKey);
        this.privateKey = privateKey;
        return this;
    }

    public ElGamalEncryptor random(Random random) {
        Objects.requireNonNull(random);
        this.random = random;
        return this;
    }

    @Override
    public ElGamalEncryptor publicKey(SecretKeyHolder<Triple<BigInteger, BigInteger, BigInteger>> publicKey) {
        Objects.requireNonNull(publicKey);
        this.publicKey = publicKey;
        return this;
    }

    @Override
    public Triple<BigInteger, BigInteger, BigInteger> getPublicKey() {
        Objects.requireNonNull(publicKey);
        return publicKey.value();
    }


    @Override
    public Pair<BigInteger, BigInteger> getPrivateKey() {
        Objects.requireNonNull(privateKey);
        return privateKey.value();
    }

    @Override
    public Stream<Pair<BigInteger, BigInteger>> encrypt(Stream<BigInteger> inputStream) {
        var publicKey = getPublicKey();
        var Y = publicKey.getLeft();
        var G = publicKey.getMiddle();
        var P = publicKey.getRight();
        var sessionKey = BigIntegerUtils.generateSmallestRandomCoprime(P.subtract(BigInteger.ONE), random);
        var A = G.modPow(sessionKey, P);
        return inputStream.map(
                input -> new ImmutablePair<>(
                        A,
                        Y.modPow(sessionKey, P).multiply(input.mod(P)).mod(P)
                )
        );
    }

    @Override
    public Stream<BigInteger> decrypt(Stream<Pair<BigInteger, BigInteger>> inputStream) {
        var X = getPrivateKey().getLeft();
        var P = getPrivateKey().getRight();
        return inputStream.map(
                inputPair -> {
                    var A = inputPair.getLeft();
                    var B = inputPair.getRight();
                    return B.mod(P).multiply(A.modPow(P.subtract(BigInteger.ONE).subtract(X), P)).mod(P);
                }
        );
    }
}
