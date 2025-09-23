package store.lookup.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum LocationType {

    SUPERMARKT("Supermarkt"),
    PUP("PuP"),
    SUPERMARKTPUP("SupermarktPuP");

    private final String text;

    private Optional<LocationType> byText(String text) {
        return Arrays
                .stream(LocationType.values())
                .filter(locationType -> locationType.text.equals(text))
                .findFirst();
    }

}
