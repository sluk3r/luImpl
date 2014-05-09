package cn.sluk3r.luImpl.analysis;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by wangxc on 2014/5/9.
 */
public class WhitespaceTokenizer implements TokenStream {
    private static char BLANK_CHAR = '\u0000';

    private static int IO_BUFFER_SIZE = 4096;
    private char[] ioBuffer = new char[IO_BUFFER_SIZE];
    private int bufferIndex, offset = 0;
    private int lastBlankIndex = 0;

    Reader in;
    public WhitespaceTokenizer(Reader reader) {
        in = reader;
    }

    @Override
    public boolean incrementToken() throws IOException {
        if (! bufferEmpty(ioBuffer)) {//缓存里已有时， 直接从缓存里读。
            if (ioBuffer[offset] == BLANK_CHAR) {
                return false;
            }
            if (! hasWhiteSpanceInLeft(ioBuffer, offset) ) {
                ioBuffer = new char[IO_BUFFER_SIZE];
                in.reset();
//                int dataLen = in.read(ioBuffer, lastBlankIndex + 1, ioBuffer.length - lastBlankIndex + 1); //先读出来，
                in.skip(lastBlankIndex + 1);
                int dataLen = in.read(ioBuffer); //先读出来，
                if (dataLen > 0 ) {
                    //由于IO_BUFFER_SIZE很大（4096），没有一个单词的长度超过这个， 就先不考虑buffer里只有半截词的情况。
                    for (int i=0;i<dataLen;i++) {
                        char c = ioBuffer[i];
                        if (Character.isWhitespace(c)) {
                            offset = i;
                            lastBlankIndex = offset + bufferIndex;
                            return  true;
                        }
                    }

                }
            } else {
                for (int i=bufferIndex;i<ioBuffer.length;i++) {
                    char c = ioBuffer[i];
                    if (Character.isWhitespace(c)) {
                        offset = i;
                        lastBlankIndex = offset + bufferIndex;

                        return  true;
                    }
                }
            }


        } else {
            int dataLen = in.read(ioBuffer, lastBlankIndex, ioBuffer.length); //先读出来，

            if (dataLen > 0 ) {
                //由于IO_BUFFER_SIZE很大（4096），没有一个单词的长度超过这个， 就先不考虑buffer里只有半截词的情况。
                for (int i=0;i<dataLen;i++) {
                    char c = ioBuffer[i];
                    if (Character.isWhitespace(c)) {
                        offset = i;
                        lastBlankIndex = offset + bufferIndex;
                        return  true;
                    }
                }

            }
        }
        return  false;
    }


    boolean hasWhiteSpanceInLeft(char[] ioBuffer, int offset) {
        String left = new String(ioBuffer, offset+1, ioBuffer.length - offset - 1);
        return left.contains(" ");
    }

//    @Override
//    public boolean incrementToken() throws IOException {
//        if (! bufferEmpty(ioBuffer)) {//缓存里已有时， 直接从缓存里读。
//            if (ioBuffer[offset] == BLANK_CHAR) {
//                return false;
//            }
//
//            for (int i=bufferIndex;i<ioBuffer.length;i++) {
//                char c = ioBuffer[i];
////                if (Character.isWhitespace(c) || i == ioBuffer.length - 1) {
//                if (Character.isWhitespace(c) || i == ioBuffer.length - 1 || c == BLANK_CHAR) {
//                    offset = i;
//                    return  true;
//                }
//            }
//        } else {
//            int dataLen = in.read(ioBuffer); //先读出来，
//
//            if (dataLen > 0 ) {
//                //由于IO_BUFFER_SIZE很大（4096），没有一个单词的长度超过这个， 就先不考虑buffer里只有半截词的情况。
//                for (int i=0;i<dataLen;i++) {
//                    char c = ioBuffer[i];
//                    if (Character.isWhitespace(c)) {
//                        offset = i;
//                        return  true;
//                    }
//                }
//
//            }
//        }
//        return  false;
//    }


    static boolean bufferEmpty(char[] buffer) {
        return buffer[0] == BLANK_CHAR;
    }


    @Override
    public Token nextToken() {
        String result = new String(ioBuffer, bufferIndex, offset - bufferIndex);
        bufferIndex = offset + 1;
        return new Token(result);
    }

    //TODO, 这个不明白有什么特殊的作用， 先抄过来。 FIXME
    protected boolean isTokenChar(char c) {
        return !Character.isWhitespace(c);
    }

    public static void setIoBufferSize(int ioBufferSize) {
        IO_BUFFER_SIZE = ioBufferSize;
    }
}
