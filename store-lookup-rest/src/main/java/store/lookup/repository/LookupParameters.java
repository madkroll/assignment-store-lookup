package store.lookup.repository;

import lombok.Builder;
import store.lookup.domain.LocationType;

import java.util.Set;

@Builder
public record LookupParameters(
        Integer limit,
        Double latitude,
        Double longitude,
        Set<LocationType> locationTypes,
        Boolean collectionPointAvailable
) {
}
