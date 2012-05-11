package net.wuchubuzai.api;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public interface ApiInterface {
	abstract HashMap<String, Object> get(String objectType, String objectId, HashMap<String, String> attributes, String restKey) throws InterruptedException, ExecutionException;
	abstract HashMap<String, Object> put(String objectType, String objectId, HashMap<String, String> attributes, String restKey) throws InterruptedException, ExecutionException;
	abstract HashMap<String, Object> post(String objectType, HashMap<String, String> attributes, String restKey) throws InterruptedException, ExecutionException;
	abstract HashMap<String, Object> search(String objectType, HashMap<String, String> attributes, String restKey) throws InterruptedException, ExecutionException;
	abstract HashMap<String, Object> sendPackage(String methodName, String objectType, String objectId, HashMap<String, String> attributes, String restKey, String targetLanguage) throws InterruptedException, ExecutionException;
	abstract String getApiUrl();
	abstract void setApiUrl(String _apiUrl);
	abstract boolean useHttps();
	abstract void setUseHttps(boolean _useHttps);
	abstract String getApiKey();
	abstract void setApiKey(String _apiKey);
	abstract String getApiSecret();
	abstract void setApiSecret(String _apiSecret);
	abstract String getApplicationId();
	abstract void setApplicationId(String _applicationId);
}
