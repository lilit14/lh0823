package com.pos.service.toolsRentalSystem.data.repository;

import com.pos.service.toolsRentalSystem.data.model.RentalAgreement;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalAgreementRepository extends JpaRepository<RentalAgreement, UUID> {
    List<RentalAgreement> findByIsReturnedAndDueDateLessThan(Boolean isReturned, LocalDate date);
}
