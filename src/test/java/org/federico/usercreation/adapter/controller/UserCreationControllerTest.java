package org.federico.usercreation.adapter.controller;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;
import jakarta.ws.rs.ServerErrorException;
import jakarta.ws.rs.core.Response.Status;
import lombok.SneakyThrows;
import org.federico.usercreation.application.CountryNotAllowedException;
import org.federico.usercreation.application.DuplicateEnrollmentException;
import org.federico.usercreation.application.User;
import org.federico.usercreation.application.UserCreationUseCase;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONCompareMode;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.federico.util.TestFileReader.read;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@QuarkusTest
class UserCreationControllerTest {
    @InjectSpy
    UserCreationUseCase userCreationUseCase;

    @Test
    @SneakyThrows
    void shouldCreateUserWhenRequestIsSuccessful() {
        String expectedUserCreationBody = read("resource/user/response/userCreation200Response.json");

        String userCreationBody = given()
                .contentType(JSON)
                .accept(JSON)
                .body(read("resource/user/request/userCreationRequest.json"))
                .when().post("/user")
                .then()
                .statusCode(200)
                .contentType(JSON)
                .extract()
                .response()
                .getBody()
                .asPrettyString();

        assertEquals(expectedUserCreationBody, userCreationBody, JSONCompareMode.STRICT);
    }

    @Test
    @SneakyThrows
    void shouldReturnDuplicateEnrollmentError() {
        when(userCreationUseCase.createUser(any(User.class))).thenThrow(new DuplicateEnrollmentException("User already exists"));
        String expectedErrorResponseBody = read("resource/user/response/userDuplicateEnrollmentErrorBody400Response.json");

        String userDuplicateEnrollmentErrorBody = given()
                .contentType(JSON)
                .accept(JSON)
                .body(read("resource/user/request/userCreationRequest.json"))
                .when().post("/user")
                .then()
                .statusCode(400)
                .contentType(JSON)
                .extract()
                .response()
                .getBody()
                .asPrettyString();

        assertEquals(expectedErrorResponseBody, userDuplicateEnrollmentErrorBody, JSONCompareMode.STRICT);
    }

    @Test
    @SneakyThrows
    void shouldReturnCountryNotAllowedError() {
        when(userCreationUseCase.createUser(any(User.class))).thenThrow(new CountryNotAllowedException());
        String expectedErrorResponseBody = read("resource/user/response/userCreationCountryNotAllowed.json");

        String countryNotAllowedErrorBody = given()
                .contentType(JSON)
                .accept(JSON)
                .body(read("resource/user/request/userCreationRequest.json"))
                .when().post("/user")
                .then()
                .statusCode(400)
                .contentType(JSON)
                .extract()
                .response()
                .getBody()
                .asPrettyString();

        assertEquals(expectedErrorResponseBody, countryNotAllowedErrorBody, JSONCompareMode.STRICT);
    }

    @Test
    @SneakyThrows
    void shouldReturnBadRequestWhenRequiredFieldIsMissing() {
        String expectedErrorResponseBody = read("resource/user/response/userCreationBadRequestRequestViolations.json");

        String countryNotAllowedErrorBody = given()
                .contentType(JSON)
                .accept(JSON)
                .body(read("resource/user/request/userCreationRequestWithMissingName.json"))
                .when().post("/user")
                .then()
                .statusCode(400)
                .contentType(JSON)
                .extract()
                .response()
                .getBody()
                .asPrettyString();

        assertEquals(expectedErrorResponseBody, countryNotAllowedErrorBody, JSONCompareMode.STRICT);
    }

    @Test
    @SneakyThrows
    void shouldReturnUnexpectedError() {
        when(userCreationUseCase.createUser(any(User.class))).thenThrow(new ServerErrorException(Status.INTERNAL_SERVER_ERROR));
        String expectedErrorResponseBody = read("resource/user/response/unexpectedErrorBody500Response.json");

        String countryNotAllowedErrorBody = given()
                .contentType(JSON)
                .accept(JSON)
                .body(read("resource/user/request/userCreationRequest.json"))
                .when().post("/user")
                .then()
                .statusCode(500)
                .contentType(JSON)
                .extract()
                .response()
                .getBody()
                .asPrettyString();

        assertEquals(expectedErrorResponseBody, countryNotAllowedErrorBody, JSONCompareMode.STRICT);
    }

}