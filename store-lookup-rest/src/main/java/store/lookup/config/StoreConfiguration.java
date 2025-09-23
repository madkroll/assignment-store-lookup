package store.lookup.config;

import store.lookup.domain.Store;

import java.util.List;

public record StoreConfiguration(
        List<Store> stores,
        Attributes attributes
) {
}
