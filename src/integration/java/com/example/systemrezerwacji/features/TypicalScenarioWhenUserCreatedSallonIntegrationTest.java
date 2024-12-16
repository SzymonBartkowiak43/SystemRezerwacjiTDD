package com.example.systemrezerwacji.features;

import com.example.systemrezerwacji.BaseIntegrationTest;

import com.example.systemrezerwacji.domain.code_module.CodeFacade;
import com.example.systemrezerwacji.domain.code_module.dto.CodeDto;
import com.example.systemrezerwacji.domain.user_module.dto.UserRegisterDto;
import com.example.systemrezerwacji.domain.user_module.response.UserFacadeResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

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
        // when
        ResultActions preformCreateSalon = mockMvc.perform(post("/salon")
                .header("Authorization", "Bearer " + token)
                .content("""
                        {
                            "salonName": "LAMBADZIARA",
                            "category": "Hair and Beauty",
                            "city": "XXXXXXXXXX",
                            "zipCode": "00-001",
                            "street": "Main Street",
                            "number": "123",
                            "userId": 1,
                            "code": "123e4567-e89b-12d3-a456-426614174000"
                        }
                        """.trim())
        );

        // then


//        step 6: user made POST /salon/1/opening-hours with valid hours and system returned OK(200)
//        step 7: user made POST /salon/1/employees with employee details and system added the employee, returning OK(200) with employeeId=1
//        step 8: user made POST /salon/1/offers with offer details and system created the offer, returning OK(200) with offerId=1
//        step 9: user made POST /employee/1/assign-offer with offerId=1 and system assigned the offer to the employee, returning OK(200)

//
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
