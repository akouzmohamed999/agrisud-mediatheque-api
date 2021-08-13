package org.agrisud.mediathequeapi.cloudservice;

import org.agrisud.mediathequeapi.clouddao.EventCloudDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EventCloudServiceTest {

    @Mock
    EventCloudDao eventCloudDao;

    EventCloudService eventCloudService;

    @BeforeEach
    public void setup() {
        eventCloudService = new EventCloudService();
        eventCloudService.eventCloudDao = eventCloudDao;
    }

    @Test
    public void shouldReturnEventFiles(){
        eventCloudService.getFolders();
        verify(eventCloudDao, times(1)).getEventFolders();
    }
}
