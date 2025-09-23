package store.lookup.domain;

public record Store(
        String uuid,
        Double latitude,
        Double longitude,
        LocationType locationType,
        // TODO: time?
        String todayOpen,
        // TODO: time?
        String todayClose,
        String city,
        String postalCode,
        String street,
        String street2,
        String street3,
        String addressName,
        Integer complexNumber,
        Integer sapStoreID,
        Boolean showWarningMessage,
        Boolean collectionPoint
) {
}
