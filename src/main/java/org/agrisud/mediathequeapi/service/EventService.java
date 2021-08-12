package org.agrisud.mediathequeapi.service;

import org.agrisud.mediathequeapi.clouddao.EventCloudDao;
import org.agrisud.mediathequeapi.dao.EventDao;
import org.agrisud.mediathequeapi.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    EventDao eventDao;

    public List<Event> getEvents() {
        return eventDao.getEvents();
    }
}
