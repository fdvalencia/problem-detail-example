package org.federico.usercreation.adapter.controller;

import com.tietoevry.quarkus.resteasy.problem.HttpProblem;
import com.tietoevry.quarkus.resteasy.problem.postprocessing.ProblemContext;
import com.tietoevry.quarkus.resteasy.problem.postprocessing.ProblemPostProcessor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;

@ApplicationScoped
public class CustomProblemPostProcessor implements ProblemPostProcessor {
    @Context
    UriInfo uriInfo;

    @Override
    public HttpProblem apply(HttpProblem problem, ProblemContext context) {
        return HttpProblem.builder(problem)
                .withInstance(URI.create(uriInfo.getPath()))
                .withType(getCurrentTypeOrElseBlank(problem))
                .build();
    }

    private static URI getCurrentTypeOrElseBlank(HttpProblem problem) {
        return problem.getType() != null ? problem.getType() : URI.create("about:blank");
    }
}
