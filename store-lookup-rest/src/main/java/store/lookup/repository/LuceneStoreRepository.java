package store.lookup.repository;

import lombok.AllArgsConstructor;
import org.apache.lucene.document.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import store.lookup.domain.LocationType;
import store.lookup.domain.Store;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
@Profile("repository.lucene")
public class LuceneStoreRepository implements LookupStoreRepository, IndexStoreRepository {

    private final Directory directory;
    private final IndexWriter writer;

    @Override
    public void index(List<Store> stores) {
        try {
            for (Store store : stores) {
                Document doc = new Document();
                doc.add(new StringField("uuid", store.uuid(), Field.Store.YES));
                doc.add(new LatLonPoint("location", store.latitude(), store.longitude()));
                doc.add(new LatLonDocValuesField("location", store.latitude(), store.longitude()));
                doc.add(new StoredField("latitude", store.latitude()));
                doc.add(new StoredField("longitude", store.longitude()));
                doc.add(new StringField("locationType", store.locationType().getText(), Field.Store.YES));
                doc.add(new StringField("todayOpen", store.todayOpen(), Field.Store.YES));
                doc.add(new StringField("todayClose", store.todayClose(), Field.Store.YES));
                doc.add(new StringField("city", store.city(), Field.Store.YES));
                doc.add(new StringField("postalCode", store.postalCode(), Field.Store.YES));
                doc.add(new StringField("street", store.street(), Field.Store.YES));
                doc.add(new StringField("street2", store.street2(), Field.Store.YES));
                doc.add(new StringField("street3", store.street3(), Field.Store.YES));
                doc.add(new StringField("addressName", store.addressName(), Field.Store.YES));
                doc.add(new IntPoint("complexNumber", store.complexNumber()));
                doc.add(new StoredField("complexNumber", store.complexNumber()));
                doc.add(new IntPoint("sapStoreID", store.sapStoreID()));
                doc.add(new StoredField("sapStoreID", store.sapStoreID()));
                doc.add(new IntPoint("showWarningMessage", toInteger(store.showWarningMessage())));
                doc.add(new StoredField("showWarningMessage", toInteger(store.showWarningMessage())));
                doc.add(new IntPoint("collectionPoint", toInteger(store.collectionPoint())));
                doc.add(new StoredField("collectionPoint", toInteger(store.collectionPoint())));

                writer.addDocument(doc);
            }

            writer.commit();
        } catch (IOException e) {
            // TODO: handle exception
            throw new RuntimeException(e);
        }
    }

    private int toInteger(Boolean value) {
        return value != null && value ? 1 : 0;
    }

    @Override
    public List<Store> lookup(double latitude, double longitude) {
        try (DirectoryReader reader = DirectoryReader.open(directory)) {
            IndexSearcher searcher = new IndexSearcher(reader);

            Sort byDistance = new Sort(LatLonDocValuesField.newDistanceSort("location", latitude, longitude));

            // TODO: parametrize
            int lookupLimit = 5;
            TopDocs topDocs = searcher.search(new MatchAllDocsQuery(), lookupLimit, byDistance);

            List<Store> results = new ArrayList<>();
            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                Document doc = searcher.storedFields().document(scoreDoc.doc);

                results.add(
                        new Store(
                                doc.get("uuid"),
                                doc.getField("latitude").numericValue().doubleValue(),
                                doc.getField("longitude").numericValue().doubleValue(),
                                LocationType.byText(doc.get("locationType")),
                                doc.get("todayOpen"),
                                doc.get("todayClose"),
                                doc.get("city"),
                                doc.get("postalCode"),
                                doc.get("street"),
                                doc.get("street2"),
                                doc.get("street3"),
                                doc.get("addressName"),
                                doc.getField("complexNumber").numericValue().intValue(),
                                doc.getField("sapStoreID").numericValue().intValue(),
                                doc.getField("showWarningMessage").numericValue().intValue() == 1,
                                doc.getField("collectionPoint").numericValue().intValue() == 1
                        )
                );
            }

            return results;
        } catch (IOException e) {
            // TODO: handle error
            throw new RuntimeException(e);
        }
    }


}
