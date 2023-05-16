package ru.itis;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import ru.itis.dto.GeneticResultDtoRequest;
import ru.itis.dto.GeneticResultDtoResponse;
import ru.itis.dto.PopulationCreateDtoRequest;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;

public class IntegrationGeneticTest extends  IntegrationTest {

    @Test
    public void createPopulation() {
        PopulationCreateDtoRequest request = PopulationCreateDtoRequest.builder()
                .max(new Double[]{ 512.0, 512.0})
                .min(new Double[]{ -512.0, -512.0})
                .populationSize(1000)
                .generationCount(100)
                .hallOfFameSize(50)
                .mutationProbability(0.8)
                .crossingProbability(0.9)
                .build();

        RestAssuredMockMvc.given().body(request)
                .post("/api/genetic")
                .then()
                .log().all()
                .assertThat().statusCode(200);
    }

    @Test
    public void calculateGenetic() {
        createPopulation();

        GeneticResultDtoResponse response = RestAssuredMockMvc.given()
                .body(GeneticResultDtoRequest.builder()
                        .expression("-(-(x1+47)*sin(sqrt(abs((x0/2)+(x1+47))))-x0*sin(sqrt(abs(x0-(x1+47)))))")
                        .build())
                .put("/api/genetic/calculate")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .body(
                        hasJsonPath("$.bestFitness", Matchers.greaterThan(950.0))
                )
                .extract().body().as(GeneticResultDtoResponse.class);
        System.out.println(response);
    }
}
