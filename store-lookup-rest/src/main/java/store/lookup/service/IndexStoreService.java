package store.lookup.service;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import store.lookup.config.StoreProvider;
import store.lookup.repository.IndexStoreRepository;

@Service
@AllArgsConstructor
public class IndexStoreService {

    private final StoreProvider storeProvider;
    private final IndexStoreRepository indexStoreRepository;

    @PostConstruct
    public void initializeStores() {
        indexStoreRepository.index(storeProvider.provide());
    }
}
