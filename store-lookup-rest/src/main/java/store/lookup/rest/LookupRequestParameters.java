package store.lookup.rest;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LookupRequestParameters {

    @NotNull
    @Min(-90) @Max(90)
    private Double latitude;

    @NotNull
    @Min(-180) @Max(180)
    private Double longitude;

    private Boolean collectionPointAvailable;
}
