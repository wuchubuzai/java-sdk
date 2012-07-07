package net.wuchubuzai.api;

import java.io.IOException;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.representation.Form;
import com.sun.jersey.api.uri.UriBuilderImpl;

/**
 * 
 * @author sd
 *
 */
public class WuchubuzaiAPI implements ApiInterface {

	static final Logger LOG = LoggerFactory.getLogger(WuchubuzaiAPI.class);
	private String apiUrl = "api.wuchubuzai.com";
	private boolean useHttps = false;
	private String applicationId;
	private String apiKey;
	private String apiSecret;
	
	private static ObjectMapper mapper = new ObjectMapper();

	public Map<String, Object> get(String objectType, String objectId, Map<String, String> attributes, String restKey) {
		if (LOG.isDebugEnabled()) { 
			LOG.debug("GET request for " + objectType);
		}
		return sendPackage("GET", objectType, objectId, attributes, restKey, null);
	}

	public Map<String, Object> put(String objectType, String objectId, Map<String, String> attributes, String restKey) {
		return sendPackage("PUT", objectType, objectId, attributes, restKey, null);
	}

	public Map<String, Object> post(String objectType, Map<String, String> attributes, String restKey) {
		return sendPackage("POST", objectType, null, attributes, restKey, null);		
	}

	public Map<String, Object> search(String objectType, Map<String, String> attributes, String restKey) {
		return sendPackage("SEARCH", objectType, null, attributes, restKey, null);		
	}

	public Map<String, Object> sendPackage(String methodName, String objectType, String objectId, Map<String, String> attributes, String restKey, String targetLanguage) {
		
		DefaultClientConfig config = new DefaultClientConfig();
		Client c = Client.create(config);
		
		if (methodName.equalsIgnoreCase("GET")) { 
			UriBuilderImpl builder = new UriBuilderImpl();
			builder.queryParam("id", objectId);
				
			if (LOG.isDebugEnabled()) { 
				LOG.debug(getApiUrl() + "/" + objectType + "/" + builder.build().toString());
			}
			WebResource r = c.resource(getApiUrl() + "/" + objectType + "/" + builder.build().toString()); 
			String response = r.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).header("USER-AGENT", "wuchubuzai java-sdk /1.1").get(String.class);
			if (LOG.isDebugEnabled()) {
				LOG.debug(response);
			}
			
			try {
				return mapper.readValue(response, Map.class);
				// return apiResponse;
			} catch (JsonParseException e) {
				LOG.error("JsonParseException:" + e.getMessage());
			} catch (JsonMappingException e) {
				LOG.error("JsonMappingException:" + e.getMessage());
			} catch (IOException e) {
				LOG.error("IOException:" + e.getMessage());
			}
				
		} else {
			Form f = new Form();
			if (restKey != null) {
				f.add("rest_key", restKey);
			}
			if (getApplicationId() != null) {
				f.add("app_id", getApplicationId());
			}
			if (objectId != null) {
				f.add("id", objectId);
			}

			WebResource r = c.resource(getApiUrl() + "/" + objectType + "/");
			if (attributes.size() > 0) { 
				for (Map.Entry<String, String> attr : attributes.entrySet()) {
					f.add(attr.getKey(), attr.getValue());
				}
			}

			if (LOG.isDebugEnabled()) { 
				LOG.debug(f.toString());
			}
			// TODO com.sun.jersey.api.client.ClientHandlerException: java.net.ProtocolException: Invalid HTTP method: SEARCH
			// http://stackoverflow.com/questions/10656812/jersey-http-client-custom-request-method
			String response;
			if (methodName.equalsIgnoreCase("SEARCH")) { 
				response = r.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).header("USER-AGENT", "wuchubuzai java-sdk /1.1").method("POST", String.class, f);
				f.add("method", "browse");
			} else {  
				response = r.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).header("USER-AGENT", "wuchubuzai java-sdk /1.1").method(methodName.toUpperCase(), String.class, f);
			}
			if (LOG.isDebugEnabled()) {
				LOG.debug(methodName.toUpperCase() + ": " + response);		
			}
			try {
				return mapper.readValue(response, Map.class);	
			} catch (JsonParseException e) {
				LOG.error("JsonParseException:" + e.getMessage());
			} catch (JsonMappingException e) {
				LOG.error("JsonMappingException:" + e.getMessage());
			} catch (IOException e) {
				LOG.error("IOException:" + e.getMessage());
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

	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	public boolean useHttps() {
		return this.useHttps;
	}

	public void setUseHttps(boolean useHttps) {
		this.useHttps = useHttps;
	}

	public String getApiKey() {
		return this.apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getApiSecret() {
		return this.apiSecret;
	}

	public void setApiSecret(String apiSecret) {
		this.apiSecret = apiSecret;
	}

	public String getApplicationId() {
		return this.applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
}
