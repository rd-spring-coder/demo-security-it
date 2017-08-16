package com.rdspringcoder;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoSecurityApplicationTests {

//	@Autowired
//	private FilterChainProxy springSecurityFilterChain;
//	
//	private MockHttpServletRequest request;
//	
//	private MockHttpServletResponse response;
//	
//	private MockFilterChain chain;

	@Autowired private WebApplicationContext context;
	private MockMvc mockMvc;
	
	@Before
	public void setup(){
//		this.request = new MockHttpServletRequest();
//		this.response = new MockHttpServletResponse();
//		this.chain = new MockFilterChain();
		mockMvc = MockMvcBuilders
					.webAppContextSetup(context)
				//	.defaultRequest(get("/").with(user("priya").password("priya").roles("ADMIN","USER")))
					.apply(springSecurity())
					.build();
	}
	
	@Test
	public void testUriThatShouldPassSecurityFilter() throws Exception{
		mockMvc.perform(get("/hello")).
			andExpect(status().is3xxRedirection());
	}
	
	@Test
	public void testUriThatShouldNotPassSecurityFilter() throws Exception{
		mockMvc.perform(get("/"))
			.andExpect(status().isOk());
	}
	
	@Test
	public void testUriThatShouldPassSecurityFilterAndFail() throws Exception{
		mockMvc.perform(get("/hello").with(anonymous()));
	}
	
	@Test
	public void testOnlyAdminRole() throws Exception{
		mockMvc.perform(formLogin("/login").user("ritesh").password("password"))
			.andExpect(authenticated().withRoles("ADMIN"));
	}

	@Test
	public void testAllRoles() throws Exception{
		mockMvc.perform(formLogin("/login").user("priya").password("priya"))
			.andExpect(authenticated().withRoles("ADMIN","USER"));
	}

	@Test
	@WithMockUser(roles="USER")
	public void testOnlyUserRole() throws Exception{
		mockMvc.perform(formLogin("/login").user("naveen").password("naveen"))
			.andExpect(authenticated());
	}

	
//	@Test
//	public void requiresAuthentication() throws Exception{
//		this.springSecurityFilterChain.doFilter(this.request, this.response, this.chain);
//		assertThat(this.response.getStatus())
//			.isEqualTo(HttpServletResponse.SC_UNAUTHORIZED);
//		
//	}
}
