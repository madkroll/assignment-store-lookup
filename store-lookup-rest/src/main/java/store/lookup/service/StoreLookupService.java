package store.lookup.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import store.lookup.domain.Store;
import store.lookup.repository.LookupStoreRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class StoreLookupService {

    private LookupStoreRepository lookupStoreRepository;

    public List<Store> lookup(double latitude, double longitude) {
        return lookupStoreRepository.lookup(latitude, longitude);
    }
}
