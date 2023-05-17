package ru.itis;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import ru.itis.dto.MultiswarmCreateDtoRequest;
import ru.itis.dto.MultiswarmResultDtoRequest;
import ru.itis.dto.MultiswarmResultDtoResponse;

import java.util.HashMap;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;


public class IntegrationSwarmTest extends IntegrationTest {

    @Test
    public void createSwarm() {
        MultiswarmCreateDtoRequest request = MultiswarmCreateDtoRequest.builder()
                .numSwarms(10)
                .particlesPerSwarm(100)
                .expression("-(-(x1+47)*sin(sqrt(abs((x0/2)+(x1+47))))-x0*sin(sqrt(abs(x0-(x1+47)))))")
                .min(new Double[]{ -512.0, -512.0})
                .max(new Double[]{ 512.0, 512.0})
                .build();

        RestAssuredMockMvc.given().body(request)
                .post("/api/swarm")
                .then()
                .log().all()
                .assertThat().statusCode(200);
    }

    @Test
    public void calculateSwarm() {
        createSwarm();

        MultiswarmResultDtoResponse response = RestAssuredMockMvc.given()
                .body(MultiswarmResultDtoRequest.builder()
                        .loopCount(10)
                        .build())
                .post("/api/swarm/calculate")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .body(
                        hasJsonPath("$.bestFitness", Matchers.greaterThan(959.0))
                )
                .extract().body().as(MultiswarmResultDtoResponse.class);
        System.out.println(response);
    }
}
