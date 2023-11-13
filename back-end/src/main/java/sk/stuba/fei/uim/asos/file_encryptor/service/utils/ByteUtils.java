/*
 *    Title: How to split a byte array around a byte sequence in Java?
 *    Author: slim
 *    Date: 2022
 *    Code version: 1.0
 *    Availability: https://stackoverflow.com/questions/22519346/how-to-split-a-byte-array-around-a-byte-sequence-in-java
 */
/*
 *    Title: Concatenate two or more Byte Arrays in Java
 *    Author: anonymous
 *    Date: 2022
 *    Code version: 1.0
 *    Availability: https://www.techiedelight.com/concatenate-byte-arrays-in-java/
 */
package sk.stuba.fei.uim.asos.file_encryptor.service.utils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ByteUtils {

    public static byte[] byteConcat(byte[]... arrays) {
        int len = Arrays.stream(arrays).filter(Objects::nonNull)
                .mapToInt(s -> s.length).sum();
        byte[] result = new byte[len];
        int lengthSoFar = 0;
        if (arrays != null) {
            for (byte[] array: arrays) {
                if (array != null) {
                    System.arraycopy(array, 0, result, lengthSoFar, array.length);
                    lengthSoFar += array.length;
                }
            }
        }
        return result;
    }

    public static boolean isByteMatch(byte[] pattern, byte[] input, int pos) {
        for(int i=0; i< pattern.length; i++) {
            if(pattern[i] != input[pos+i]) {
                return false;
            }
        }
        return true;
    }

    public static List<byte[]> byteSplit(byte[] pattern, byte[] input) {
        List<byte[]> l = new LinkedList<byte[]>();
        int blockStart = 0;
        for(int i=0; i<input.length; i++) {
            if(isByteMatch(pattern,input,i)) {
                l.add(Arrays.copyOfRange(input, blockStart, i));
                blockStart = i+pattern.length;
                i = blockStart;
            }
        }
        l.add(Arrays.copyOfRange(input, blockStart, input.length ));
        return l;
    }

}
