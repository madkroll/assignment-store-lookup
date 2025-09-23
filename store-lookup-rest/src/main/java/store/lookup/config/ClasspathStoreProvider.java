package store.lookup.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import store.lookup.domain.Store;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.List;

@Component
public class ClasspathStoreProvider implements StoreProvider {

    private final String storeSourceFile;
    private final ObjectMapper objectMapper;

    public ClasspathStoreProvider(@Value("${store.source.file}") String storeSourceFile, ObjectMapper objectMapper) {
        this.storeSourceFile = storeSourceFile;
        this.objectMapper = objectMapper;
    }

    public List<Store> provide() {
        ClassPathResource resource = new ClassPathResource(storeSourceFile);

        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(resource.getInputStream())) {
            return objectMapper.readValue(bufferedInputStream, StoreConfiguration.class).stores();
        } catch (IOException e) {
            // TODO: exception handling
            throw new RuntimeException(e);
        }
    }
}
