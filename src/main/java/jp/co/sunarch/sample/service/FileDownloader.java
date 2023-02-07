package jp.co.sunarch.sample.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileDownloader {

	private final RestTemplate restTemplate;

	/**
	 * ファイルダウンロード機能①
	 * @throws URISyntaxException
	 */
	public void execute1() throws URISyntaxException {
		URI uri = new URI("http://mirror.centos.org/centos/7/os/x86_64/images/efiboot.img");
		ResponseEntity<byte[]> response = restTemplate.exchange(uri, HttpMethod.GET, null, byte[].class);
		printHashCode(response.getBody());
		log.info("length   : " + response.getBody().length);
	}

	/**
	 * ファイルダウンロード機能②
	 * @throws URISyntaxException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void execute2() throws URISyntaxException, FileNotFoundException, IOException {
		URI uri = new URI("http://mirror.centos.org/centos/7/os/x86_64/images/efiboot.img");
		ResponseExtractor<ResponseEntity<File>> responseExtractor = new LargeFileResponseExtractor();
		ResponseEntity<File> response = restTemplate.execute(uri, HttpMethod.GET, null, responseExtractor);
		printHashCode(getFileBytes(response.getBody()));
		log.info("length   : " + response.getBody().length());
	}
	
	/** ファイルの内容取得 */
	private byte[] getFileBytes(File file) throws FileNotFoundException, IOException {
		byte[] results = null;
		try(FileInputStream in = new FileInputStream(file)){
			// めんどいから適当・・・
			byte[] bytes = new byte[10 * 1024 * 1024];
			int len = in.read(bytes);
			if(len > 0) {
				results = new byte[len];
				System.arraycopy(bytes, 0, results, 0, len);
			}
		}
		return results;
	}
	
	/** バイトをハッシュ値にして表示 */
	private void printHashCode(byte[] bytes) {
		log.info("hash code: " + Arrays.deepHashCode(ArrayUtils.toObject(bytes)));
	}

	/**
	 * めんどいからインナークラス
	 * @author T.Nemoto
	 *
	 */
	@Getter
	@Setter
	@NoArgsConstructor
	class LargeFileResponseExtractor implements ResponseExtractor<ResponseEntity<File>> {
		private String filePath;

		public LargeFileResponseExtractor(String filePath) {
			this.filePath = filePath;
		}

		@Override
		public ResponseEntity<File> extractData(ClientHttpResponse response) throws IOException {
			File downloadFile = null;
			if (filePath == null) {
				downloadFile = File.createTempFile("download", null);
			} else {
				downloadFile = new File(filePath);
			}

			FileCopyUtils.copy(response.getBody(), new FileOutputStream(downloadFile));

			return ResponseEntity.status(response.getStatusCode()).headers(response.getHeaders()).body(downloadFile);
		}
	}
}
