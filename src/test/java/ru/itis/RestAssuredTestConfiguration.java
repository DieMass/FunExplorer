package ru.itis;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import static org.apache.http.client.config.CookieSpecs.DEFAULT;
import static org.apache.http.client.params.ClientPNames.COOKIE_POLICY;
@TestConfiguration
public class RestAssuredTestConfiguration {

    @Autowired
    void setUp(final MockMvc mockMvc) {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @EventListener
    public void configure(WebServerInitializedEvent event) {
        if ("management".equals(event.getApplicationContext().getServerNamespace())) {
            return;
        }
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = event.getWebServer().getPort();
        RestAssured.config = RestAssured.config()
                .httpClient(HttpClientConfig.httpClientConfig().setParam(COOKIE_POLICY, DEFAULT));
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        RestAssuredMockMvc.requestSpecification = new MockMvcRequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader(HttpHeaders.ACCEPT, ContentType.JSON.getAcceptHeader())
                .log(LogDetail.ALL)
                .build();

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(null);
        RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails(null);
    }
}
