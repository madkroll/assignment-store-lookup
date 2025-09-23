package store.lookup.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import store.lookup.domain.Store;
import store.lookup.repository.StoreLookupRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class StoreLookupService {

    private StoreLookupRepository storeLookupRepository;

    public List<Store> lookup(double latitude, double longitude) {
        return storeLookupRepository.lookup(latitude, longitude);
    }
}
