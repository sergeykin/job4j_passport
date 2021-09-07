package ru.job4j.mailservice;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
public class MailserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailserviceApplication.class, args);
    }

    @KafkaListener(topics= {"unpassports"})
    public void msgListener(ConsumerRecord<String, String> input){
        System.out.println("accepting " + input.key());
        System.out.println(input.value());
    }
}
