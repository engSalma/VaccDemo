package com.app.vaccnow.service.mapper;


import com.app.vaccnow.domain.*;
import com.app.vaccnow.service.dto.ScheduleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Schedule} and its DTO {@link ScheduleDTO}.
 */
@Mapper(componentModel = "spring", uses = {BranchMapper.class})
public interface ScheduleMapper extends EntityMapper<ScheduleDTO, Schedule> {

    @Mapping(source = "branch.id", target = "branchId")
    @Mapping(source = "branch.name", target = "branchName")
    ScheduleDTO toDto(Schedule schedule);

    @Mapping(source = "branchId", target = "branch")
    @Mapping(target = "timeslots", ignore = true)
    @Mapping(target = "removeTimeslot", ignore = true)
    Schedule toEntity(ScheduleDTO scheduleDTO);

    default Schedule fromId(String id) {
        if (id == null) {
            return null;
        }
        Schedule schedule = new Schedule();
        schedule.setId(id);
        return schedule;
    }
}
