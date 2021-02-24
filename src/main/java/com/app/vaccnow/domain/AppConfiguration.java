package com.app.vaccnow.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.app.vaccnow.domain.enumeration.DataType;

/**
 * A AppConfiguration.
 */
@Document(collection = "app_configuration")
public class AppConfiguration extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("key")
    private String key;

    @NotNull
    @Field("data_type")
    private DataType dataType;

    @Field("pattern")
    private String pattern;

    @NotNull
    @Field("value")
    private String value;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public AppConfiguration key(String key) {
        this.key = key;
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public DataType getDataType() {
        return dataType;
    }

    public AppConfiguration dataType(DataType dataType) {
        this.dataType = dataType;
        return this;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public String getPattern() {
        return pattern;
    }

    public AppConfiguration pattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getValue() {
        return value;
    }

    public AppConfiguration value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppConfiguration)) {
            return false;
        }
        return id != null && id.equals(((AppConfiguration) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppConfiguration{" +
            "id=" + getId() +
            ", key='" + getKey() + "'" +
            ", dataType='" + getDataType() + "'" +
            ", pattern='" + getPattern() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
