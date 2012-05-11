package net.wuchubuzai.api;

import java.util.HashMap;

public interface ApiInterface {
	abstract HashMap<String, String> get(String objectType, String objectId, HashMap<String, String> attributes, String restKey);
	abstract HashMap<String, String> put(String objectType, String objectId, HashMap<String, String> attributes, String restKey);
	abstract HashMap<String, String> post(String objectType, HashMap<String, String> attributes, String restKey);
	abstract HashMap<String, String> search(String objectType, HashMap<String, String> attributes, String restKey);
	abstract HashMap<String, Object> sendPackage(String methodName, String objectType, String objectId, HashMap<String, String> attributes, String restKey, String targetLanguage);
	abstract String getApiUrl();
	abstract void setApiUrl(String _apiUrl);
	abstract boolean useHttps();
	abstract void setUseHttps(boolean _useHttps);
	abstract String getApiKey();
	abstract void setApiKey(String _apiKey);
	abstract String getApiSecret();
	abstract void setApiSecret(String _apiSecret);
	abstract boolean isDebug();
	abstract void setDebug(boolean _debug);
	abstract String getApplicationId();
	abstract void setApplicationId(String _applicationId);
}
