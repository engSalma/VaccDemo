package com.app.vaccnow.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.app.vaccnow.domain.Schedule} entity.
 */
public class ScheduleDTO implements Serializable {
    
    private String id;

    @NotNull
    private LocalDate date;

    private Boolean isWorkingDay;


    private String branchId;

    private String branchName;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean isIsWorkingDay() {
        return isWorkingDay;
    }

    public void setIsWorkingDay(Boolean isWorkingDay) {
        this.isWorkingDay = isWorkingDay;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScheduleDTO)) {
            return false;
        }

        return id != null && id.equals(((ScheduleDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ScheduleDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", isWorkingDay='" + isIsWorkingDay() + "'" +
            ", branchId='" + getBranchId() + "'" +
            ", branchName='" + getBranchName() + "'" +
            "}";
    }
}
