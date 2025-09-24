package store.lookup.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import store.lookup.ApplicationException;
import store.lookup.domain.Store;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class ClasspathStoreProvider implements StoreProvider {

    private final String storeSourceFile;
    private final ObjectMapper objectMapper;

    public ClasspathStoreProvider(@Value("${store.source.file}") String storeSourceFile, ObjectMapper objectMapper) {
        this.storeSourceFile = storeSourceFile;
        this.objectMapper = objectMapper;
    }

    public List<Store> provide() {
        log.debug("Loading stores from {}", storeSourceFile);

        ClassPathResource resource = new ClassPathResource(storeSourceFile);
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(resource.getInputStream())) {
            return objectMapper.readValue(bufferedInputStream, StoreConfiguration.class).stores();
        } catch (IOException e) {
            throw new ApplicationException("Failed to read stores from JSON: " + storeSourceFile, e);
        }
    }
}
