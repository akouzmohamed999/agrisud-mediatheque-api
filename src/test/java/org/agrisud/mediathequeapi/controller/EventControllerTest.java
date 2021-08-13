package org.agrisud.mediathequeapi.controller;


import org.agrisud.mediathequeapi.cloudservice.EventCloudService;
import org.agrisud.mediathequeapi.service.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @MockBean
    private EventCloudService eventCloudService;

    @Test
    public void shouldGetEvents() throws Exception {
        mockMvc.perform(get("/event"))
                .andExpect(status().isOk());
        verify(eventService, times(1)).getEvents();
    }

    @Test
    public void shouldGetEventsFiles() throws Exception {
        mockMvc.perform(get("/event/files"))
                .andExpect(status().isOk());
        verify(eventCloudService, times(1)).getFolders();
    }
}
