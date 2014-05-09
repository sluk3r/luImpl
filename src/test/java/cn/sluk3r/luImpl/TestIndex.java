/**
 * Created by wangxc on 2014/5/9.
 */
package cn.sluk3r.luImpl;


import cn.sluk3r.luImpl.document.Document;
import cn.sluk3r.luImpl.document.Field;
import org.junit.Test;

import cn.sluk3r.luImpl.analysis.Analyzer;
import cn.sluk3r.luImpl.analysis.StandardAnalyzer;
import cn.sluk3r.luImpl.index.IndexWriter;
import cn.sluk3r.luImpl.store.Directory;
import cn.sluk3r.luImpl.store.RAMDirectory;


public class TestIndex {

    @Test
    public void testDemo() {
        Analyzer analyzer = new StandardAnalyzer();

        // Store the index in memory:
        Directory directory = new RAMDirectory();

        IndexWriter iwriter = new IndexWriter(directory, analyzer, true,
                new IndexWriter.MaxFieldLength(25000));
        Document doc = new Document();
        String text = "This is the text to be indexed.";
        doc.add(new Field("fieldname", text, Field.Store.YES,
                Field.Index.ANALYZED));
        iwriter.addDocument(doc);
        iwriter.close();


//
//        // Now search the index:
//        IndexSearcher isearcher = new IndexSearcher(directory, true);
//        // Parse a simple query that searches for "text":
//        QueryParser parser = new QueryParser(Version.LUCENE_CURRENT, "fieldname", analyzer);
//        Query query = parser.parse("text");
//        ScoreDoc[] hits = isearcher.search(query, null, 1000).scoreDocs;
//        assertEquals(1, hits.length);
//        // Iterate through the results:
//        for (int i = 0; i < hits.length; i++) {
//            Document hitDoc = isearcher.doc(hits[i].doc);
//            assertEquals("This is the text to be indexed.", hitDoc.get("fieldname"));
//        }
//        isearcher.close();
//        directory.close();
    }

}
