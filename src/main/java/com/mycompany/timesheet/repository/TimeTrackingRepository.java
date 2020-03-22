package com.mycompany.timesheet.repository;

import com.mycompany.timesheet.domain.TimeTracking;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TimeTracking entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TimeTrackingRepository extends JpaRepository<TimeTracking, Long> {
}
