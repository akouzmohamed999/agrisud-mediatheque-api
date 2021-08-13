package org.agrisud.mediathequeapi.clouddao;

import org.aarboard.nextcloud.api.NextcloudConnector;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class EventCloudDaoTest {

    @Mock
    NextcloudConnector connector;

    EventCloudDao eventCloudDao;

    @BeforeEach()
    public void setup() {
        eventCloudDao = new EventCloudDao();
        eventCloudDao.connector = connector;
    }

    @Test
    public void shouldGetFolders() {
        eventCloudDao.getEventFolders();
        verify(connector, times(1)).listFolderContent("/");
    }
}
