package com.example.systemrezerwacji.features;

import com.example.systemrezerwacji.BaseIntegrationTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;

public class TypicalScenarioWhenUserCreatedSallonIntegrationTest extends BaseIntegrationTest {


    @Test
    void user_patch_to_create_a_salon() throws Exception {
//        step 1: user tried to get JWT token by requesting
//        POST /token with username=someUser, password=somePassword and system returned UNAUTHORIZED(401)




//        step 2: user made GET /offers with no jwt token and system returned UNAUTHORIZED(401)
//        step 3: user made POST /register with username=someUser, password=somePassword and system registered user with status OK(200)
//        step 4: user tried to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned OK(200) and jwttoken=AAAA.BBBB.CCC
//        step 5: user registered a new account by making POST /register with username=someOwner, password=somePassword and system returned OK(200)
//        step 6: user logged in by requesting POST /token with username=someOwner, password=somePassword and system returned OK(200) with jwttoken=XXXX.YYYY.ZZZ
//        step 7: user made POST /salon with valid salon details and system created the salon, returning OK(200) with salonId=1
//        step 8: user made POST /salon/1/opening-hours with valid hours and system returned OK(200)
//        step 9: user made POST /salon/1/employees with employee details and system added the employee, returning OK(200) with employeeId=1
//        step 10: user made POST /salon/1/offers with offer details and system created the offer, returning OK(200) with offerId=1
//        step 11: user made POST /employee/1/assign-offer with offerId=1 and system assigned the offer to the employee, returning OK(200)

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
}
