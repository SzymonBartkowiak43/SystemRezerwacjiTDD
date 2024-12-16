package com.example.systemrezerwacji.features;

import com.example.systemrezerwacji.BaseIntegrationTest;

import com.example.systemrezerwacji.domain.code_module.CodeFacade;
import com.example.systemrezerwacji.domain.code_module.dto.CodeDto;
import com.example.systemrezerwacji.domain.employee_module.response.CreateEmployeeResponseDto;
import com.example.systemrezerwacji.domain.offer_module.response.OfferFacadeResponse;
import com.example.systemrezerwacji.domain.salon_module.dto.SalonFacadeResponseDto;
import com.example.systemrezerwacji.domain.user_module.dto.UserRegisterDto;
import com.example.systemrezerwacji.domain.user_module.response.UserFacadeResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.net.URL;
import java.util.regex.Pattern;

import com.example.systemrezerwacji.infrastructure.loginandregister.dto.*;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TypicalScenarioWhenUserCreatedSallonIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CodeFacade codeFacade;

    @Test
    void user_patch_to_create_a_salon() throws Exception {
//        step 1: user tried to get JWT token by requesting POST /token with email=someUser, password=somePassword and system returned UNAUTHORIZED(401)
        // given && when
        ResultActions failedTokenRequest = mockMvc.perform(post("/token")
                .content("""
                        {
                        "email": "someEmail@some.pl",
                        "password": "somePassword"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        failedTokenRequest
                .andExpect(status().isUnauthorized())
                .andExpect(content().json(
                        """
                                {
                                "message": "Bad Credentials",
                                "status": "UNAUTHORIZED"
                                }
                                """.trim()
                ));


//        step 2: user made GET /reservation/user/1 with no jwt token and system returned UNAUTHORIZED(401)
        // given && when
        ResultActions failedGetReservationToUser = mockMvc.perform(get("/reservation/user/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        failedGetReservationToUser.andExpect(status().isUnauthorized());


//        step 3: user made POST /register with email=someUser, password=somePassword and system registered user with status CREATED(201)
        // given && when
        ResultActions registerAction = mockMvc.perform(post("/register")
                .content("""
                        {
                        "name": "someName",
                        "email": "someEmail@some.pl",
                        "password": "somePassword"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        MvcResult registerResult = registerAction.andExpect(status().isCreated()).andReturn();
        String registerResultJson = registerResult.getResponse().getContentAsString();
        UserFacadeResponse userFacadeResponse = objectMapper.readValue(registerResultJson, UserFacadeResponse.class);
        assertAll(
                () -> assertThat(userFacadeResponse.userId()).isNotNull(),
                () -> assertThat(userFacadeResponse.message()).isEqualTo("success")
        );


//        step 4: user tried to get JWT token by requesting POST /token with email=someUser, password=somePassword and system returned OK(200) and jwttoken=AAAA.BBBB.CCC
        ResultActions successLoginRequest = mockMvc.perform(post("/token")
                .content("""
                        {
                        "email": "someEmail@some.pl",
                        "password": "somePassword"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        MvcResult mvcResultSuccess = successLoginRequest.andExpect(status().isOk()).andReturn();
        String SuccessJson = mvcResultSuccess.getResponse().getContentAsString();
        JwtResponseDto jwtResponse = objectMapper.readValue(SuccessJson, JwtResponseDto.class);
        String token = jwtResponse.token();
        assertAll(
                () -> assertThat(jwtResponse.email()).isEqualTo("someEmail@some.pl"),
                () -> assertThat(token).matches(Pattern.compile("^([A-Za-z0-9-_=]+\\.)+([A-Za-z0-9-_=])+\\.?$"))
        );


//        step 5: user made POST /salon with valid salon details and system created the salon, returning OK(200) with salonId=1
        // given
        CodeDto codeDto = codeFacade.generateNewCode();
        String salonRequestJson = String.format("""
        {
            "salonName": "Amazing Barber",
            "category": "Hair and Beauty",
            "city": "Bialystok",
            "zipCode": "00-001",
            "street": "Main Street",
            "number": "123",
            "userId": 1,
            "code": "%s"
        }
        """.trim(), codeDto.code());
        // when
        ResultActions performCreateSalon = mockMvc.perform(post("/salon")
                .header("Authorization", "Bearer " + token)
                .content(salonRequestJson)
                .contentType(MediaType.APPLICATION_JSON_VALUE));
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


//      step 6: user made Patch /salon/add-opening-hours with valid hours and system returned OK(200)
        // given
        String salonOpeningHours = getSalonOpeningHours();
        // when
        ResultActions performAddOpeningHours = mockMvc.perform(patch("/salon/add-opening-hours")
                .header("Authorization", "Bearer " + token)
                .content(salonOpeningHours)
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        // then
        String addOpeningHoursJson = performAddOpeningHours.andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        SalonFacadeResponseDto salonAddHoursResponseDto = objectMapper.readValue(addOpeningHoursJson, SalonFacadeResponseDto.class);
        assertThat(salonAddHoursResponseDto.message()).isEqualTo("success");


//      step 7: user made POST /salon/1/employee with employee details and system added the employee, returning OK(200)
        // given
        String employeeDateAndAvailability = getEmployeeDate();
        // when
        ResultActions performAddEmployee = mockMvc.perform(post("/salon/1/employee")
                .header("Authorization", "Bearer " + token)
                .content(employeeDateAndAvailability)
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        // then
        String addEmployeeJson = performAddEmployee.andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        CreateEmployeeResponseDto salonEmployeeResponse = objectMapper.readValue(addEmployeeJson, CreateEmployeeResponseDto.class);
        assertAll(
                () -> assertThat(salonEmployeeResponse.employeePassword()).isNotNull(),
                () -> assertThat(salonEmployeeResponse.employeeEmail()).isEqualTo("Kot@example.com"),
                () -> assertThat(salonEmployeeResponse.message()).isEqualTo("success")
        );

    //  step 8: user made POST /offers with offer details and system created the offer, returning Created(201) with offerId=1
        // given
        String createOffer = """
                {
                    "name": "Haircut",
                    "description": "A stylish haircut and grooming session.",
                    "price": 49.99,
                    "duration": "00:30:00",
                    "salonId": 1
                }
                """.trim();
        // when
        ResultActions performAddOffer = mockMvc.perform(post("/offer")
                .header("Authorization", "Bearer " + token)
                .content(createOffer)
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        // then
        String offerJson = performAddOffer.andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        OfferFacadeResponse offerFacadeResponse = objectMapper.readValue(offerJson, OfferFacadeResponse.class);
        assertAll(
                () -> assertThat(offerFacadeResponse.OfferId()).isEqualTo(1),
                () -> assertThat(offerFacadeResponse.message()).isEqualTo("success")
        );

        //  step 9: user made POST /offers with offer details and system created the offer, returning Created(201) with offerId=1
        // given
        String createOffer2 = """
                {
                    "name": "Haircut && Beard",
                    "description": "A stylish haircut and grooming session.",
                    "price": 99.99,
                    "duration": "01:00:00",
                    "salonId": 1
                }
                """.trim();
        // when
        ResultActions performAddOffer2 = mockMvc.perform(post("/offer")
                .header("Authorization", "Bearer " + token)
                .content(createOffer2)
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        // then
        String offerJson2 = performAddOffer2.andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        OfferFacadeResponse offerFacadeResponse2 = objectMapper.readValue(offerJson2, OfferFacadeResponse.class);
        assertAll(
                () -> assertThat(offerFacadeResponse2.OfferId()).isEqualTo(2),
                () -> assertThat(offerFacadeResponse2.message()).isEqualTo("success")
        );


//        step 9: user made POST /employee/1/assign-offer with offerId=1 and system assigned the offer to the employee, returning OK(200)
//        step 10: get all information about salon with id 1

//        String jsonSalons = mockMvc.perform(get("/salons")
//                        .contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(status().isOk()).andReturn()
//                .getResponse()
//                .getContentAsString();
//
//        List<SalonWithIdDto> salons = objectMapper.readValue(jsonSalons, new TypeReference<>() {
//        });
//
//        assertThat(salons).isEmpty();
//
    }

    private String getSalonOpeningHours() {
        return """
                [
                     {
                        "salonId": 1,
                        "dayOfWeek": "MONDAY",
                        "openingTime": "09:00",
                        "closingTime": "19:00"
                    },
                    {
                        "salonId": 1,
                        "dayOfWeek": "TUESDAY",
                        "openingTime": "09:00",
                        "closingTime": "19:00"
                    },
                    {
                        "salonId": 1,
                        "dayOfWeek": "WEDNESDAY",
                        "openingTime": "09:00",
                        "closingTime": "19:00"
                    },
                    {
                        "salonId": 1,
                        "dayOfWeek": "THURSDAY",
                        "openingTime": "09:00",
                        "closingTime": "19:00"
                    },
                    {
                        "salonId": 1,
                        "dayOfWeek": "FRIDAY",
                        "openingTime": "09:00",
                        "closingTime": "19:00"
                    },
                    {
                        "salonId": 1,
                        "dayOfWeek": "SATURDAY",
                        "openingTime": "10:00",
                        "closingTime": "14:00"
                    },
                    {
                        "salonId": 1,
                        "dayOfWeek": "SUNDAY",
                        "openingTime": "10:00",
                        "closingTime": "14:00"
                    }
                ]
                                
                """.trim();
    }

    private String getEmployeeDate() {
        return """
                {
                  "name": "Seba",
                  "email": "Kot@example.com",
                  "availability": [
                    {
                      "dayOfWeek": "MONDAY",
                      "startTime": "09:00",
                      "endTime": "14:00"
                    },
                    {
                      "dayOfWeek": "TUESDAY",
                      "startTime": "09:00",
                      "endTime": "14:00"
                    },
                    {
                      "dayOfWeek": "WEDNESDAY",
                      "startTime": "09:00",
                      "endTime": "14:00"
                    },
                    {
                      "dayOfWeek": "THURSDAY",
                      "startTime": "09:00",
                      "endTime": "14:00"
                    },
                    {
                      "dayOfWeek": "FRIDAY",
                      "startTime": "09:00",
                      "endTime": "14:00"
                    },
                    {
                      "dayOfWeek": "SATURDAY",
                      "startTime": "10:00",
                      "endTime": "12:00"
                    },
                    {
                      "dayOfWeek": "SUNDAY",
                      "startTime": "10:00",
                      "endTime": "12:00"
                    }
                  ]
                }
                                
                """.trim();
    }

//    @Transactional
//    public void insertRoles() {
//        jdbcTemplate.execute("""
//        INSERT INTO user_role (name, description)
//        VALUES
//            ('USER', 'People who would like to make reservations'),
//            ('EMPLOYEE', 'Staff members who manage reservations and services'),
//            ('ADMIN', 'Administrators with full system access and management capabilities'),
//            ('OWNER', 'Salon owners with management access to their respective salons');
//    """);
//    }

}
