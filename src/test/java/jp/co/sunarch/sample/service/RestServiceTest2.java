package jp.co.sunarch.sample.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;

@SpringBootTest
@WireMockTest(proxyMode = true)
public class RestServiceTest2 {

	@Autowired
	private RestService testService;
	
	private static final String RESPONSE = "test response.";

	@Test
	void test3() {
		// 別のクラスでstubFor使ってても関係ない
		String res = testService.execute();
		assertThat(res).isEqualTo(RESPONSE);
	}
}
