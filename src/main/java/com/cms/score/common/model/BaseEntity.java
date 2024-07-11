package com.cms.score.common.model;

import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@MappedSuperclass
@Data
public abstract class BaseEntity {

    @Column(name = "is_deleted")
    private int isDeleted = 0;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_date")
    private Timestamp updatedDate;

    @Column(name = "updated_by")
    private String updatedBy;

    public Timestamp getCreatedDate() {
        if (createdDate != null) {
            LocalDateTime currentDateTime = createdDate.toLocalDateTime();
            ZoneId utcZone = ZoneId.of("UTC");
            ZoneId wibZone = ZoneId.of("Asia/Jakarta");
            ZonedDateTime utcDateTime = currentDateTime.atZone(utcZone);
            ZonedDateTime wibDateTime = utcDateTime.withZoneSameInstant(wibZone);
            LocalDateTime wibLocalDateTime = wibDateTime.toLocalDateTime();
            return Timestamp.valueOf(wibLocalDateTime);
        }
        return null;
    }

    public Timestamp getUpdatedDate() {
        if (updatedDate != null) {
            LocalDateTime currentDateTime = updatedDate.toLocalDateTime();
            ZoneId utcZone = ZoneId.of("UTC");
            ZoneId wibZone = ZoneId.of("Asia/Jakarta");
            ZonedDateTime utcDateTime = currentDateTime.atZone(utcZone);
            ZonedDateTime wibDateTime = utcDateTime.withZoneSameInstant(wibZone);
            LocalDateTime wibLocalDateTime = wibDateTime.toLocalDateTime();
            return Timestamp.valueOf(wibLocalDateTime);
        }
        return null;
    }

    @PrePersist
    public void onPrePersist() {
        this.createdDate = new Timestamp(System.currentTimeMillis());
        this.updatedDate = new Timestamp(System.currentTimeMillis());

        String currentUserName = getCurrentUserName();
        this.createdBy = currentUserName;
        this.updatedBy = currentUserName;
    }

    @PreUpdate
    public void onPreUpdate() {
        this.updatedDate = new Timestamp(System.currentTimeMillis());
        this.updatedBy = getCurrentUserName();
    }

    // value in username is nip
    private String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }
        return null; // Handle cases where there is no authentication context
    }
}
