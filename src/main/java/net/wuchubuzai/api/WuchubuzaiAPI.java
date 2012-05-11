package net.wuchubuzai.api;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;

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
	private boolean debug = false;
	
	

	public HashMap<String, String> get(String objectType, String objectId,
			HashMap<String, String> attributes, String restKey) {
		// TODO Auto-generated method stub
		// sendPackage(methodName, objectType, objectId, attributes, restKey, targetLanguage);
		return null;
	}

	public HashMap<String, String> put(String objectType, String objectId,
			HashMap<String, String> attributes, String restKey) {
		// TODO Auto-generated method stub
		// sendPackage(methodName, objectType, objectId, attributes, restKey, targetLanguage);
		return null;
	}

	public HashMap<String, String> post(String objectType,
			HashMap<String, String> attributes, String restKey) {
		// TODO Auto-generated method stub
		// sendPackage(methodName, objectType, objectId, attributes, restKey, targetLanguage);		
		return null;
	}

	public HashMap<String, String> search(String objectType,
			HashMap<String, String> attributes, String restKey) {
		// TODO Auto-generated method stub
		// sendPackage(methodName, objectType, objectId, attributes, restKey, targetLanguage);		
		return null;
	}

	public HashMap<String, Object> sendPackage(String methodName, String objectType, String objectId, HashMap<String, String> attributes, String restKey, String targetLanguage) {
		
		Form f = new Form();
		if (restKey != null) f.add("rest_key", restKey); // not a mandatory parameter
		f.add("app_id", getApiKey());
		f.add("id", objectId);
				
		Client c = Client.create();
		c.setFollowRedirects(true); // just incase we 301/302 the api in the future
		

		if (methodName.toUpperCase().equals("GET")) { 
			WebResource r = c.resource(getApiUrl() + "/" + objectType); // TODO encode the attributes into a query string at the end of the url
			String response = r.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).header("USER-AGENT", "wuchubuzai java-sdk /1.1").get(String.class);
			if (log.isDebugEnabled()) log.debug(response);
		} else {
			WebResource r = c.resource(getApiUrl() + "/" + objectType);
			if (attributes.size() > 0) { 
				for (Map.Entry<String, String> attr : attributes.entrySet()) {
					f.add(attr.getKey(), attr.getValue());
				}
			}
			if (methodName.toUpperCase().equals("POST")) { 
				String response = r.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).header("USER-AGENT", "wuchubuzai java-sdk /1.1").method(methodName.toUpperCase(), String.class, f);
				if (log.isDebugEnabled()) log.debug(methodName.toUpperCase() + ": " + response);				
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

	public boolean isDebug() {
		return this.debug;
	}

	public void setDebug(boolean _debug) {
		this.debug = _debug;
	}

	public String getApplicationId() {
		return this.applicationId;
	}

	public void setApplicationId(String _applicationId) {
		this.applicationId = _applicationId;
	}
}
