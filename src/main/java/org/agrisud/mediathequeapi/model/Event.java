package org.agrisud.mediathequeapi.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@Setter
public class Event {
    private Long id;
    private String title;
    private String description;

    public static Event baseMapper(ResultSet resultSet, int rowNumber) throws SQLException {
        Event event = new Event();
        event.setId(resultSet.getLong("event_id"));
        event.setTitle(resultSet.getString("event_title"));
        event.setDescription(resultSet.getString("event_description"));
        return event;
    }

}
