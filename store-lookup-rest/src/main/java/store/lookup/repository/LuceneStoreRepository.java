package store.lookup.repository;

import lombok.AllArgsConstructor;
import org.apache.lucene.document.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import store.lookup.domain.LocationType;
import store.lookup.domain.Store;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
                doc.add(new IntPoint("showWarningMessage", toIntegerFieldValue(store.showWarningMessage())));
                doc.add(new StoredField("showWarningMessage", toIntegerFieldValue(store.showWarningMessage())));
                doc.add(new IntPoint("collectionPoint", toIntegerFieldValue(store.collectionPoint())));
                doc.add(new StoredField("collectionPoint", toIntegerFieldValue(store.collectionPoint())));

                writer.addDocument(doc);
            }

            writer.commit();
        } catch (IOException e) {
            throw new RepositoryOperationException("Failed to index " + stores.size() + " documents.", e);
        }
    }

    @Override
    public List<Store> lookup(LookupParameters lookupParameters) {
        try (DirectoryReader reader = DirectoryReader.open(directory)) {
            Query acrossAllDocuments = new MatchAllDocsQuery();
            Query oneOfLocationTypes = new TermInSetQuery(
                    "locationType",
                    lookupParameters.locationTypes()
                            .stream()
                            .map(locationType -> new Term("locationType", locationType.getText()).bytes()).toList()
            );

            BooleanQuery.Builder queryBuilder =
                    new BooleanQuery.Builder()
                            .add(acrossAllDocuments, BooleanClause.Occur.MUST)
                            .add(oneOfLocationTypes, BooleanClause.Occur.FILTER);

            if (Objects.nonNull(lookupParameters.collectionPointAvailable())) {
                Query considerCollectionPoint = IntPoint.newExactQuery("collectionPoint", toIntegerFieldValue(lookupParameters.collectionPointAvailable()));
                queryBuilder.add(considerCollectionPoint, BooleanClause.Occur.FILTER);
            }

            Sort byDistance = new Sort(LatLonDocValuesField.newDistanceSort("location", lookupParameters.latitude(), lookupParameters.longitude()));

            IndexSearcher searcher = new IndexSearcher(reader);
            BooleanQuery lookupQuery = queryBuilder.build();
            TopDocs topDocsByCriteria = searcher.search(lookupQuery, lookupParameters.limit(), byDistance);

            List<Store> results = new ArrayList<>();
            for (ScoreDoc scoreDoc : topDocsByCriteria.scoreDocs) {
                Document doc = searcher.storedFields().document(scoreDoc.doc);

                results.add(
                        new Store(
                                doc.get("uuid"),
                                fieldAsDouble(doc.getField("latitude")),
                                fieldAsDouble(doc.getField("longitude")),
                                LocationType.byText(doc.get("locationType")),
                                doc.get("todayOpen"),
                                doc.get("todayClose"),
                                doc.get("city"),
                                doc.get("postalCode"),
                                doc.get("street"),
                                doc.get("street2"),
                                doc.get("street3"),
                                doc.get("addressName"),
                                fieldAsInteger(doc.getField("complexNumber")),
                                fieldAsInteger(doc.getField("sapStoreID")),
                                fieldAsBoolean(doc.getField("showWarningMessage")),
                                fieldAsBoolean(doc.getField("collectionPoint"))
                        )
                );
            }

            return results;
        } catch (IOException e) {
            throw new RepositoryOperationException("Failed to query documents ", e);
        }
    }

    private int toIntegerFieldValue(Boolean value) {
        return value != null && value ? 1 : 0;
    }

    private Integer fieldAsInteger(IndexableField field) {
        return field != null && field.numericValue() != null ? field.numericValue().intValue() : null;
    }

    private Double fieldAsDouble(IndexableField field) {
        return field != null && field.numericValue() != null ? field.numericValue().doubleValue() : null;
    }

    private Boolean fieldAsBoolean(IndexableField field) {
        return field != null && field.numericValue() != null ? field.numericValue().intValue() == 1 : null;
    }
}
