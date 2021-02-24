package com.app.vaccnow.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Schedule.
 */
@Document(collection = "schedule")
public class Schedule extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("date")
    private LocalDate date;

    @Field("is_working_day")
    private Boolean isWorkingDay;

    @DBRef
    @Field("branch")
    @JsonIgnoreProperties(value = "schedules", allowSetters = true)
    private Branch branch;

    @DBRef
    @Field("timeslot")
    private Set<Timeslot> timeslots = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Schedule date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean isIsWorkingDay() {
        return isWorkingDay;
    }

    public Schedule isWorkingDay(Boolean isWorkingDay) {
        this.isWorkingDay = isWorkingDay;
        return this;
    }

    public void setIsWorkingDay(Boolean isWorkingDay) {
        this.isWorkingDay = isWorkingDay;
    }

    public Branch getBranch() {
        return branch;
    }

    public Schedule branch(Branch branch) {
        this.branch = branch;
        return this;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Set<Timeslot> getTimeslots() {
        return timeslots;
    }

    public Schedule timeslots(Set<Timeslot> timeslots) {
        this.timeslots = timeslots;
        return this;
    }

    public Schedule addTimeslot(Timeslot timeslot) {
        this.timeslots.add(timeslot);
        timeslot.setSchedule(this);
        return this;
    }

    public Schedule removeTimeslot(Timeslot timeslot) {
        this.timeslots.remove(timeslot);
        timeslot.setSchedule(null);
        return this;
    }

    public void setTimeslots(Set<Timeslot> timeslots) {
        this.timeslots = timeslots;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Schedule)) {
            return false;
        }
        return id != null && id.equals(((Schedule) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

	@Override
	public String toString() {
		return "Schedule [id=" + id + ", date=" + date + ", isWorkingDay=" + isWorkingDay + ", branch=" + branch
				+ ", timeslots=" + timeslots + "]";
	}

    // prettier-ignore

}
