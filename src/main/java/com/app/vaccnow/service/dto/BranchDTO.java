package com.app.vaccnow.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.app.vaccnow.domain.Branch} entity.
 */
public class BranchDTO implements Serializable {
    
    private String id;

    @NotNull
    private String name;

    private String address;

    private Boolean vaccineAllowed;

    private String vaccineStock;

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean isVaccineAllowed() {
        return vaccineAllowed;
    }

    public void setVaccineAllowed(Boolean vaccineAllowed) {
        this.vaccineAllowed = vaccineAllowed;
    }

    public String getVaccineStock() {
        return vaccineStock;
    }

    public void setVaccineStock(String vaccineStock) {
        this.vaccineStock = vaccineStock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BranchDTO)) {
            return false;
        }

        return id != null && id.equals(((BranchDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BranchDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", vaccineAllowed='" + isVaccineAllowed() + "'" +
            ", vaccineStock='" + getVaccineStock() + "'" +
            "}";
    }
}
