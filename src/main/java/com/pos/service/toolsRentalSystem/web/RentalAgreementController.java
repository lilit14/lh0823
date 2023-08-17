package com.pos.service.toolsRentalSystem.web;

import com.pos.service.toolsRentalSystem.data.model.RentalAgreement;
import com.pos.service.toolsRentalSystem.payloads.request.RentalAgreementRequest;
import com.pos.service.toolsRentalSystem.payloads.response.MessageResponse;
import com.pos.service.toolsRentalSystem.service.RentalAgreementService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rentalAgreement")
public class RentalAgreementController {
    @Autowired private RentalAgreementService rentalAgreementService;

    @GetMapping("/all")
    public ResponseEntity<List<RentalAgreement>> getAll() {
        List<RentalAgreement> rentalAgreements = rentalAgreementService.getAll();
        return new ResponseEntity<>(rentalAgreements, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<RentalAgreement> get(@PathVariable("id") UUID id) {
        RentalAgreement rentalAgreement = rentalAgreementService.get(id);
        return new ResponseEntity<>(rentalAgreement, HttpStatus.OK);
    }

    @PostMapping("/checkout")
    public ResponseEntity<RentalAgreement> create(@RequestBody RentalAgreementRequest request) {
        RentalAgreement rentalAgreement = rentalAgreementService.create(request);
        return new ResponseEntity<>(rentalAgreement, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID id) {
        rentalAgreementService.delete(id);
        return new ResponseEntity<>(
                new MessageResponse("Successfully deleted by id: " + id), HttpStatus.OK);
    }
}
