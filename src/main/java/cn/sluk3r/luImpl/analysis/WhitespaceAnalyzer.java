package cn.sluk3r.luImpl.analysis;

import java.io.Reader;

/**
 * Created by wangxc on 2014/5/9.
 */
public class WhitespaceAnalyzer implements Analyzer {
    @Override
    public TokenStream tokenStream(String fieldName, Reader reader) {
        return  new WhitespaceTokenizer(reader);
    }
}
