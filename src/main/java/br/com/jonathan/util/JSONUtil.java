package br.com.jonathan.util;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.scheduling.annotation.Async;

public final class JSONUtil {

	@Async
	public static String xmlToJson(String xml) {
		JSONObject json = XML.toJSONObject(xml);
		return json.toString();
	}

}