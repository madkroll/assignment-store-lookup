package store.lookup.rest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.lookup.domain.Store;
import store.lookup.service.StoreLookupService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/lookup")
@AllArgsConstructor
class LookupController {

    private final StoreLookupService storeLookupService;

    @GetMapping
    private ResponseEntity<List<Store>> lookup() {
        double latitude = 0, longitude = 0;
        return ResponseEntity.ok(storeLookupService.lookup(latitude, longitude));
    }
}
