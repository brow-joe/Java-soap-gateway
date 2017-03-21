package br.com.jonathan.soap;

import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;

public final class ConnectionAvailable {
	private static final Logger logger = LogManager.getLogger(ConnectionAvailable.class);

	@Async
	public static void available(String url) throws ConnectionAvailableException {
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(StringUtils.remove(url, "?wsdl"))
					.openConnection();
			connection.setRequestMethod("GET");
			int responseCode = connection.getResponseCode();
			if (responseCode != 200) {
				throw new ConnectionAvailableException("Response error: " + responseCode);
			}
		} catch (Exception e) {
			logger.error(e);
			throw new ConnectionAvailableException(e);
		}
	}

}