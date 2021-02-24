package com.app.vaccnow.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

import com.app.vaccnow.domain.enumeration.PaymentMethod;

/**
 * A Timeslot.
 */
@Document(collection = "timeslot")
public class Timeslot extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("from")
    private ZonedDateTime from;

    @NotNull
    @Field("to")
    private ZonedDateTime to;

    @Field("status")
    private String status;

    @Field("payment")
    private PaymentMethod payment;

    @DBRef
    @Field("schedule")
    @JsonIgnoreProperties(value = "timeslots", allowSetters = true)
    private Schedule schedule;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ZonedDateTime getFrom() {
        return from;
    }

    public Timeslot from(ZonedDateTime from) {
        this.from = from;
        return this;
    }

    public void setFrom(ZonedDateTime from) {
        this.from = from;
    }

    public ZonedDateTime getTo() {
        return to;
    }

    public Timeslot to(ZonedDateTime to) {
        this.to = to;
        return this;
    }

    public void setTo(ZonedDateTime to) {
        this.to = to;
    }

    public String getStatus() {
        return status;
    }

    public Timeslot status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PaymentMethod getPayment() {
        return payment;
    }

    public Timeslot payment(PaymentMethod payment) {
        this.payment = payment;
        return this;
    }

    public void setPayment(PaymentMethod payment) {
        this.payment = payment;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public Timeslot schedule(Schedule schedule) {
        this.schedule = schedule;
        return this;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Timeslot)) {
            return false;
        }
        return id != null && id.equals(((Timeslot) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Timeslot{" +
            "id=" + getId() +
            ", from='" + getFrom() + "'" +
            ", to='" + getTo() + "'" +
            ", status='" + getStatus() + "'" +
            ", payment='" + getPayment() + "'" +
            "}";
    }
}
