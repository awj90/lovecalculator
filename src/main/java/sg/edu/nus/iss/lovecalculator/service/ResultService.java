package sg.edu.nus.iss.lovecalculator.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import sg.edu.nus.iss.lovecalculator.model.Result;
import sg.edu.nus.iss.lovecalculator.repository.ResultRepository;

@Service
public class ResultService {
    
    @Autowired
    ResultRepository resultRepository;

    @Value("${love.calculator.url}")
    private String loveCalculatorUrl; // https://love-calculator.p.rapidapi.com/getPercentage

    // unlike the weather API, the developer key for this case is not 'provided' via the url, but in the HTTP request header
    @Value("${love.calculator.api.key}")
    private String loveCalculatorApiKey; 

    @Value("${love.calculator.api.host}")
    private String loveCalculatorApiHost; // love-calculator.p.rapidapi.com

    public Optional<Result> getResult(String sname, String fname) throws IOException {
        String fullUrl = 
        UriComponentsBuilder.fromUriString(loveCalculatorUrl)
                            .queryParam("sname", sname)
                            .queryParam("fname", fname)
                            .toUriString();

        RestTemplate restTemplate = new RestTemplate();
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", loveCalculatorApiKey);
        headers.set("X-RapidAPI-Host", loveCalculatorApiHost);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        
        ResponseEntity<String> resp = restTemplate.exchange(fullUrl, HttpMethod.GET,
                requestEntity, String.class);
        
        Result result = Result.jsonStringToJavaObject(resp.getBody());
        resultRepository.saveResult(result);

        if (result!=null) {
            return Optional.of(result);
        } else {
            return Optional.empty();
        }
    }

    public List<Result> getAllResults() throws IOException {
        return resultRepository.getAllResults();
    }
}
