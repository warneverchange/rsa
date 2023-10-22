package org.example.core.impl.gamal;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.example.core.AsymmetricalEncryptor;
import org.example.core.SecretKeyHolder;
import org.example.core.utils.BigIntegerUtils;

import java.math.BigInteger;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Stream;

public class ElGamalEncryptorImpl implements AsymmetricalEncryptor<BigInteger, Pair<BigInteger, BigInteger>> {
    private Triple<BigInteger, BigInteger, BigInteger> publicKey;
    private Pair<BigInteger, BigInteger> privateKey;

    private Random random;

    private ElGamalEncryptorImpl() {

    }

    public static ElGamalEncryptorImpl create() {
        return new ElGamalEncryptorImpl();
    }

    public ElGamalEncryptorImpl random(Random random) {
        Objects.requireNonNull(random);
        this.random = random;
        return this;
    }



    @Override
    public Stream<Pair<BigInteger, BigInteger>> encrypt(Stream<BigInteger> inputStream) {
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
        var X = privateKey.getLeft();
        var P = privateKey.getRight();
        return inputStream.map(
                inputPair -> {
                    var A = inputPair.getLeft();
                    var B = inputPair.getRight();
                    return B.mod(P).multiply(A.modPow(P.subtract(BigInteger.ONE).subtract(X), P)).mod(P);
                }
        );
    }


    @Override
    public <K extends SecretKeyHolder<?>> ElGamalEncryptorImpl privateKey(K privateKey) {
        if (privateKey.value() instanceof Pair pair
                && pair.getLeft() instanceof BigInteger leftValue
                && pair.getRight() instanceof BigInteger rightValue) {
            this.privateKey = new ImmutablePair<>(leftValue, rightValue);
            return this;
        }
        throw new IllegalArgumentException("Private key must have type Pair<BigInteger, BigInteger>");
    }

    @Override
    public <K extends SecretKeyHolder<?>> ElGamalEncryptorImpl publicKey(K publicKey) {
        if (publicKey.value() instanceof Triple triple
                && triple.getLeft() instanceof BigInteger leftValue
                && triple.getRight() instanceof BigInteger rightValue
                && triple.getMiddle() instanceof BigInteger middleValue) {
            this.publicKey = new ImmutableTriple<>(leftValue, middleValue, rightValue);
            return this;
        }
        throw new IllegalArgumentException("Private key must have type Pair<BigInteger, BigInteger>");
    }
}
