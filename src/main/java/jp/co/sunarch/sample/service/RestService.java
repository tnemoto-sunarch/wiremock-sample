package jp.co.sunarch.sample.service;

import java.net.URI;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestService {
	
	private final RestTemplate restTemplate;

	public String execute() {
		String response = null;
		try {
			URI uri = new URI("http://www.tohoho-web.com/docker/command.html");
			
			response = restTemplate.getForObject(uri, String.class);
			
			log.info(response);
		} catch(Exception e) {
			log.error(e.getMessage(), e);
		}
		return response;
	}
}
