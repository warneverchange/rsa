package org.example;

import org.example.core.utils.BigIntegerUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;

/**
 * Unit test for simple App.
 */

@ExtendWith(MockitoExtension.class)
public class BigIntegerUtilsTests
{
    @Test
    void getPrimitiveRootShouldWorkBest() {
        Assertions.assertEquals(0, BigIntegerUtils
                .getPrimitiveRoot(BigInteger.valueOf(11L))
                .compareTo(BigInteger.valueOf(2L)));
        Assertions.assertEquals(0, BigIntegerUtils
                .getPrimitiveRoot(BigInteger.valueOf(5011L))
                .compareTo(BigInteger.valueOf(2L)));
        Assertions.assertEquals(0, BigIntegerUtils
                .getPrimitiveRoot(BigInteger.valueOf(5113L))
                .compareTo(BigInteger.valueOf(19L)));
        Assertions.assertEquals(0, BigIntegerUtils
                .getPrimitiveRoot(BigInteger.valueOf(5197L))
                .compareTo(BigInteger.valueOf(7L)));
        Assertions.assertEquals(0, BigIntegerUtils
                .getPrimitiveRoot(BigInteger.valueOf(5081L))
                .compareTo(BigInteger.valueOf(3L)));
        Assertions.assertEquals(0, BigIntegerUtils
                .getPrimitiveRoot(BigInteger.valueOf(5521L))
                .compareTo(BigInteger.valueOf(11L)));
    }
}
