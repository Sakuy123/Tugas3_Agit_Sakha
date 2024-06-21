package id.co.astratech.tugasagit_sakha;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitController {
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/publish")
    public String publishMessage(@RequestBody String message) {
        rabbitTemplate.convertAndSend("sakhaExchange", "sakha061", message);
        return "Message sent to RabbitMQ: " + message;
    }

    @RabbitListener(queues = "sakhaQueue")
    public void processMessage(String message) {
        System.out.println("Pesan dari saka: " + message);
        // Lakukan pemrosesan pesan di sini sesuai kebutuhan Anda
    }
}
