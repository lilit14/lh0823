package com.pos.service.toolsRentalSystem;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pos.service.toolsRentalSystem.data.model.FixedDateHoliday;
import com.pos.service.toolsRentalSystem.data.model.FloatingDateHoliday;
import com.pos.service.toolsRentalSystem.data.model.ToolType;
import com.pos.service.toolsRentalSystem.data.repository.HolidayRepository;
import com.pos.service.toolsRentalSystem.data.repository.RentalAgreementRepository;
import com.pos.service.toolsRentalSystem.data.repository.ToolRepository;
import com.pos.service.toolsRentalSystem.data.repository.ToolTypeRepository;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.Month;
import org.json.JSONObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ToolsRentalSystemApplicationTests {
    @Autowired private MockMvc mockMvc;

    @Autowired private ObjectMapper objectMapper;
    @Autowired private RentalAgreementRepository rentalAgreementRepository;

    @Autowired private HolidayRepository holidayRepository;

    @Autowired private ToolRepository toolRepository;

    @Autowired private ToolTypeRepository toolTypeRepository;

    @Test
    @Order(0)
    void cleanup() {
        rentalAgreementRepository.deleteAll();
        holidayRepository.deleteAll();
        toolRepository.deleteAll();
        toolTypeRepository.deleteAll();
    }

    @Test
    @Order(1)
    void createFloatingHolidayTest() throws Exception {
        FloatingDateHoliday holiday =
                FloatingDateHoliday.builder()
                        .name("Labor Day")
                        .dayOfWeek(DayOfWeek.MONDAY)
                        .weekNumber(1)
                        .month(Month.SEPTEMBER)
                        .build();

        ResultActions response =
                mockMvc.perform(
                        post("/holiday/create/floatingDateHoliday")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(holiday)));
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(holiday.getName())))
                .andExpect(jsonPath("$.dayOfWeek", is(holiday.getDayOfWeek().name())))
                .andExpect(jsonPath("$.weekNumber", is(holiday.getWeekNumber())))
                .andExpect(jsonPath("$.month", is(holiday.getMonth().name())));
    }

    @Test
    @Order(2)
    void createFixedHolidayTest() throws Exception {
        FixedDateHoliday holiday =
                FixedDateHoliday.builder()
                        .name("Independence Day")
                        .day(4)
                        .month(Month.JULY)
                        .isObservedHoliday(true)
                        .build();

        ResultActions response =
                mockMvc.perform(
                        post("/holiday/create/fixedDateHoliday")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(holiday)));
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(holiday.getName())))
                .andExpect(jsonPath("$.day", is(holiday.getDay())))
                .andExpect(jsonPath("$.month", is(holiday.getMonth().name())))
                .andExpect(jsonPath("$.isObservedHoliday", is(holiday.getIsObservedHoliday())));
    }

    @Test
    @Order(3)
    void createToolTypeTest1() throws Exception {
        ToolType toolType =
                ToolType.builder()
                        .name("Ladder")
                        .dailyCharge(BigDecimal.valueOf(1.99))
                        .weekdayCharge(true)
                        .weekendCharge(true)
                        .holidayCharge(false)
                        .build();
        testToolTypeCreation(toolType);
    }

    @Test
    @Order(4)
    void createToolTypeTest2() throws Exception {
        ToolType toolType =
                ToolType.builder()
                        .name("Chainsaw")
                        .dailyCharge(BigDecimal.valueOf(1.49))
                        .weekdayCharge(true)
                        .weekendCharge(false)
                        .holidayCharge(true)
                        .build();
        testToolTypeCreation(toolType);
    }

    @Test
    @Order(5)
    void createToolTypeTest3() throws Exception {
        ToolType toolType =
                ToolType.builder()
                        .name("Jackhammer")
                        .dailyCharge(BigDecimal.valueOf(2.99))
                        .weekdayCharge(true)
                        .weekendCharge(false)
                        .holidayCharge(false)
                        .build();
        testToolTypeCreation(toolType);
    }

    private void testToolTypeCreation(ToolType toolType) throws Exception {
        ResultActions response =
                mockMvc.perform(
                        post("/toolType/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(toolType)));
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(toolType.getName())))
                .andExpect(jsonPath("$.dailyCharge", is(toolType.getDailyCharge().doubleValue())))
                .andExpect(jsonPath("$.weekdayCharge", is(toolType.getWeekdayCharge())))
                .andExpect(jsonPath("$.weekendCharge", is(toolType.getWeekendCharge())))
                .andExpect(jsonPath("$.holidayCharge", is(toolType.getHolidayCharge())));
    }

    @Test
    @Order(6)
    void createToolTest1() throws Exception {
        JSONObject tool = new JSONObject();
        tool.put("code", "CHNS");
        tool.put("type", "Chainsaw");
        tool.put("brand", "Stihl");
        tool.put("availableAmount", 10);
        testToolCreation(tool);
    }

    private void testToolCreation(JSONObject tool) throws Exception {
        ResultActions response =
                mockMvc.perform(
                        post("/tool/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(tool.toString()));
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code", is(tool.get("code"))))
                .andExpect(jsonPath("$.brand", is(tool.get("brand"))))
                .andExpect(jsonPath("$.type.name", is(tool.get("type"))));
    }

    @Test
    @Order(7)
    void createToolTest2() throws Exception {
        JSONObject tool = new JSONObject();
        tool.put("code", "LADW");
        tool.put("type", "Ladder");
        tool.put("brand", "Werner");
        tool.put("availableAmount", 7);
        testToolCreation(tool);
    }

    @Test
    @Order(8)
    void createToolTest3() throws Exception {
        JSONObject tool = new JSONObject();
        tool.put("code", "JAKD");
        tool.put("type", "Jackhammer");
        tool.put("brand", "DeWalt");
        tool.put("availableAmount", 1);
        testToolCreation(tool);
    }

    @Test
    @Order(9)
    void createToolTest4() throws Exception {
        JSONObject tool = new JSONObject();
        tool.put("code", "JAKR");
        tool.put("type", "Jackhammer");
        tool.put("brand", "Ridgid");
        tool.put("availableAmount", 2);
        testToolCreation(tool);
    }

    @Test
    void createRentalAgreementTest1() throws Exception {
        JSONObject rentalAgreement = new JSONObject();
        rentalAgreement.put("toolCode", "JAKR");
        rentalAgreement.put("discountPercent", 101);
        rentalAgreement.put("rentalDayCount", 5);
        rentalAgreement.put("checkoutDate", "09/03/15");
        ResultActions response =
                mockMvc.perform(
                        post("/rentalAgreement/checkout")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(rentalAgreement.toString()));
        response.andDo(print()).andExpect(status().is4xxClientError());
    }

    private void testRentalAgreementCreation(
            JSONObject rentalAgreement,
            String expectedDueDate,
            int expectedChargeDays,
            double expectedPreDiscountCharge,
            double expectedDiscountAmount,
            double expectedFinalCharge,
            Object expectedPreDiscountChargeFormatted,
            Object expectedDiscountPercentFormatted,
            Object expectedDiscountAmountFormatted,
            Object expectedFinalChargeFormatted)
            throws Exception {
        ResultActions response =
                mockMvc.perform(
                        post("/rentalAgreement/checkout")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(rentalAgreement.toString()));
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tool.code", is(rentalAgreement.get("toolCode"))))
                .andExpect(
                        jsonPath("$.discountPercent", is(rentalAgreement.get("discountPercent"))))
                .andExpect(jsonPath("$.rentalDayCount", is(rentalAgreement.get("rentalDayCount"))))
                .andExpect(jsonPath("$.checkoutDate", is(rentalAgreement.get("checkoutDate"))))
                .andExpect(jsonPath("$.dueDate", is(expectedDueDate)))
                .andExpect(jsonPath("$.chargeDays", is(expectedChargeDays)))
                .andExpect(jsonPath("$.preDiscountCharge", is(expectedPreDiscountCharge)))
                .andExpect(jsonPath("$.discountAmount", is(expectedDiscountAmount)))
                .andExpect(jsonPath("$.finalCharge", is(expectedFinalCharge)))
                .andExpect(
                        jsonPath(
                                "$.preDiscountChargeFormatted",
                                is(expectedPreDiscountChargeFormatted)))
                .andExpect(
                        jsonPath(
                                "$.discountPercentFormatted", is(expectedDiscountPercentFormatted)))
                .andExpect(
                        jsonPath("$.discountAmountFormatted", is(expectedDiscountAmountFormatted)))
                .andExpect(jsonPath("$.finalChargeFormatted", is(expectedFinalChargeFormatted)));
    }

    @Test
    void createRentalAgreementTest2() throws Exception {
        JSONObject rentalAgreement = new JSONObject();
        rentalAgreement.put("toolCode", "LADW");
        rentalAgreement.put("discountPercent", 10);
        rentalAgreement.put("rentalDayCount", 3);
        rentalAgreement.put("checkoutDate", "7/2/20");
        testRentalAgreementCreation(
                rentalAgreement, "7/4/20", 2, 3.98, 0.4, 3.58, "$3.98", "10%", "$0.4", "$3.58");
    }

    @Test
    void createRentalAgreementTest3() throws Exception {
        JSONObject rentalAgreement = new JSONObject();
        rentalAgreement.put("toolCode", "CHNS");
        rentalAgreement.put("discountPercent", 25);
        rentalAgreement.put("rentalDayCount", 5);
        rentalAgreement.put("checkoutDate", "7/2/15");
        testRentalAgreementCreation(
                rentalAgreement, "7/6/15", 3, 4.47, 1.12, 3.35, "$4.47", "25%", "$1.12", "$3.35");
    }

    @Test
    void createRentalAgreementTest4() throws Exception {
        JSONObject rentalAgreement = new JSONObject();
        rentalAgreement.put("toolCode", "JAKD");
        rentalAgreement.put("discountPercent", 0);
        rentalAgreement.put("rentalDayCount", 6);
        rentalAgreement.put("checkoutDate", "9/3/15");
        testRentalAgreementCreation(
                rentalAgreement, "9/8/15", 3, 8.97, 0, 8.97, "$8.97", "0%", "$0", "$8.97");
    }

    @Test
    void createRentalAgreementTest5() throws Exception {
        JSONObject rentalAgreement = new JSONObject();
        rentalAgreement.put("toolCode", "JAKR");
        rentalAgreement.put("discountPercent", 0);
        rentalAgreement.put("rentalDayCount", 9);
        rentalAgreement.put("checkoutDate", "7/2/15");
        testRentalAgreementCreation(
                rentalAgreement, "7/10/15", 6, 17.94, 0, 17.94, "$17.94", "0%", "$0", "$17.94");
    }

    @Test
    void createRentalAgreementTest6() throws Exception {
        JSONObject rentalAgreement = new JSONObject();
        rentalAgreement.put("toolCode", "JAKR");
        rentalAgreement.put("discountPercent", 50);
        rentalAgreement.put("rentalDayCount", 4);
        rentalAgreement.put("checkoutDate", "7/2/20");
        testRentalAgreementCreation(
                rentalAgreement, "7/5/20", 1, 2.99, 1.5, 1.49, "$2.99", "50%", "$1.5", "$1.49");
    }
}
