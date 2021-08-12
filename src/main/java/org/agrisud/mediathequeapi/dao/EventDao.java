package org.agrisud.mediathequeapi.dao;

import org.agrisud.mediathequeapi.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Properties;

@Repository
public class EventDao {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    Properties sqlProperties;

    public List<Event> getEvents() {
        return jdbcTemplate.query(sqlProperties.getProperty("event.get.all"), Event::baseMapper);
    }
}
