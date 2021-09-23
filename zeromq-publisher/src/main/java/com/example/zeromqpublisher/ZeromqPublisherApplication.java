package com.example.zeromqpublisher;

import io.insource.framework.annotation.EnableZmqPublisher;
import io.insource.framework.zeromq.SimpleMessageConverter;
import io.insource.framework.zeromq.ZmqTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableZmqPublisher
public class ZeromqPublisherApplication {
    @Autowired
    ZmqTemplate zmqTemplate;

    public static void main(String[] args) {
        SpringApplication.run(ZeromqPublisherApplication.class, args);
    }

    @Bean
    public ZmqTemplate zmqTemplate() {
        ZmqTemplate zmqTemplate = new ZmqTemplate();
        zmqTemplate.setTopic("events");
        zmqTemplate.setRoutingKey("MyEvent");
        zmqTemplate.setMessageConverter(new SimpleMessageConverter());
        return zmqTemplate;
    }

    //    @Override
//    public void run(String... args) throws Exception {
//         int massagenumber =0;
//        while (!Thread.currentThread().isInterrupted()){
//            String massge = "Massage publish - " + massagenumber++;
//            zmqTemplate.send(massge);
//            System.out.println(massge);
//            Thread.sleep(1000);
//        }
//    }
    @RestController
    @RequestMapping(value = "/publish")
    static
    class MessagePublishController {
        @Autowired
        ZmqTemplate template;
        @GetMapping
        public ResponseEntity<String> publish(){
            template.send("Hello The massage is published ");
            return ResponseEntity.ok().body("massage Published ");
        }
    }
}
