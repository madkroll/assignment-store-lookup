package store.lookup.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import store.lookup.repository.LookupStoreRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LookupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoSpyBean
    private LookupStoreRepository lookupStoreRepository;

    @Test
    void shouldReturnStoresWithPickUp() throws Exception {
        mockMvc.perform(
                        get("/lookup/store")
                                .param("latitude", "1.1")
                                .param("longitude", "1.1")
                                .param("collectionPointAvailable", "true")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"uuid\":\"uuid-2\",\"latitude\":12.11,\"longitude\":12.111,\"locationType\":\"SupermarktPuP\",\"todayOpen\":\"02:00\",\"todayClose\":\"12:00\",\"city\":\"city-2\",\"postalCode\":\"postal-code-2\",\"street\":\"street-2\",\"street2\":\"street2-2\",\"street3\":\"street3-2\",\"addressName\":\"address-name-2\",\"complexNumber\":2,\"sapStoreID\":2,\"showWarningMessage\":false,\"collectionPoint\":true}]"));
    }

    @Test
    void shouldReturnStoresWithoutPickUp() throws Exception {
        mockMvc.perform(
                        get("/lookup/store")
                                .param("latitude", "1.1")
                                .param("longitude", "1.1")
                                .param("collectionPointAvailable", "false")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"uuid\":\"uuid-1\",\"latitude\":11.11,\"longitude\":11.111,\"locationType\":\"Supermarkt\",\"todayOpen\":\"01:00\",\"todayClose\":\"11:00\",\"city\":\"city-1\",\"postalCode\":\"postal-code-1\",\"street\":\"street-1\",\"street2\":\"street2-1\",\"street3\":\"street3-1\",\"addressName\":\"address-name-1\",\"complexNumber\":1,\"sapStoreID\":1,\"showWarningMessage\":false,\"collectionPoint\":false}]"));
    }

    @Test
    void whenRequiredParameterIsMissing_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(
                        get("/lookup/store")
                                .param("latitude", "11.11")
                                .param("collectionPointAvailable", "true")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnPickUpLocations() throws Exception {
        mockMvc.perform(
                        get("/lookup/pickup")
                                .param("latitude", "1.1")
                                .param("longitude", "1.1")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"uuid\":\"uuid-2\",\"latitude\":12.11,\"longitude\":12.111,\"locationType\":\"SupermarktPuP\",\"todayOpen\":\"02:00\",\"todayClose\":\"12:00\",\"city\":\"city-2\",\"postalCode\":\"postal-code-2\",\"street\":\"street-2\",\"street2\":\"street2-2\",\"street3\":\"street3-2\",\"addressName\":\"address-name-2\",\"complexNumber\":2,\"sapStoreID\":2,\"showWarningMessage\":false,\"collectionPoint\":true}]"));
    }
}