package com.mycompany.timesheet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;

import com.mycompany.timesheet.domain.enumeration.AccesType;

/**
 * A TimeTracking.
 */
@Entity
@Table(name = "time_tracking")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TimeTracking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "date", nullable = false)
    private Instant date;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "access_typee", nullable = false)
    private AccesType accessTypee;

    @Lob
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonIgnoreProperties("timeTrackings")
    private Door door;

    @ManyToOne
    @JsonIgnoreProperties("timeTrackings")
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public TimeTracking date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public AccesType getAccessTypee() {
        return accessTypee;
    }

    public TimeTracking accessTypee(AccesType accessTypee) {
        this.accessTypee = accessTypee;
        return this;
    }

    public void setAccessTypee(AccesType accessTypee) {
        this.accessTypee = accessTypee;
    }

    public String getDescription() {
        return description;
    }

    public TimeTracking description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Door getDoor() {
        return door;
    }

    public TimeTracking door(Door door) {
        this.door = door;
        return this;
    }

    public void setDoor(Door door) {
        this.door = door;
    }

    public Employee getEmployee() {
        return employee;
    }

    public TimeTracking employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TimeTracking)) {
            return false;
        }
        return id != null && id.equals(((TimeTracking) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TimeTracking{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", accessTypee='" + getAccessTypee() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
