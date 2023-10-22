package org.example.core;

import java.util.stream.Stream;

public interface Encryptor<T, R> {
    Stream<R> encrypt(Stream<T> inputStream);
    Stream<T> decrypt(Stream<R> inputStream);
}
