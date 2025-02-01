package com.example.systemrezerwacji.features.owner;

import com.example.systemrezerwacji.BaseIntegrationTest;
import com.example.systemrezerwacji.domain.codeModule.dto.CodeDto;
import com.example.systemrezerwacji.domain.employeeModule.dto.EmployeeFacadeResponseDto;
import com.example.systemrezerwacji.domain.employeeModule.response.CreateEmployeeResponseDto;
import com.example.systemrezerwacji.domain.offerModule.response.OfferFacadeResponse;
import com.example.systemrezerwacji.domain.salonModule.dto.SalonFacadeResponseDto;
import com.example.systemrezerwacji.domain.userModule.response.UserFacadeResponse;
import com.example.systemrezerwacji.infrastructure.loginandregister.dto.JwtResponseDto;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TypicalScenarioWhenOwnerCreateSalonTest extends BaseIntegrationTest {

    @Test
    public void user_create_a_salon_and_check_everything() throws Exception {

//        step 1: user made POST /register with email=Owner@owner.pl", password=OwnerPassword123and system registered user with status CREATED(201)
        // given && when
        ResultActions registerAction = mockMvc.perform(post("/register")
                .content("""
                        {
                        "name": "Owner",
                        "email": "Owner@owner.pl",
                        "password": "OwnerPassword123"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        MvcResult registerResult = registerAction.andExpect(status().isCreated()).andReturn();
        String registerResultJson = registerResult.getResponse().getContentAsString();
        UserFacadeResponse userFacadeResponse = objectMapper.readValue(registerResultJson, UserFacadeResponse.class);
        assertAll(
                () -> assertThat(userFacadeResponse.userId()).isEqualTo(1),
                () -> assertThat(userFacadeResponse.name()).isEqualTo("Owner"),
                () -> assertThat(userFacadeResponse.message()).isEqualTo("success")
        );


//        step 2: user tried to get JWT token by requesting POST /token with email=someUser, password=somePassword and system returned OK(200) and jwttoken=AAAA.BBBB.CCC
        // given && when
        ResultActions successLoginRequest = mockMvc.perform(post("/token")
                .content("""
                        {
                        "email": "Owner@owner.pl",
                        "password": "OwnerPassword123"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        MvcResult mvcResultSuccess = successLoginRequest.andExpect(status().isOk()).andReturn();
        String successJson = mvcResultSuccess.getResponse().getContentAsString();
        JwtResponseDto jwtResponse = objectMapper.readValue(successJson, JwtResponseDto.class);
        String token = jwtResponse.token();
        assertAll(
                () -> assertThat(jwtResponse.email()).isEqualTo("Owner@owner.pl"),
                () -> assertThat(jwtResponse.token()).isNotNull()
        );


//        step 3: user made POST/generateCode and system return code with status CREATED(201)
        // given && when
        ResultActions generateCode = mockMvc.perform(post("/generateCode")
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        // then
        MvcResult mvcCodeCreated = generateCode.andExpect(status().isCreated()).andReturn();
        String codeJson = mvcCodeCreated.getResponse().getContentAsString();
        CodeDto codeDto = objectMapper.readValue(codeJson, CodeDto.class);

        assertThat(codeDto.code()).isNotNull();
        assertThat(codeDto.isConsumed()).isFalse();


//        step 4: user made POST /salon with valid salon details and system created the salon, returning OK(200) with salonId=1
        String salonRequestJson = String.format("""
        {
            "salonName": "Owner Test Salon",
            "category": "Hair",
            "city": "Bialystok",
            "zipCode": "15-370",
            "street": "Józefa Bema",
            "number": "91",
            "userId": 1,
            "code": "%s"
        }
        """.trim(), codeDto.code());
        // when
        ResultActions performCreateSalon = addSalon(token, salonRequestJson);
        // then
        String createdSalonJson = performCreateSalon.andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        SalonFacadeResponseDto salonCreatedResponseDto = objectMapper.readValue(createdSalonJson, SalonFacadeResponseDto.class);
        assertAll(
                () -> assertThat(salonCreatedResponseDto.message()).isEqualTo("success"),
                () -> assertThat(salonCreatedResponseDto.salonId()).isEqualTo(1)
        );


//        step 5: owner made Patch /salon/add-opening-hours with valid hours and system returned OK(200)
        // given
        String salonOpeningHours = readJsonFromFile("salonOpeningHours.json");
        // when
        ResultActions performAddOpeningHours = addOpeningHours(token, salonOpeningHours);
        // then
        String addOpeningHoursJson = performAddOpeningHours.andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        SalonFacadeResponseDto salonAddHoursResponseDto = objectMapper.readValue(addOpeningHoursJson, SalonFacadeResponseDto.class);
        assertThat(salonAddHoursResponseDto.message()).isEqualTo("success");


//        step 6: owner made POST /salon/{id}/employee with first employee details and system added the employee, returning OK(200)
        // given
        String firstEmployeeDateAndAvailability = readJsonFromFile("employeeData.json");
        // when
        ResultActions performAddEmployee = addEmployee(token, firstEmployeeDateAndAvailability);
        // then
        String addFirstEmployeeJson = performAddEmployee.andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        CreateEmployeeResponseDto createFirstEmployeeResponse = objectMapper.readValue(addFirstEmployeeJson, CreateEmployeeResponseDto.class);

        assertAll(
                () -> assertThat(createFirstEmployeeResponse.employeePassword()).isNotNull(),
                () -> assertThat(createFirstEmployeeResponse.employeeEmail()).isEqualTo("Kot@example.com"),
                () -> assertThat(createFirstEmployeeResponse.message()).isEqualTo("success")
        );


//        step 7: owner made POST /salon/{id}/employee with second employee details and system added the employee, returning OK(200)
        // given
        String secondEmployeeDateAndAvailability = readJsonFromFile("secondEmployeeData.json");
        // when
        ResultActions performAddSecondEmployee = addEmployee(token, secondEmployeeDateAndAvailability);
        // then
        String addSecondEmployeeJson = performAddSecondEmployee.andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        CreateEmployeeResponseDto createSecondEmployeeResponse = objectMapper.readValue(addSecondEmployeeJson, CreateEmployeeResponseDto.class);

        assertAll(
                () -> assertThat(createSecondEmployeeResponse.employeePassword()).isNotNull(),
                () -> assertThat(createSecondEmployeeResponse.employeeEmail()).isEqualTo("Marcin@employee.com"),
                () -> assertThat(createSecondEmployeeResponse.message()).isEqualTo("success")
        );


//        step 8: owner made POST /offer with offer details and system created the offer, returning Created(201) with offerId=1
        // given
        String firstOffer = """
                {
                    "name": "Haircut",
                    "description": "A stylish haircut and grooming session.",
                    "price": 49.99,
                    "duration": "00:30:00",
                    "salonId": 1
                }
                """.trim();
        // when
        ResultActions performAddOffer = addOffer(token, firstOffer);
        // then
        String firstOfferJson = performAddOffer.andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        OfferFacadeResponse firstOfferFacadeResponse = objectMapper.readValue(firstOfferJson, OfferFacadeResponse.class);
        assertAll(
                () -> assertThat(firstOfferFacadeResponse.OfferId()).isEqualTo(1),
                () -> assertThat(firstOfferFacadeResponse.message()).isEqualTo("success")
        );


//        step 9: owner made POST /offer with offer details and system created the offer, returning Created(201) with offerId=2
        // given
        String secondOffer = """
                {
                    "name": "Hair cutting and washing",
                    "description": "A stylish haircut and washing it.",
                    "price": 80.00,
                    "duration": "00:45:00",
                    "salonId": 1
                }
                """.trim();
        // when
        ResultActions performAddOffer2 = addOffer(token, secondOffer);
        // then
        String secondOfferJson = performAddOffer2.andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        OfferFacadeResponse secondOfferFacadeResponse = objectMapper.readValue(secondOfferJson, OfferFacadeResponse.class);
        assertAll(
                () -> assertThat(secondOfferFacadeResponse.OfferId()).isEqualTo(2),
                () -> assertThat(secondOfferFacadeResponse.message()).isEqualTo("success")
        );

//        step 9: owner assign offer 1 to employee with id 1 make PATCH to /employees/add-offer, returning OK(200)
        // given
        String assignFirstOfferToFirstEmployee = """
                {
                  "offerId": 1,
                  "employeeId": 1
                }
                """.trim();
        // when
        ResultActions performAssignOffer = addOfferToEmployee(token, assignFirstOfferToFirstEmployee);
        // then
        String assignOfferJson1 = performAssignOffer.andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        EmployeeFacadeResponseDto employeeFacadeResponse1 = objectMapper.readValue(assignOfferJson1, EmployeeFacadeResponseDto.class);
        assertAll(
                () -> assertThat(employeeFacadeResponse1.employeeId()).isEqualTo(1),
                () -> assertThat(employeeFacadeResponse1.message()).isEqualTo("success")
        );


//        step 10: owner assign offer 2 to employee with id 1 make PATCH to /employees/add-offer, returning OK(200)
        // given
        String assignSecondOfferToFirstEmployee = """
                {
                  "offerId": 2,
                  "employeeId": 1
                }
                """.trim();
        // when
        ResultActions performAssignOffer2 = addOfferToEmployee(token, assignSecondOfferToFirstEmployee);
        // then
        String assignOfferJson2 = performAssignOffer2.andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        EmployeeFacadeResponseDto employeeFacadeResponse2 = objectMapper.readValue(assignOfferJson2, EmployeeFacadeResponseDto.class);
        assertAll(
                () -> assertThat(employeeFacadeResponse2.employeeId()).isEqualTo(1),
                () -> assertThat(employeeFacadeResponse2.message()).isEqualTo("success")
        );


//        step 11: owner assign offer 1 to employee with id 2 make PATCH to /employees/add-offer, returning OK(200)
        // given
        String assignFirstOfferToSecondEmployee = """
                {
                  "offerId": 1,
                  "employeeId": 2
                }
                """.trim();
        // when
        ResultActions performAssignOffer3 = addOfferToEmployee(token, assignFirstOfferToSecondEmployee);
        // then
        String assignOfferJson3 = performAssignOffer3.andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        EmployeeFacadeResponseDto employeeFacadeResponse3 = objectMapper.readValue(assignOfferJson3, EmployeeFacadeResponseDto.class);
        assertAll(
                () -> assertThat(employeeFacadeResponse3.employeeId()).isEqualTo(2),
                () -> assertThat(employeeFacadeResponse3.message()).isEqualTo("success")
        );

//        step 12: owner made GET /owner/salon/ with email in body to view all they salons and system returned OK(200) with salons information
//        step 13: owner made GET /owner/salon/{id} with email in body to view details this salon system returned OK(200) with salon details
//        (in the middle owner see information about all reservation to this day, and he can go to next day to check reservation)
//        step 14: owner made GET /owner/reservation-details/{reservation-id} and now he can see information about this
//        step 15: owner made GET /owner/salon-details/{salonId} and now he can see all information about salon and can manage it
//        step 16: owner made PATCH /owner/salon/{id} to update salon details (e.g., name, category) and system returned OK(200)

    }


    @NotNull
    private ResultActions addSalon(String token, String salonRequestJson) throws Exception {
        return mockMvc.perform(post("/salon")
                .header("Authorization", "Bearer " + token)
                .content(salonRequestJson)
                .contentType(MediaType.APPLICATION_JSON_VALUE));
    }
    @NotNull
    private ResultActions addOfferToEmployee(String token, String assignOffer) throws Exception {
        return mockMvc.perform(patch("/employees/add-offer")
                .header("Authorization", "Bearer " + token)
                .content(assignOffer)
                .contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @NotNull
    private ResultActions addEmployee(String token, String employeeDateAndAvailability) throws Exception {
        return mockMvc.perform(post("/salon/1/employee")
                .header("Authorization", "Bearer " + token)
                .content(employeeDateAndAvailability)
                .contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @NotNull
    private ResultActions addOpeningHours(String token, String salonOpeningHours) throws Exception {
        return mockMvc.perform(patch("/salon/add-opening-hours")
                .header("Authorization", "Bearer " + token)
                .content(salonOpeningHours)
                .contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @NotNull
    private ResultActions addOffer(String token, String createOffer) throws Exception {
        return mockMvc.perform(post("/offer")
                .header("Authorization", "Bearer " + token)
                .content(createOffer)
                .contentType(MediaType.APPLICATION_JSON_VALUE));
    }
}
