package com.app.vaccnow.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Branch.
 */
@Document(collection = "branch")
public class Branch extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @Field("address")
    private String address;

    @Field("vaccine_allowed")
    private Boolean vaccineAllowed;

    @Field("vaccine_stock")
    private String vaccineStock;

    @DBRef
    @Field("schedule")
    private Set<Schedule> schedules = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Branch name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public Branch address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean isVaccineAllowed() {
        return vaccineAllowed;
    }

    public Branch vaccineAllowed(Boolean vaccineAllowed) {
        this.vaccineAllowed = vaccineAllowed;
        return this;
    }

    public void setVaccineAllowed(Boolean vaccineAllowed) {
        this.vaccineAllowed = vaccineAllowed;
    }

    public String getVaccineStock() {
        return vaccineStock;
    }

    public Branch vaccineStock(String vaccineStock) {
        this.vaccineStock = vaccineStock;
        return this;
    }

    public void setVaccineStock(String vaccineStock) {
        this.vaccineStock = vaccineStock;
    }

    public Set<Schedule> getSchedules() {
        return schedules;
    }

    public Branch schedules(Set<Schedule> schedules) {
        this.schedules = schedules;
        return this;
    }

    public Branch addSchedule(Schedule schedule) {
        this.schedules.add(schedule);
        schedule.setBranch(this);
        return this;
    }

    public Branch removeSchedule(Schedule schedule) {
        this.schedules.remove(schedule);
        schedule.setBranch(null);
        return this;
    }

    public void setSchedules(Set<Schedule> schedules) {
        this.schedules = schedules;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Branch)) {
            return false;
        }
        return id != null && id.equals(((Branch) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Branch{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", vaccineAllowed='" + isVaccineAllowed() + "'" +
            ", vaccineStock='" + getVaccineStock() + "'" +
            "}";
    }
}
