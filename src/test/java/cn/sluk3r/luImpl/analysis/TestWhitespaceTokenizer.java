package cn.sluk3r.luImpl.analysis;

import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Created by wangxc on 2014/5/9.
 */
public class TestWhitespaceTokenizer {
    @Test
    public void testNormal() throws IOException {
        WhitespaceAnalyzer whitespaceAnalyzer = new WhitespaceAnalyzer();

        String content = "C embedded developers wanted";
        StringReader reader = new StringReader(content);

        TokenStream stream = whitespaceAnalyzer.tokenStream(null, reader);

        String[] targets = content.split(" ");

        int i=0;
        while( stream.incrementToken()) {
            Token token = stream.nextToken();

            Pattern whitespace = Pattern.compile("\\s");
            Matcher matcher = whitespace.matcher(token.getContent());
            assertFalse("whitespace char should not in token, now the content is: " + token.getContent(), matcher.find());
            assertEquals(String.format("whitespace-splitted[%s] should be equals to self splitted[%s]", token.getContent(), targets[i]), targets[i], token.getContent());
            i++;

            System.out.println("\t\t\twhitespace-splitted: " + token.getContent());
        }
        assertEquals(String.format("分词出来的个数[%s]应该跟目标[%s]一致", i, targets.length), targets.length, i);
    }


    @Test
    public void testIncompleteToken() throws IOException {
        String content = "C embedded developers wanted";
        int firstBlankIndex = content.indexOf(" ");
        WhitespaceTokenizer.setIoBufferSize(firstBlankIndex + 1 + 2);

        StringReader reader = new StringReader(content);

        WhitespaceAnalyzer whitespaceAnalyzer = new WhitespaceAnalyzer();
        TokenStream stream = whitespaceAnalyzer.tokenStream(null, reader);

        String[] targets = content.split(" ");

        int i=0;
        while( stream.incrementToken()) {
            Token token = stream.nextToken();

            Pattern whitespace = Pattern.compile("\\s");
            Matcher matcher = whitespace.matcher(token.getContent());
            assertFalse("whitespace char should not in token, now the content is: " + token.getContent(), matcher.find());
            assertEquals(String.format("whitespace-splitted[%s] should be equals to self splitted[%s]", token.getContent(), targets[i]), targets[i], token.getContent());
            i++;

            System.out.println("\t\t\twhitespace-splitted: " + token.getContent());
        }
        assertEquals(String.format("分词出来的个数[%s]应该跟目标[%s]一致", i, targets.length), targets.length, i);
    }
}
