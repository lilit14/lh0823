package com.pos.service.toolsRentalSystem.data.repository;

import com.pos.service.toolsRentalSystem.data.model.Holiday;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, UUID> {}
