package ru.itis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,  classes = {
        RestAssuredTestConfiguration.class,
//                ManagementPortTestConfiguration.class,
//                PGContainerTestConfiguration.class,
//                LiquibaseTestConfiguration.class,
//                WireMockContainerTestConfiguration.class
})
@AutoConfigureMockMvc
public class IntegrationTest {

    @Autowired
    protected ApplicationContext context;
}
