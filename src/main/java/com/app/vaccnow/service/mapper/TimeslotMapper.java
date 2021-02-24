package com.app.vaccnow.service.mapper;


import com.app.vaccnow.domain.*;
import com.app.vaccnow.service.dto.TimeslotDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Timeslot} and its DTO {@link TimeslotDTO}.
 */
@Mapper(componentModel = "spring", uses = {ScheduleMapper.class})
public interface TimeslotMapper extends EntityMapper<TimeslotDTO, Timeslot> {

    @Mapping(source = "schedule.id", target = "scheduleId")
    @Mapping(source = "schedule.date", target = "scheduleDate")
    TimeslotDTO toDto(Timeslot timeslot);

    @Mapping(source = "scheduleId", target = "schedule")
    Timeslot toEntity(TimeslotDTO timeslotDTO);

    default Timeslot fromId(String id) {
        if (id == null) {
            return null;
        }
        Timeslot timeslot = new Timeslot();
        timeslot.setId(id);
        return timeslot;
    }
}
