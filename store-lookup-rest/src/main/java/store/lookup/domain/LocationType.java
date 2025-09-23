package store.lookup.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum LocationType {

    SUPERMARKT("Supermarkt"),
    PUP("PuP"),
    SUPERMARKTPUP("SupermarktPuP");

    @JsonValue
    private final String text;

    @JsonCreator
    private LocationType byText(String text) {
        return Arrays
                .stream(LocationType.values())
                .filter(locationType -> locationType.text.equals(text))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown location type: " + text));
    }
}
