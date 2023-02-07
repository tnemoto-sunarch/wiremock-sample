package jp.co.sunarch.sample.service;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import java.io.File;
import java.io.FileInputStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;

@SpringBootTest
@WireMockTest(proxyMode = true)
public class FileDownloaderTest {

	@Autowired
	private FileDownloader service;

	@Test
	void test0() throws Exception {
		service.execute1();
	}

	@Test
	void test1() throws Exception {
		//テスト用ファイルの読み込み
		File testFile = new File("src/test/resources/efiboot.img");
		try (FileInputStream in = new FileInputStream(testFile)){
			byte[] body = new byte[(int)testFile.length()];
			in.read(body);
			// ファイル内容をモックに設定
			stubFor(get(anyUrl())
					.willReturn(aResponse().withBody(body).withStatus(200)));
		}
		service.execute1();
	}
	
	@Test
	void test2() throws Exception {
		//テスト用ファイルの読み込み
		File testFile = new File("src/test/resources/efiboot.img");
		try (FileInputStream in = new FileInputStream(testFile)){
			byte[] body = new byte[(int)testFile.length()];
			in.read(body);
			// ファイル内容をモックに設定
			stubFor(get(anyUrl())
					.willReturn(aResponse().withBody(body).withStatus(200)));
		}
		service.execute2();
	}

}
