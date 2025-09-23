package store.lookup.repository;

import store.lookup.domain.Store;

import java.util.List;

public interface IndexStoreRepository {

    void index(List<Store> stores);
}
