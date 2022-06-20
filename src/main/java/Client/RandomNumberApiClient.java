package Client;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

public class RandomNumberApiClient {
    private final RestTemplate restTemplate;

    public RandomNumberApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Async("asyncExecutor")
    public CompletableFuture<ResponseEntity<String>> sendRequest(int min, int max) throws InterruptedException {
        return CompletableFuture.completedFuture(restTemplate.getForEntity(makeUrl(String.valueOf(min), String.valueOf(max)), String.class));
    }

    private String makeUrl(String min, String max) {
        return "https://www.randomnumberapi.com/api/v1.0/random?min=" + min + "&max=" + max + "&count=1";
    }
}

