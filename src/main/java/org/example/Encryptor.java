package org.example;

import java.util.stream.Stream;

public interface Encryptor<T, R> {
    // вот смотри, ты сюда передал строку, разбил ее на байты допустим, чтобы каждый байт зашифровать или это не так?
    Stream<R> encrypt(Stream<T> inputStream);
    Stream<R> decrypt(Stream<T> inputStream);
}
