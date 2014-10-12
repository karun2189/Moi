package com.moiavenue.service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.type.TypeReference;

public class JsonJacksonUtil {
	private static final ObjectMapper mapper = new ObjectMapper();
	private static JsonFactory jf = new JsonFactory();

	@SuppressWarnings("rawtypes")
	public static Object deserializeEvent(InputStream is,
			TypeReference valueTypeRef) throws JsonParseException,
			JsonMappingException, IOException {
		mapper.configure(Feature.USE_GETTERS_AS_SETTERS, true);
		mapper.configure(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		return mapper.readValue(is, valueTypeRef);
	}

	public static <T> Object deserializeEvent(InputStream is,
			Class<T> valueClassType) throws JsonParseException,
			JsonMappingException, IOException {
		mapper.configure(Feature.USE_GETTERS_AS_SETTERS, true);
		mapper.configure(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		return mapper.readValue(is, valueClassType);
	}

	public static <T> Object deserializeEvent(String jsonAsString,
			Class<T> pojoClass) throws JsonMappingException,
			JsonParseException, IOException {

		mapper.configure(Feature.USE_GETTERS_AS_SETTERS, true);
		mapper.configure(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

		return mapper.readValue(jsonAsString, pojoClass);
//		return mapper.readValue(
//				jsonAsString, new TypeReference<List<LMD_Simplified_PriceData >>(){});
	}

	public static <T> Object deserializeEvent(FileReader fr, Class<T> pojoClass)
			throws JsonParseException, IOException {
		mapper.configure(Feature.USE_GETTERS_AS_SETTERS, true);
		mapper.configure(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		return mapper.readValue(fr, pojoClass);
	}

	@SuppressWarnings("deprecation")
	public static String toJson(Object pojo, boolean prettyPrint)
			throws JsonMappingException, JsonGenerationException, IOException {
		StringWriter sw = new StringWriter();
		mapper.configure(SerializationConfig.Feature.WRITE_NULL_PROPERTIES,
				false);
		mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
		JsonGenerator jg = jf.createJsonGenerator(sw);
		if (prettyPrint) {
			jg.useDefaultPrettyPrinter();
		}
		mapper.writeValue(jg, pojo);
		return sw.toString();
	}

	@SuppressWarnings("deprecation")
	public static void toJson(Object pojo, FileWriter fw, boolean prettyPrint)
			throws JsonMappingException, JsonGenerationException, IOException {
		JsonGenerator jg = jf.createJsonGenerator(fw);
		mapper.configure(SerializationConfig.Feature.WRITE_NULL_PROPERTIES,
				false);
		if (prettyPrint) {
			jg.useDefaultPrettyPrinter();
		}
		mapper.writeValue(jg, pojo);
	}

}
