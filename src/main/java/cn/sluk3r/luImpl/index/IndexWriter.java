package cn.sluk3r.luImpl.index;

import cn.sluk3r.luImpl.analysis.Analyzer;
import cn.sluk3r.luImpl.document.Document;
import cn.sluk3r.luImpl.store.Directory;

/**
 * Created by wangxc on 2014/5/9.
 */
public class IndexWriter {
    public IndexWriter(Directory directory, Analyzer analyzer, boolean b, MaxFieldLength maxFieldLength) {

    }

    public void addDocument(Document doc) {

    }

    public void close() {
    }

    public  static final class MaxFieldLength {
        public MaxFieldLength(int i) {
        }
    }
}
