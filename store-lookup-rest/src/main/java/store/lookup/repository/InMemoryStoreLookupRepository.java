package store.lookup.repository;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import store.lookup.domain.Store;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
@AllArgsConstructor
@Profile("repository.in-memory")
public class InMemoryStoreLookupRepository implements StoreLookupRepository, IndexStoreRepository {

    private final List<Store> stores = new CopyOnWriteArrayList<>();

    @Override
    public List<Store> lookup(double latitude, double longitude) {
        return stores.stream().limit(5).toList();
    }

    @Override
    public void index(List<Store> stores) {
        this.stores.addAll(stores);
    }
}
