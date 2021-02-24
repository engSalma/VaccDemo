package com.app.vaccnow.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import com.app.vaccnow.domain.enumeration.DataType;

/**
 * A DTO for the {@link com.app.vaccnow.domain.AppConfiguration} entity.
 */
public class AppConfigurationDTO implements Serializable {
    
    private String id;

    @NotNull
    private String key;

    @NotNull
    private DataType dataType;

    private String pattern;

    @NotNull
    private String value;

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppConfigurationDTO)) {
            return false;
        }

        return id != null && id.equals(((AppConfigurationDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppConfigurationDTO{" +
            "id=" + getId() +
            ", key='" + getKey() + "'" +
            ", dataType='" + getDataType() + "'" +
            ", pattern='" + getPattern() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
