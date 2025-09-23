package store.lookup.config;

import store.lookup.domain.Store;

import java.util.List;

public interface StoreProvider {

    List<Store> provide();
}
