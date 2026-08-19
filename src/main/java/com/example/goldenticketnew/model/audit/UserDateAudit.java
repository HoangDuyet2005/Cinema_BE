package com.example.goldenticketnew.model.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.MappedSuperclass;


@MappedSuperclass
@JsonIgnoreProperties(
        value = {"createdBy", "modifiedBy"},
        allowGetters = true
)
public abstract class UserDateAudit extends DateAudit {

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String modifiedBy;

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }


}
