package cn.sluk3r.luImpl.analysis;

import java.io.IOException;

/**
 * Created by wangxc on 2014/5/9.
 */
interface TokenStream {
    public boolean incrementToken() throws IOException;
    public Token nextToken();
}
