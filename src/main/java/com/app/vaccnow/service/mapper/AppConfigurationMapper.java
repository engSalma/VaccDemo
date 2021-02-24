package com.app.vaccnow.service.mapper;


import com.app.vaccnow.domain.*;
import com.app.vaccnow.service.dto.AppConfigurationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AppConfiguration} and its DTO {@link AppConfigurationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AppConfigurationMapper extends EntityMapper<AppConfigurationDTO, AppConfiguration> {



    default AppConfiguration fromId(String id) {
        if (id == null) {
            return null;
        }
        AppConfiguration appConfiguration = new AppConfiguration();
        appConfiguration.setId(id);
        return appConfiguration;
    }
}
