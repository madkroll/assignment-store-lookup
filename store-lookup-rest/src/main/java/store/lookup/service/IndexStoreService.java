package store.lookup.service;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import store.lookup.config.StoreProvider;
import store.lookup.repository.IndexStoreRepository;

@Slf4j
@Service
@AllArgsConstructor
public class IndexStoreService {

    private final StoreProvider storeProvider;
    private final IndexStoreRepository indexStoreRepository;

    @PostConstruct
    public void initializeStores() {
        log.info("Initializing indexing stores.");
        indexStoreRepository.index(storeProvider.provide());
    }
}
