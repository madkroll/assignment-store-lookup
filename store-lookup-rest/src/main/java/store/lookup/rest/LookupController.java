package store.lookup.rest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.lookup.domain.Store;
import store.lookup.service.LocationLookupService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/lookup")
@AllArgsConstructor
class LookupController {

    private final LocationLookupService locationLookupService;

    @GetMapping("/store")
    private ResponseEntity<List<Store>> lookupStore(@Validated LookupRequestParameters requestParameters) {
        return ResponseEntity.ok(
                locationLookupService.lookupStore(
                        requestParameters.getLatitude(),
                        requestParameters.getLongitude(),
                        requestParameters.getCollectionPointAvailable()
                )
        );
    }

    @GetMapping("/pickup")
    private ResponseEntity<List<Store>> lookupPickUpPoint(@Validated LookupRequestParameters requestParameters) {
        return ResponseEntity.ok(
                locationLookupService.lookupPickUpPoint(requestParameters.getLatitude(), requestParameters.getLongitude())
        );
    }
}
