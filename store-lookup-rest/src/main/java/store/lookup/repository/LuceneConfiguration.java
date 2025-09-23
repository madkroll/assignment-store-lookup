package store.lookup.repository;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.apache.lucene.store.Directory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class LuceneConfiguration {

    @Bean(destroyMethod = "close")
    public Analyzer analyzer() {
        return new StandardAnalyzer();
    }

    @Bean(destroyMethod = "close")
    public Directory directory() {
        return new ByteBuffersDirectory();
    }

    @Bean(destroyMethod = "close")
    public IndexWriter writer(Analyzer analyzer, Directory directory) throws IOException {
        return new IndexWriter(directory, new IndexWriterConfig(analyzer));
    }
}
