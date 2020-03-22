package com.mycompany.timesheet.repository;

import com.mycompany.timesheet.domain.Door;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Door entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DoorRepository extends JpaRepository<Door, Long> {
}
