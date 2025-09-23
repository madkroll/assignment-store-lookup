package store.lookup.repository;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import store.lookup.domain.Store;

import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
@Profile("repository.in-memory")
public class InMemoryStoreLookupRepository implements StoreLookupRepository {

    @Override
    public List<Store> lookup(double latitude, double longitude) {
        return List.of(
                new Store(
                        UUID.randomUUID().toString(),
                        51.874272,
                        6.245829,
                        "'s-Heerenberg",
                        "7041 JE",
                        "Stadsplein",
                        "71",
                        "",
                        "Jumbo 's-Heerenberg Stadsplein",
                        30170,
                        4670,
                        true,
                        false
                )
        );
    }
}
