package com.mycompany.timesheet.repository;

import com.mycompany.timesheet.domain.AbsenceType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AbsenceType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AbsenceTypeRepository extends JpaRepository<AbsenceType, Long> {
}
