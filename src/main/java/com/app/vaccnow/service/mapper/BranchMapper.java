package com.app.vaccnow.service.mapper;


import com.app.vaccnow.domain.*;
import com.app.vaccnow.service.dto.BranchDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Branch} and its DTO {@link BranchDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BranchMapper extends EntityMapper<BranchDTO, Branch> {


    @Mapping(target = "schedules", ignore = true)
    @Mapping(target = "removeSchedule", ignore = true)
    Branch toEntity(BranchDTO branchDTO);

    default Branch fromId(String id) {
        if (id == null) {
            return null;
        }
        Branch branch = new Branch();
        branch.setId(id);
        return branch;
    }
}
