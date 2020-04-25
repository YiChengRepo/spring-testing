package example.weather;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import example.helper.FileLoader;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WeatherAcceptanceTest {

  @Rule
  public WireMockRule wireMockRule = new WireMockRule(8089);
  @LocalServerPort
  private int port;

  @Test
  public void shouldReturnYesterdaysWeather() throws Exception {
    wireMockRule.stubFor(get(urlPathEqualTo("/some-test-api-key/53.5511,9.9937"))
        .willReturn(aResponse()
            .withBody(FileLoader.read("classpath:weatherApiResponse.json"))
            .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .withStatus(200)));

    when()
        .get(String.format("http://localhost:%s/weather", port))
        .then()
        .statusCode(is(200))
        .body(containsString("Rain"));
  }
}
