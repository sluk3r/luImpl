package cn.sluk3r.luImpl.analysis;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by wangxc on 2014/5/9.
 */
public class WhitespaceTokenizer implements TokenStream {
    private static int IO_BUFFER_SIZE = 1024;
    private static final int MAX_WORK_LENGTH = 255;

    private char[] ioBuffer = new char[IO_BUFFER_SIZE];
    private char[] resultBuffer = new char[MAX_WORK_LENGTH];
    int resultBufferLength = 0;
    int dataLen = 0;
    int bufferIndex = 0;


    Reader in;
    public WhitespaceTokenizer(Reader reader) {
        in = reader;
    }

    @Override
    public boolean incrementToken() throws IOException {
        while(true) {
            if (bufferIndex >= dataLen) {
                dataLen = in.read(ioBuffer);
                bufferIndex = 0;
            }

            if (dataLen == -1) {
                dataLen = 0; //wangxc 下次循环时， 再从in里读一次，这时resultBufferLength肯定是0， 也就return false了。  TODO, 这里会多读一次IO， 会不会有性能方面的影响？
                if (resultBufferLength > 0 ) { //wangxc, 表示还有最终的结果没有取走。
                    return true;
                }
                return false;
            }

            char c = ioBuffer[bufferIndex ++];
            if (! Character.isWhitespace(c)) {
                resultBuffer[resultBufferLength++] = c;
            } else { //wangxc 如果是空白时， 返回true
                return true;
            }

//            if (bufferIndex == dataLen) {
//                return true;
//            }
        }
    }

    @Override
    public Token nextToken() {
        String result = new String(resultBuffer, 0, resultBufferLength);
        resultBufferLength = 0;
        return new Token(result);
    }

    public static void setIoBufferSize(int ioBufferSize) {
        IO_BUFFER_SIZE = ioBufferSize;
    }
}
