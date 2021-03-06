package example.weather;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.PactProviderRule;
import au.com.dius.pact.consumer.junit.PactVerification;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import example.helper.FileLoader;
import java.io.IOException;
import java.util.Optional;
import org.apache.http.entity.ContentType;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WeatherClientConsumerTest {

  @Rule
  public PactProviderRule weatherProvider = new PactProviderRule
      ("weather_provider", "localhost", 8089, this);
  @Autowired
  private WeatherClient weatherClient;

  @Pact(consumer = "sample_microservice")
  public RequestResponsePact createPact(PactDslWithProvider builder) throws IOException {
    return builder
        .given("weather forecast data")
        .uponReceiving("a request for a weather request for Hamburg")
        .path("/some-test-api-key/53.5511,9.9937")
        .method("GET")
        .willRespondWith()
        .status(200)
        .body(FileLoader.read("classpath:weatherApiResponse.json"), ContentType.APPLICATION_JSON)
        .toPact();
  }

  @Test
  @PactVerification("weather_provider")
  public void shouldFetchWeatherInformation() throws Exception {
    Optional<WeatherResponse> weatherResponse = weatherClient.fetchWeather();
    assertThat(weatherResponse.isPresent(), is(true));
    assertThat(weatherResponse.get().getSummary(), is("Rain"));
  }
}
