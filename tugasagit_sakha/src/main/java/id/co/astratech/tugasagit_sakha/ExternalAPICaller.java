package id.co.astratech.tugasagit_sakha;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class ExternalAPICaller {
    private final String EXTERNAL_API_URL = "https://reqres.in/api/users/2"; // URL dari external API

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/external")
    public ResponseEntity<String> callExternalAPI() {
        ResponseEntity<String> response = restTemplate.getForEntity(EXTERNAL_API_URL, String.class);
        String responseBody = response.getBody();

        // Mengirim respons dari external API ke RabbitMQ
        rabbitTemplate.convertAndSend("sakhaExchange", "sakha061", responseBody);

        return ResponseEntity.ok().body(responseBody);
    }
}
