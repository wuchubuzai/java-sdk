package net.wuchubuzai.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;
import com.sun.jersey.api.uri.UriBuilderImpl;

/**
 * 
 * @author sd
 *
 */
public class WuchubuzaiAPI implements ApiInterface {

	static final Logger log = LoggerFactory.getLogger(WuchubuzaiAPI.class);
	private String apiUrl = "api.wuchubuzai.com";
	private boolean useHttps = false;
	private String applicationId;
	private String apiKey;
	private String apiSecret;
	
	private static ObjectMapper mapper = new ObjectMapper();

	public HashMap<String, Object> get(String objectType, String objectId, HashMap<String, String> attributes, String restKey) {
		if (log.isDebugEnabled()) log.debug("GET request for " + objectType);
		return sendPackage("GET", objectType, objectId, attributes, restKey, null);
	}

	public HashMap<String, Object> put(String objectType, String objectId, HashMap<String, String> attributes, String restKey) {
		return sendPackage("PUT", objectType, objectId, attributes, restKey, null);
	}

	public HashMap<String, Object> post(String objectType, HashMap<String, String> attributes, String restKey) {
		return sendPackage("POST", objectType, null, attributes, restKey, null);		
	}

	public HashMap<String, Object> search(String objectType, HashMap<String, String> attributes, String restKey) {
		return sendPackage("SEARCH", objectType, null, attributes, restKey, null);		
	}

	public HashMap<String, Object> sendPackage(String methodName, String objectType, String objectId, HashMap<String, String> attributes, String restKey, String targetLanguage) {
		
		Client c = Client.create();
		c.setFollowRedirects(true);
		
		if (methodName.toUpperCase().equals("GET")) { 
			UriBuilderImpl builder = new UriBuilderImpl();
			builder.queryParam("id", objectId);
				
			log.debug(getApiUrl() + "/" + objectType + "/" + builder.build().toString());
			WebResource r = c.resource(getApiUrl() + "/" + objectType + "/" + builder.build().toString()); // TODO encode the attributes into a query string at the end of the url
			String response = r.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).header("USER-AGENT", "wuchubuzai java-sdk /1.1").get(String.class);
			if (log.isDebugEnabled()) log.debug(response);
			
			try {
				HashMap<String, Object> apiResponse = mapper.readValue(response, HashMap.class);
				return apiResponse;
			} catch (JsonParseException e) {
				log.error("JsonParseException:" + e.getMessage());
			} catch (JsonMappingException e) {
				e.printStackTrace();
				log.error("JsonMappingException:" + e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				log.error("IOException:" + e.getMessage());
			}
				
		} else {
			Form f = new Form();
			if (restKey != null) f.add("rest_key", restKey);
			if (getApplicationId() != null) f.add("app_id", getApplicationId());
			if (objectId != null) f.add("id", objectId);

			WebResource r = c.resource(getApiUrl() + "/" + objectType + "/");
			if (attributes.size() > 0) { 
				for (Map.Entry<String, String> attr : attributes.entrySet()) {
					f.add(attr.getKey(), attr.getValue());
				}
			}

			log.debug(f.toString());
			// TODO this is causing problems (returning HTTP/1.1 411 Length Required from nginx
			String response = r.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).header("USER-AGENT", "wuchubuzai java-sdk /1.1").method(methodName.toUpperCase(), String.class, f);
			if (log.isDebugEnabled()) log.debug(methodName.toUpperCase() + ": " + response);		
			try {
				HashMap<String, Object> apiResponse = mapper.readValue(response, HashMap.class);				
				return apiResponse;
			} catch (JsonParseException e) {
				log.error("JsonParseException:" + e.getMessage());
			} catch (JsonMappingException e) {
				e.printStackTrace();
				log.error("JsonMappingException:" + e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				log.error("IOException:" + e.getMessage());
			}			
		}
		return null;
	}

	public String getApiUrl() {
		if (this.useHttps) { 
			return "https://" + this.apiUrl;
		} else { 
			return "http://" + this.apiUrl;
		}
	}

	public void setApiUrl(String _apiUrl) {
		this.apiUrl = _apiUrl;
	}

	public boolean useHttps() {
		return this.useHttps;
	}

	public void setUseHttps(boolean _useHttps) {
		this.useHttps = _useHttps;
	}

	public String getApiKey() {
		return this.apiKey;
	}

	public void setApiKey(String _apiKey) {
		this.apiKey = _apiKey;
	}

	public String getApiSecret() {
		return this.apiSecret;
	}

	public void setApiSecret(String _apiSecret) {
		this.apiSecret = _apiSecret;
	}

	public String getApplicationId() {
		return this.applicationId;
	}

	public void setApplicationId(String _applicationId) {
		this.applicationId = _applicationId;
	}
}
