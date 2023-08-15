package com.pos.service.toolsRentalSystem.service;

import com.pos.service.toolsRentalSystem.data.model.*;
import com.pos.service.toolsRentalSystem.data.repository.HolidayRepository;
import com.pos.service.toolsRentalSystem.data.repository.RentalAgreementRepository;
import com.pos.service.toolsRentalSystem.data.repository.ToolRepository;
import com.pos.service.toolsRentalSystem.exceptions.RentalAgreementException;
import com.pos.service.toolsRentalSystem.exceptions.ResourceNotFoundException;
import com.pos.service.toolsRentalSystem.payloads.request.RentalAgreementRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentalAgreementServiceImpl implements RentalAgreementService {

    private final int DECIMAL_COUNT = 2;
    private final RoundingMode ROUND_MODE = RoundingMode.HALF_UP;
    @Autowired
    RentalAgreementRepository rentalAgreementRepository;
    @Autowired
    ToolRepository toolRepository;
    @Autowired
    HolidayRepository holidayRepository;


    @Override
    public RentalAgreement create(RentalAgreementRequest request) throws RentalAgreementException {
        if (request.getRentalDayCount() < 1) {
            throw new RentalAgreementException("Invalid rental day count.", "Rental day count must be 1 or greater.");
        }
        if (request.getDiscountPercent() < 0 || request.getDiscountPercent() > 100) {
            throw new RentalAgreementException("Invalid discount percent.", "Discount percent must be in the range 0-100");
        }
        RentalAgreement rentalAgreement = new RentalAgreement();

        Tool tool = toolRepository.findByCodeEquals(request.getToolCode());
        if (tool == null){
            throw new RentalAgreementException("Unknown tool type", "Could not find a tool type by the given type.");
        }

        rentalAgreement.setTool(tool);
        Integer rentalDayCount = request.getRentalDayCount();
        rentalAgreement.setRentalDayCount(rentalDayCount);
        Integer discountPercent = request.getDiscountPercent();
        rentalAgreement.setDiscountPercent(discountPercent);
        LocalDate checkoutDate = request.getCheckoutDate();
        rentalAgreement.setCheckoutDate(checkoutDate);
        LocalDate dueDate = calculateDueDate(checkoutDate, rentalDayCount);
        rentalAgreement.setDueDate(dueDate);
        int chargeDays = calculateChargeDays(tool.getType(), checkoutDate, dueDate, rentalDayCount);
        rentalAgreement.setChargeDays(chargeDays);
        BigDecimal preDiscountCharge = BigDecimal.valueOf(chargeDays).multiply(tool.getType().getDailyCharge()).
                setScale(DECIMAL_COUNT, ROUND_MODE);
        rentalAgreement.setPreDiscountCharge(preDiscountCharge);
        BigDecimal discountAmount = preDiscountCharge.multiply(BigDecimal.valueOf(discountPercent).
                divide(BigDecimal.valueOf(100), DECIMAL_COUNT, ROUND_MODE)).
                setScale(DECIMAL_COUNT, ROUND_MODE);
        rentalAgreement.setDiscountAmount(discountAmount);
        rentalAgreement.setFinalCharge(preDiscountCharge.subtract(discountAmount));
        return rentalAgreementRepository.save(rentalAgreement);
    }


    private int calculateChargeDays(ToolType toolType, LocalDate checkoutDate, LocalDate dueDate, Integer rentalDayCount) {
        int chargeableRentalDays = 0;
        if (toolType.weekdayCharge) {
            chargeableRentalDays += getWeakDaysCount(checkoutDate, rentalDayCount);
        }
        if (toolType.weekendCharge) {
            chargeableRentalDays += getWeakEndsCount(checkoutDate, rentalDayCount);
        }
        if (!toolType.holidayCharge) {
            chargeableRentalDays -= getHolidaysCount(checkoutDate, dueDate);
        }
        return chargeableRentalDays;
    }

    private int getWeakEndsCount(LocalDate checkoutDate, int rentalDayCount) {
        int weekEndsCount = 0;

        int weekDay = checkoutDate.getDayOfWeek().getValue();
        rentalDayCount-=1;
        weekEndsCount += (weekDay+rentalDayCount)/7;
        weekEndsCount+=(weekDay+rentalDayCount+1)/7;
        if (weekDay == DayOfWeek.SATURDAY.getValue()){
            weekEndsCount-=1;
        }
        return weekEndsCount;
    }

    private int getWeakDaysCount(LocalDate checkoutDate, int rentalDayCount) {
        return rentalDayCount - getWeakEndsCount(checkoutDate, rentalDayCount);
    }

    private int getHolidaysCount(LocalDate checkoutDate, LocalDate dueDate) {
        int holidaysCount = 0;
        int checkoutYear = checkoutDate.getYear();
        int dueDateYear = dueDate.getYear();
        for(;checkoutYear<=dueDateYear;checkoutYear+=1) { // corner case if checkout days are more than a year
            for (Holiday holiday : holidayRepository.findAll()) {
                LocalDate date = holiday.getDate(checkoutYear);
                if (date.compareTo(checkoutDate) >= 0 && date.compareTo(dueDate) <= 0) {
                    holidaysCount += 1;
                }
            }
        }
        return holidaysCount;
    }

    private LocalDate calculateDueDate(LocalDate checkoutDate, Integer rentalDayCount) {
        return checkoutDate.plusDays(rentalDayCount - 1);
    }

    @Override
    public void delete(UUID id) {
        if (rentalAgreementRepository.getReferenceById(id).getId().equals(id)) {
            rentalAgreementRepository.deleteById(id);
        } else throw new ResourceNotFoundException("RentalAgreement", "id", id);
    }

    @Override
    public RentalAgreement get(UUID id) {
        return rentalAgreementRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("RentalAgreement", "id", id));
    }

    @Override
    public List<RentalAgreement> getAll() {
        return rentalAgreementRepository.findAll();
    }
}

