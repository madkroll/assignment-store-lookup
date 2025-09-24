package store.lookup.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import store.lookup.domain.LocationType;
import store.lookup.domain.Store;
import store.lookup.repository.LookupParameters;
import store.lookup.repository.LookupStoreRepository;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class LocationLookupService {

    private final Integer lookupLimit;
    private final LookupStoreRepository lookupStoreRepository;

    public LocationLookupService(@Value("${store.lookup.limit}") Integer lookupLimit, LookupStoreRepository lookupStoreRepository) {
        this.lookupLimit = lookupLimit;
        this.lookupStoreRepository = lookupStoreRepository;
    }

    public List<Store> lookupStore(Double latitude, Double longitude, Boolean collectionPointAvailable) {
        LookupParameters.LookupParametersBuilder lookupQueryBuilder = LookupParameters.builder()
                .limit(lookupLimit)
                .latitude(latitude)
                .longitude(longitude)
                .locationTypes(Set.of(LocationType.SUPERMARKT, LocationType.SUPERMARKTPUP));

        if (Objects.nonNull(collectionPointAvailable)) {
            lookupQueryBuilder.collectionPointAvailable(collectionPointAvailable);
        }

        LookupParameters lookupParameters = lookupQueryBuilder.build();

        return lookupStoreRepository.lookup(lookupParameters);
    }

    public List<Store> lookupPickUpPoint(Double latitude, Double longitude) {
        LookupParameters lookupPickUpPointQuery = LookupParameters.builder()
                .limit(lookupLimit)
                .latitude(latitude)
                .longitude(longitude)
                .locationTypes(Set.of(LocationType.SUPERMARKTPUP, LocationType.PUP))
                .collectionPointAvailable(true)
                .build();

        return lookupStoreRepository.lookup(lookupPickUpPointQuery);
    }
}
