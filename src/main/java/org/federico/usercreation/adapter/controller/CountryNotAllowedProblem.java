package org.federico.usercreation.adapter.controller;

import com.tietoevry.quarkus.resteasy.problem.HttpProblem;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.net.URI;

@Schema(
        name = "CountryNotAllowedProblem",
        description = "Exception indicating that the country is not allowed",
        example = """
                {
                  "status": 400,
                  "type": "https://example.com/problem/country-not-allowed",
                  "title": "The country is not allowed",
                  "detail": "The country is not allowed",
                  "instance": "/user"
                }
                """
)
public class CountryNotAllowedProblem extends HttpProblem {
    public CountryNotAllowedProblem(String message) {
        super(builder()
                .withStatus(Response.Status.BAD_REQUEST)
                .withType(URI.create("https://example.com/problem/country-not-allowed"))
                .withTitle(message)
                .withDetail(message)
        );
    }
}
