package net.wuchubuzai.api;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface ApiInterface {
	Map<String, Object> get(String objectType, String objectId, Map<String, String> attributes, String restKey) throws InterruptedException, ExecutionException;
	Map<String, Object> put(String objectType, String objectId, Map<String, String> attributes, String restKey) throws InterruptedException, ExecutionException;
	Map<String, Object> post(String objectType, Map<String, String> attributes, String restKey) throws InterruptedException, ExecutionException;
	Map<String, Object> search(String objectType, Map<String, String> attributes, String restKey) throws InterruptedException, ExecutionException;
	Map<String, Object> sendPackage(String methodName, String objectType, String objectId, Map<String, String> attributes, String restKey, String targetLanguage) throws InterruptedException, ExecutionException;
	String getApiUrl();
	void setApiUrl(String apiUrl);
	boolean useHttps();
	void setUseHttps(boolean useHttps);
	String getApiKey();
	void setApiKey(String apiKey);
	String getApiSecret();
	void setApiSecret(String apiSecret);
	String getApplicationId();
	void setApplicationId(String applicationId);
}
