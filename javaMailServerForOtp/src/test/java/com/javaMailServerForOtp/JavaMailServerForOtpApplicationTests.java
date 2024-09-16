package com.javaMailServerForOtp;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class JavaMailServerForOtpApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	String username = "sachim888777@gmail.com";
	String password = "827617";
	String encodedCredentials = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());

	@Test
	public void shouldOtpSendMail() throws Exception {
		mockMvc.perform(post("/otp")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"emailTo\": \"sachim888777@gmail.com\"}"))
				.andDo(print())
				.andExpect(status().isOk());

	}

	String accessToken = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJhbXNlcnZpY2VzIiwic3ViIjoic2FjaGltODg4Nzc3QGdtYWlsLmNvbSIsImV4cCI6MTcyMTEwOTA3NiwiaWF0IjoxNzIxMTA5MDE2LCJzY29wZSI6IlJFQUQifQ.posd7YmKqhKHIpN7JNjr9EPDqMz0K3McTgNM_xP6AanUk-jzKztAZbtoWJ-ruqlBEJ5u6rijzuH7aYkaxebHQp3DSXY4CbcxUVEJM4ZsXbpCwuLLA2GoB-FJBOUddbVE401Y8egN4Dry9-7DupqPTlw5ziISphgQm2AlVo7nRpoBxXHSTWUuUx5osDjiUVQtr7NT02aJIX6ctFG0R7v-uU7F2HWyQRK3pSDWaWNTtgaJVSzsD1VER3dwolinjhJeBqywuUWLl4x4b2lCKETGyVUI-Xc2LU9Uuwh5Hl8HHru4OmOgxo2HFSAKEu7ZkmeYkagnxl_4fauVENrNViEjGQ";

	@Test
	public void shouldAuthenticateAndReturnToken() throws Exception {
		mockMvc.perform(post("/sign-in")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"username\": \"sachim888777@gmail.com\", \"password\": \"829455\"}")
						.header("Authorization", "Basic " + encodedCredentials))
				.andDo(print())
				.andExpect(status().isOk());
	}
	@Test
	public void shouldAccessToken() throws Exception {
		mockMvc.perform(post("/api/get-user")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + accessToken))
				.andDo(print())
				.andExpect(status().isOk());
	}

}
