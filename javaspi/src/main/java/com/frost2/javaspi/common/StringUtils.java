package com.frost2.javaspi.common;

public class StringUtils {

    /**
     * 上面的字符串既有汉字，又有英文字符和数字。如果要截取前6个字节的字符，应该是”a加b等"，
     * 但如果用substring方法截取前6个字符就成了"a 加b等于c"。
     * 产生这个问题的原因是将substring方法将双字节的汉字当成一个字节的字符（UCS2字符）处理了。
     * 要解决这个问题的方法是首先得到该字符串的UCS2编码的字节数组，如下面的代码如下：
     * <p>
     * byte[] bytes = s.getBytes("Unicode");
     * <p>
     * 由于上面生成的字节数组中前两个字节是标志位，bytes[0] = -2，bytes[1] = -1，
     * 因此，要从第三个字节开始扫描，对于一个英文或数字字符，UCS2编码的第二个字节是相应的ASCII，
     * 第一个字节是0，如a的UCS2编码是0 97，而汉字两个字节都不为0，
     * 因此，可以利于UCS2编码的这个规则来计算实际的字节数，该方法的实现代码如下
     *
     * @param s
     * @param length
     * @return
     * @throws Exception
     */
    public static String substring(String s, int length) throws Exception {

        byte[] bytes = s.getBytes("Unicode");
        int n = 0; //表示当前的字节数
        int i = 2; //要截取的字节数，从第3个字节开始
        for (; i < bytes.length && n < length; i++) {
            //奇数位置，如3、5、7等，为UCS2编码中两个字节的第二个字节
            if (i % 2 == 1) {
                n++; //在UCS2第二个字节时n加1
            } else {
                //当UCS2编码的第一个字节不等于0时，该UCS2字符为汉字，一个汉字算两个字节
                if (bytes[i] != 0) {
                    n++;
                } else {
                    if (bytes[i + 1] == -73) {
                        n++;
                    }
                }
            }
        }
        //如果i为奇数时，处理成偶数
        if (i % 2 == 1) {
            //该UCS2字符是汉字时，去掉这个截一半的汉字
            if (bytes[i - 1] != 0) {
                i = i - 1;
            } else { //该UCS2字符是字母或数字，则保留该字符
                if (bytes[i] == -73) {
                    i = i - 1;
                } else {
                    i = i + 1;
                }
            }
        }

        return new String(bytes, 0, i, "Unicode");
    }

    public static String substring(String s, int length, String part) throws Exception {

        byte[] bytes = s.getBytes("UnicodeBigUnmarked");
        int n = 0; //表示当前的字节数
        for (int i = 0; i < bytes.length && n < length; i += 2) {
            //首字节等于0非汉字，特殊处理间隔符(·)按两个字节计算
            if (bytes[i] == 0) {
                if (bytes[i + 1] == -73) {
                    n += 2;
                } else {
                    n++;
                }
            } else {
                n++;
            }
        }
        //如果i为奇数时，处理成偶数
        if (n % 2 == 1) {
            //该UCS2字符是汉字时，去掉这个截一半的汉字
            if (bytes[n - 1] != 0) {
                n = n - 1;
            } else { //该UCS2字符是字母或数字
                if (bytes[n] == -73) {
                    n = n - 1;
                } else {
                    n = n + 1;
                }
            }
        }

        if (part.equalsIgnoreCase("head")) {
            return new String(bytes, 0, n, "Unicode");
        } else {
            return new String(bytes, n, bytes.length - n, "Unicode");
        }
    }

    /**
     * 替换str1中首次出现的str2
     */
    public String replaceFirst(String str1, String str2) {
        StringBuilder stringBuffer = new StringBuilder(str1);
        int index = stringBuffer.indexOf(str1);
        stringBuffer.delete(index, index + str2.length());
        return stringBuffer.toString();
    }

}