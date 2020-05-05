package cz.crynet.hitbtc_ws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.json.JsonParseException;

import java.io.IOException;

@Slf4j
public class JsonUtils {

	private JsonUtils() {
	}

	private static ObjectMapper mapper;

	private static ObjectMapper getMapper() {
		if (mapper == null) {
			mapper = new ObjectMapper()//.registerModules(new JavaTimeModule())
					.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
							false);
		}
		return mapper;
	}

	public static String objectToString(Object object) {
		try {
			return getMapper().writeValueAsString(object);
		} catch (JsonProcessingException e) {
			log.error("JSON-related error during converting object to String. Object"
					+ object, e);
		}
		return null;
	}

	public static String objectToPrettyString(Object object) {
		try {
			return getMapper().writerWithDefaultPrettyPrinter()
					.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			log.error("JSON-related error during converting object to String. Object"
					+ object, e);
		}
		return null;
	}

	public static <T> T stringToObject(String s, Class<T> clazz)
			throws JsonParseException {
		try {
			return getMapper().readValue(s, clazz);
		} catch (IOException e) {
			log.error("JSON-related error during parsing string " + s
					+ " to class " + clazz, e);
			throw new JsonParseException();
		}
	}

	public static <T> T stringToObject(String s, TypeReference<T> type)
			throws JsonParseException {
		try {
			return getMapper().readValue(s, type);
		} catch (IOException e) {
			log.error("JSON-related error during parsing string " + s + " to type "
					+ type, e);
			throw new JsonParseException();
		}

	}

}
