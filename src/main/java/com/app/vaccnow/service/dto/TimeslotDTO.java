package com.app.vaccnow.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import com.app.vaccnow.domain.enumeration.PaymentMethod;

/**
 * A DTO for the {@link com.app.vaccnow.domain.Timeslot} entity.
 */
public class TimeslotDTO implements Serializable {
    
    private String id;

    @NotNull
    private ZonedDateTime from;

    @NotNull
    private ZonedDateTime to;

    private String status;

    private PaymentMethod payment;


    private String scheduleId;

    private String scheduleDate;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ZonedDateTime getFrom() {
        return from;
    }

    public void setFrom(ZonedDateTime from) {
        this.from = from;
    }

    public ZonedDateTime getTo() {
        return to;
    }

    public void setTo(ZonedDateTime to) {
        this.to = to;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PaymentMethod getPayment() {
        return payment;
    }

    public void setPayment(PaymentMethod payment) {
        this.payment = payment;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TimeslotDTO)) {
            return false;
        }

        return id != null && id.equals(((TimeslotDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TimeslotDTO{" +
            "id=" + getId() +
            ", from='" + getFrom() + "'" +
            ", to='" + getTo() + "'" +
            ", status='" + getStatus() + "'" +
            ", payment='" + getPayment() + "'" +
            ", scheduleId='" + getScheduleId() + "'" +
            ", scheduleDate='" + getScheduleDate() + "'" +
            "}";
    }
}
