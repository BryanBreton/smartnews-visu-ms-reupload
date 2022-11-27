package fr.su.smartnewsvisu.integrationtest;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.JsonNode;

import fr.su.smartnewsvisu.SmartnewsVisuApplication;

/**
 * Test le health check
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SmartnewsVisuApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class HealthCheckIT {

    /**
     * endpoint du healthcheck
     */
    private static final String HEALTHCHECK_ENDPOINT = "/actuator/health";

    /**
     * Template REST permettant de valider les appels HTTP.
     */
    @Autowired
    TestRestTemplate restTemplate;

    /**
     * Test le healthcheck
     */
    @Test
    @DisplayName("Test du health check")
    public void healthcheck_should_be_ok() {
        //given

        //when
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(HEALTHCHECK_ENDPOINT, JsonNode.class);

        //then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        String serviceStatus = Optional.ofNullable(response.getBody())
                .map(it -> it.get("status"))
                .map(JsonNode::asText)
                .orElse("");
        Assertions.assertThat(serviceStatus).isEqualTo("UP");
    }
}
