package com.app.vaccnow.service.dto;

import java.io.Serializable;
import com.app.vaccnow.domain.enumeration.PaymentMethod;

/**
 * A DTO for the {@link com.app.vaccnow.domain.Payment} entity.
 */
public class PaymentDTO implements Serializable {
    
    private String id;

    private PaymentMethod method;

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public void setMethod(PaymentMethod method) {
        this.method = method;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentDTO)) {
            return false;
        }

        return id != null && id.equals(((PaymentDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentDTO{" +
            "id=" + getId() +
            ", method='" + getMethod() + "'" +
            "}";
    }
}
