package com.hugnew.test.sps.common;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={"/applicationContext.xml", "/spring_mvc.xml", "/spring_rabbitmq.xml", "/spring_shiro.xml"})
public class BaseContextCase extends
		AbstractTransactionalJUnit4SpringContextTests{
//		AbstractJUnit4SpringContextTests {

	protected MockHttpServletRequest  request;
	protected MockHttpSession session;
	protected MockHttpServletResponse response;
	protected MockMvc mockMvc;
	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setup() throws Exception {
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		session = new MockHttpSession();

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();


	}
}
