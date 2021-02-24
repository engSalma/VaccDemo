package com.app.vaccnow.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

import com.app.vaccnow.domain.enumeration.PaymentMethod;

/**
 * A Payment.
 */
@Document(collection = "payment")
public class Payment extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("method")
    private PaymentMethod method;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public Payment method(PaymentMethod method) {
        this.method = method;
        return this;
    }

    public void setMethod(PaymentMethod method) {
        this.method = method;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Payment)) {
            return false;
        }
        return id != null && id.equals(((Payment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Payment{" +
            "id=" + getId() +
            ", method='" + getMethod() + "'" +
            "}";
    }
}
