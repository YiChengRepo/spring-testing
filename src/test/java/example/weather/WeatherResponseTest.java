package example.weather;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.helper.FileLoader;
import org.junit.Test;

public class WeatherResponseTest {

  @Test
  public void shouldDeserializeJson() throws Exception {
    String jsonResponse = FileLoader.read("classpath:weatherApiResponse.json");
    WeatherResponse expectedResponse = new WeatherResponse("Rain");

    WeatherResponse parsedResponse = new ObjectMapper()
        .readValue(jsonResponse, WeatherResponse.class);

    assertThat(parsedResponse, is(expectedResponse));
  }
}