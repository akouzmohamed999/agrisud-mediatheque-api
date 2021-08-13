package org.agrisud.mediathequeapi.service;

import org.agrisud.mediathequeapi.dao.EventDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    EventDao eventDao;

    EventService eventService;

    @BeforeEach
    public void setup() {
        eventService = new EventService();
        eventService.eventDao = eventDao;
    }

    @Test
    public void shouldGetEvents(){
        eventService.getEvents();
        verify(eventDao, times(1)).getEvents();
    }
}
