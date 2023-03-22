package com.example.postof.repository;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.example.postof.model.Address;

@Repository
public class SetupRepository {
	
	@Value("${setup.origin.url}")
	private String url;

	public List<Address> getFromOrigin() throws Exception {
		List<Address> resultList = new ArrayList<>();
		String result = "";
		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(new HttpGet(url))) {
					HttpEntity entity = response.getEntity();
					result = EntityUtils.toString(entity);
				}
		
		String[] resultSplited = result.split("\n");

		for (String currentLine : resultSplited) {
			String[] currentLineSplited = currentLine.split(",");
			
			resultList.add(Address.builder()
			.state(currentLineSplited[0])
			.city(currentLineSplited[1])
			.district(currentLineSplited[2])
			.zipcode(StringUtils.leftPad(currentLineSplited[3], 8, "0"))
			.street(currentLineSplited.length > 4 ? currentLineSplited[4] : null)
			.build());
		}
		
		return resultList;
		
	}
}
