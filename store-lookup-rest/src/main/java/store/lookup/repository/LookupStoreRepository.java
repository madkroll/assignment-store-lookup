package store.lookup.repository;

import store.lookup.domain.Store;

import java.util.List;

public interface LookupStoreRepository {

    List<Store> lookup(double latitude, double longitude);

}
