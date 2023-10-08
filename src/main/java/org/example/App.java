package org.example;

import  org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigInteger;
import java.util.Currency;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final Random random = new Random();

    public static void main( String[] args )
    {
        String msg = "hello";
        Stream<BigInteger> msgStream = StringUtils.convertToBigIntegerStream(msg);
        AsymmetricalEncryptorKeyGenerator<Pair<BigInteger, BigInteger>> keyGenerator =
                new AsymmetricalEncryptorKeyGeneratorImpl(random);
        System.out.println(keyGenerator.getPublicKey());
        System.out.println(keyGenerator.getPrivateKey());
        var encryptor = RsaEncryptor.create()
                .configure()
                .privateKey(SecretKeyHolder.of(keyGenerator.getPrivateKey()))
                .publicKey(SecretKeyHolder.of(keyGenerator.getPublicKey()));
        Stream<BigInteger> encryptedMsg = encryptor.encrypt(msgStream);
        String decryptedMsg = StringUtils.convertBigIntegerStreamToString(encryptor.decrypt(encryptedMsg));
        System.out.println(decryptedMsg);
    }
}
