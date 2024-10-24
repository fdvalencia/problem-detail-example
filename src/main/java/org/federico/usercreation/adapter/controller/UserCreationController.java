package org.federico.usercreation.adapter.controller;

import com.tietoevry.quarkus.resteasy.problem.HttpProblem;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.federico.usercreation.application.CountryNotAllowedException;
import org.federico.usercreation.application.DuplicateEnrollmentException;
import org.federico.usercreation.application.User;
import org.federico.usercreation.application.UserCreationUseCase;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/user")
@RequiredArgsConstructor
public class UserCreationController {

    private final UserCreationUseCase userCreationUseCase;

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @Operation(description = "Create user with the given information")
    @APIResponses(value = {
            @APIResponse(
                    responseCode = "200",
                    description = "User created",
                    content = @Content(
                            mediaType = APPLICATION_JSON,
                            schema = @Schema(implementation = UserResponse.class))),
            @APIResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = @Content(
                            mediaType = APPLICATION_JSON,
                            schema = @Schema(oneOf = {DuplicateEnrollmentProblem.class, CountryNotAllowedProblem.class}))),
            @APIResponse(
                    responseCode = "500",
                    description = "Unexpected error",
                    content = @Content(
                            schema = @Schema(implementation = HttpProblem.class),
                            examples = @ExampleObject(
                                    name = "UnexpectedErrorExample",
                                    value = """
                                            {
                                                "status": 500,
                                                "type": "about:blank",
                                                "title": "Internal Server Error",
                                                "detail": "HTTP 500 Internal Server Error",
                                                "instance": "/user"
                                              }
                                            """
                            )
                    )
            )
    })
    public UserResponse createUser(@Valid UserRequest userRequest) {
        User user = userRequest.toUser();
        try {
            User userSaved = userCreationUseCase.createUser(user);
            return UserResponse.fromUser(userSaved);
        } catch (DuplicateEnrollmentException e) {
            throw new DuplicateEnrollmentProblem(e.getMessage());
        } catch (CountryNotAllowedException e) {
            throw new CountryNotAllowedProblem(e.getMessage());
        }
    }
}
