package com.example.multithreadingtest.service;

import com.example.multithreadingtest.model.SearchFilterDto;
import com.example.multithreadingtest.model.User;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * GitHubLookupService.
 *
 * @Description TODO
 * @Date 14/05/2022 0:58
 * @Created by Qinxiu Wang
 */
@Service
@Slf4j
public class GitHubLookupService {

  private final RestTemplate restTemplate;

  public GitHubLookupService(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.build();
  }

  @Async
  public CompletableFuture<User> findUser(String user) throws InterruptedException {
    log.info("Looking up " + user);
    String url = String.format("https://api.github.com/users/%s", user);
    User results = restTemplate.getForObject(url, User.class);
    // Artificial delay of 1s for demonstration purposes
//    Thread.sleep(1000L);
    return CompletableFuture.completedFuture(results);
  }

  @Async
  public CompletableFuture<String> postRequest(int index) throws URISyntaxException {
    log.info("post-request-" + index);
    final String baseUrl = "http://localhost:8081/commercial/incentiveRed";
    URI uri = new URI(baseUrl);

    String countryCode = "ESP";
    List<String> productCodes = Arrays.asList("BV", "T05", "T03", "BPC", "ACC");
    int fiscalYear = 2022;
    int sinceMonth = 4;
    int upToMonth = 4;
    int languageId = 1;
    SearchFilterDto filter = SearchFilterDto.builder()
        .fiscalYear(fiscalYear)
        .countryCode(countryCode)
        .productCodes(productCodes)
        .sinceMonth(sinceMonth)
        .upToMonth(upToMonth)
        .languageId(languageId).build();

    HttpHeaders headers = new HttpHeaders();
    headers.set("Content-Type", "application/json");
    headers.set("Authorization",
        "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjoiQVFDUFliRWJsSVFqVFNsRWl1SHAxbWdaeDhIUHFlb2lVR0dPcUIwMm9vemJESE9SRkFweTg1c2hNMyt6bVgzcmo2VjBTY1pmdkt1aW1EWGc5ZXV5UERTaEtmSkJ5clllL3FhU1JrWlJ6RXlydFJ1RjRUSVptNWtmY3ZMMmtjTTdJa3pHellkdWl6eWpmcW5HWTY4Z3ZWTTRodmRNU0xpVXRHWktIUDc5L1VYeHN0UUZsWlZIRDB1Y2dTZU44cHZzV01yWlA3MkxoTExWZnVnVHlTTndxWTJwUmJDakMzRUppK3ZNU3BNckZzZWpMMVVoQmgwZ09iS3pjbmhxcEhvamE5SDgvczlOZFdVMUZRdTZsYzRrV3VNYlJVZ0I2S2kwditXcW5wbnJubllKYmVEMlplMFU4TmcwSm9xMUpYQk12c2ZDR1ZsVUtsMDZEdWVBMDhnTGRscUpHZk9HUUJ4T2wwQy95UjhQVlZCd1NsSTdGKzM4M3M4VHlXcG5kN1U1VmRpVFNBY0x6VytKVjc3ZVM0bkVVenRFbGRnaklEVHdVS1hObVFYNFZrcnhqQkQvdllxSTMwT3F5Vlo2VzhCajgzND0iLCJleHAiOjE2NTI0OTE0MTAsImlhdCI6MTY1MjQ4NDIxMH0.2QjDtdWMS0e-kx0ciI5ry3_L5amlM2zN7Adiug6j9fI");

    HttpEntity<SearchFilterDto> request = new HttpEntity<>(filter, headers);
    ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
    return CompletableFuture.completedFuture(result.getBody());
  }


  @Async
  public CompletableFuture<String> postRequest(String aspm,String token) throws URISyntaxException {
    log.info("post-request-" + aspm);
    final String baseUrl = "http://localhost:8081/commercial/incentiveRed";
    URI uri = new URI(baseUrl);

    List<String> productCodes = Arrays.asList("BV", "T05", "T03", "BPC", "ACC");
    int fiscalYear = 2021;
    int sinceMonth = 4;
    int upToMonth = 8;
    int languageId = 1;
    SearchFilterDto filter = SearchFilterDto.builder()
        .fiscalYear(fiscalYear)
        .aspm(aspm)
        .productCodes(productCodes)
        .sinceMonth(sinceMonth)
        .upToMonth(upToMonth)
        .languageId(languageId).build();

    HttpHeaders headers = new HttpHeaders();
    headers.set("Content-Type", "application/json");
    headers.set("Authorization", token);

    HttpEntity<SearchFilterDto> request = new HttpEntity<>(filter, headers);
    ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
    return CompletableFuture.completedFuture(result.getBody());
  }
}