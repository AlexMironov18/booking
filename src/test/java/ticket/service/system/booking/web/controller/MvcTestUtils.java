package ticket.service.system.booking.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.assertj.core.api.Assertions.assertThat;

public class MvcTestUtils {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> ResultMatcher containsObjectAsJson(Object expectedObject, Class<T> targetClass) {
        return mvcResult -> {
            String json = mvcResult.getResponse().getContentAsString();
            T actualObject = objectMapper.readValue(json, targetClass);
            assertThat(actualObject).usingRecursiveComparison().isEqualTo(expectedObject);
        };
    }

    public static <T> ResultMatcher hasMessage(String expectedMessage) {
        return mvcResult -> {
            String message = mvcResult.getResponse().getContentAsString();
            assertThat(message).isEqualTo(expectedMessage);
        };
    }
}
