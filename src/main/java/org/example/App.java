package org.example;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.example.impl.gamal.ElGamalAsymmetricalEncryptorKeyGeneratorImpl;
import org.example.impl.gamal.ElGamalEncryptor;
import org.example.impl.rsa.impl.RsaAsymmetricalKeyGeneratorImpl;
import org.example.impl.rsa.impl.RsaAsymmetricalEncryptorImpl;
import org.example.utils.StringUtils;

import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final Random random = new Random();
    private static final AsymmetricalEncryptorKeyGenerator<Pair<BigInteger, BigInteger>, Pair<BigInteger, BigInteger>> rsaKeyGenerator
            = new RsaAsymmetricalKeyGeneratorImpl(random);

    private static final AsymmetricalEncryptorKeyGenerator<Triple<BigInteger, BigInteger, BigInteger>, Pair<BigInteger, BigInteger>> elGamalKeyGenerator =
            new ElGamalAsymmetricalEncryptorKeyGeneratorImpl(random);

    public static void DoRsaEncryption() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter length in bits for generating keys: ");
        rsaKeyGenerator.generate(scanner.nextInt());
        System.out.println("Generated public key: " + rsaKeyGenerator.getPublicKey());
        System.out.println("Generated private key: " + rsaKeyGenerator.getPrivateKey());
        System.out.print("Enter msg for encrypting: ");
        Encryptor<BigInteger, BigInteger> encryptor =
                RsaAsymmetricalEncryptorImpl.create()
                        .privateKey(SecretKeyHolder.of(rsaKeyGenerator.getPrivateKey()))
                        .publicKey(SecretKeyHolder.of(rsaKeyGenerator.getPublicKey()));
        String msg = scanner.next();
        var encryptedMsgStream =  encryptor.encrypt(StringUtils.convertToBigIntegerStream(msg));
        System.out.println("Encrypted msg: " + encryptedMsgStream.toString());
        System.out.println("Decrypted msg: " + StringUtils.convertBigIntegerStreamToString(encryptor.decrypt(encryptedMsgStream)));
    }

    public static void DoElGamalEncryption() {
        var scanner = new Scanner(System.in);
        System.out.print("Enter length in bits for generating keys: ");
        elGamalKeyGenerator.generate(scanner.nextInt());
        System.out.println("Generated public key: " + elGamalKeyGenerator.getPublicKey());
        System.out.println("Generated private key: " + elGamalKeyGenerator.getPrivateKey());
        System.out.print("Enter msg for encrypting: ");
        String msg = scanner.next();
        Encryptor<BigInteger, Pair<BigInteger, BigInteger>> encryptor = ElGamalEncryptor.create()
                .privateKey(SecretKeyHolder.of(elGamalKeyGenerator.getPrivateKey()))
                .publicKey(SecretKeyHolder.of(elGamalKeyGenerator.getPublicKey()))
                .random(random);
        Stream<Pair<BigInteger, BigInteger>> encryptedMsg = encryptor.encrypt(StringUtils.convertToBigIntegerStream(msg));
        System.out.println("Encrypted msg: ");
        /*encryptedMsg.forEach(
                System.out::println
        );
*/
        System.out.println("Decrypted msg: " + StringUtils.convertBigIntegerStreamToString(encryptor.decrypt(encryptedMsg)));
    }

    public static void main( String[] args )
    {
        DoElGamalEncryption();
    }
}
