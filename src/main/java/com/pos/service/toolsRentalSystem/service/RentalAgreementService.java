package com.pos.service.toolsRentalSystem.service;

import com.pos.service.toolsRentalSystem.data.model.RentalAgreement;
import com.pos.service.toolsRentalSystem.exceptions.RentalAgreementException;
import com.pos.service.toolsRentalSystem.payloads.request.RentalAgreementRequest;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public interface RentalAgreementService {
    RentalAgreement create(RentalAgreementRequest request) throws RentalAgreementException;

    void delete(UUID id);

    RentalAgreement get(UUID id);

    List<RentalAgreement> getAll();
}
