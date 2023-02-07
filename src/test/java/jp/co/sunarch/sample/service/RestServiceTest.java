package jp.co.sunarch.sample.service;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;

@SpringBootTest
@WireMockTest(proxyMode = true)
public class RestServiceTest {

	@Autowired
	private RestService testService;
	
	private static final String RESPONSE = "test response.";

	@Test
	void test1() {
		stubFor(get(anyUrl())
				.willReturn(aResponse().withBody(RESPONSE).withStatus(200)));

		String res = testService.execute();
		
		assertThat(res).isEqualTo(RESPONSE);
	}

	@Test
	void test2() {
		// １つずつstubForでセットしないとNGになる
		String res = testService.execute();
		assertThat(res).isEqualTo(RESPONSE);
	}
}
