package org.federico.usercreation.adapter.controller;

import com.tietoevry.quarkus.resteasy.problem.HttpProblem;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.net.URI;

@Schema(
        name = "DuplicateEnrollmentProblem",
        description = "Problem indicating that the user is already enrolled",
        example = """
                {
                  "status": 400,
                  "type": "https://example.com/problem/user-already-exists",
                  "title": "User already exists",
                  "detail": "User already exists",
                  "instance": "/user"
                }
                """)
public class DuplicateEnrollmentProblem extends HttpProblem {
    public DuplicateEnrollmentProblem(String message) {
        super(builder()
                .withStatus(Response.Status.BAD_REQUEST)
                .withType(URI.create("https://example.com/problem/user-already-exists"))
                .withTitle(message)
                .withDetail(message)
        );
    }
}
