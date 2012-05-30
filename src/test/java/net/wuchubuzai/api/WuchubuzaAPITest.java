package net.wuchubuzai.api;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WuchubuzaAPITest {

	private WuchubuzaiAPI api;
	
	private String expectedNickname = "-----";
	private String expectedUid = "-----";
	private String emailAddress = "-----";
	private String password = "-----";
	private String restKey = "-----";
	private String apiUrl = "dev.api.wuchubuzai.com";
	
	
	@Before
	public void setUp() throws Exception {
		api = new WuchubuzaiAPI();
	}
	
	@Test
	public void testGetApiUrls() {
		// production (default)
		Assert.assertEquals("http://api.wuchubuzai.com", api.getApiUrl());
		
		// development
		api.setApiUrl(this.apiUrl);
		Assert.assertEquals("http://dev.api.wuchubuzai.com", api.getApiUrl());
		
		// sandbox
		api.setApiUrl("sbx.api.wuchubuzai.com");
		Assert.assertEquals("http://sbx.api.wuchubuzai.com", api.getApiUrl());
	}

	@Test
	public void testGetUser() {
		api.setApiUrl(this.apiUrl);
		Map<String, Object> apiResults = api.get("user", expectedUid, null, null);
		Assert.assertEquals(expectedNickname, apiResults.get("nickname").toString());
	}
	
	@Test
	public void testPostAuthentication() {
		api.setApiUrl(this.apiUrl);
		HashMap<String, String> attributes = new HashMap<String, String>();
		attributes.put("email", this.emailAddress);
		attributes.put("password", this.password);
		
		Map<String, Object> apiResults = api.post("accounts", attributes, null);
		Assert.assertEquals("API_SUCCESS_000002", apiResults.get("success_code").toString());
		this.restKey = apiResults.get("rest_key").toString();
	}
	
	@Test
	public void testSearch() { 
		api.setApiUrl(this.apiUrl);
		if (this.restKey != null) { 
			HashMap<String, String> attributes = new HashMap<String, String>();
			attributes.put("gender", "1");
			attributes.put("seeking_gender", "2");
			Map<String, Object> apiResults = api.search("user", attributes, this.restKey);
		}
	}
	
	
}
