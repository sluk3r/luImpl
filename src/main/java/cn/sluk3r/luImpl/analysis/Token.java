package cn.sluk3r.luImpl.analysis;

/**
 * Created by wangxc on 2014/5/9.
 */
public class Token {
    String token;
    public Token(String content) {
        token = content;
    }

    @Override
    public String toString() {
        return "Token{" +
                "token='" + token + '\'' +
                '}';
    }

    public String getContent() {
        return token;
    }
}
