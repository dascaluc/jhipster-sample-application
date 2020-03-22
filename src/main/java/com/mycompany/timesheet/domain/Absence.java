package com.mycompany.timesheet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;

/**
 * A Absence.
 */
@Entity
@Table(name = "absence")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Absence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_from", nullable = false)
    private Instant from;

    @NotNull
    @Column(name = "jhi_to", nullable = false)
    private Instant to;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "motivation")
    private String motivation;

    @ManyToOne
    @JsonIgnoreProperties("absences")
    private Employee employee;

    @ManyToOne
    @JsonIgnoreProperties("absences")
    private AbsenceType type;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFrom() {
        return from;
    }

    public Absence from(Instant from) {
        this.from = from;
        return this;
    }

    public void setFrom(Instant from) {
        this.from = from;
    }

    public Instant getTo() {
        return to;
    }

    public Absence to(Instant to) {
        this.to = to;
        return this;
    }

    public void setTo(Instant to) {
        this.to = to;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Absence phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMotivation() {
        return motivation;
    }

    public Absence motivation(String motivation) {
        this.motivation = motivation;
        return this;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Absence employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public AbsenceType getType() {
        return type;
    }

    public Absence type(AbsenceType absenceType) {
        this.type = absenceType;
        return this;
    }

    public void setType(AbsenceType absenceType) {
        this.type = absenceType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Absence)) {
            return false;
        }
        return id != null && id.equals(((Absence) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Absence{" +
            "id=" + getId() +
            ", from='" + getFrom() + "'" +
            ", to='" + getTo() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", motivation='" + getMotivation() + "'" +
            "}";
    }
}
