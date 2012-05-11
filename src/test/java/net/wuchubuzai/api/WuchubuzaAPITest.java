package net.wuchubuzai.api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WuchubuzaAPITest {

	private WuchubuzaiAPI api;
	static final Logger log = LoggerFactory.getLogger(WuchubuzaAPITest.class);
	
	@Before
	public void setUp() throws Exception {
		api = new WuchubuzaiAPI();
	}

	@Test
	public void testGetApiUrls() {
		// production (default)
		Assert.assertEquals("http://api.wuchubuzai.com", api.getApiUrl());
		
		// development
		api.setApiUrl("dev.api.wuchubuzai.com");
		Assert.assertEquals("http://dev.api.wuchubuzai.com", api.getApiUrl());
		
		// sandbox
		api.setApiUrl("sbx.api.wuchubuzai.com");
		Assert.assertEquals("http://sbx.api.wuchubuzai.com", api.getApiUrl());
	}

	
	
	
}
