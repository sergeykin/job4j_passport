package ru.job4j.clientservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j.bdservice.model.Passport;

import java.util.List;
import java.util.UUID;

@RestController
public class PassportController {
    private final String url = "http://localhost:8090";
    @Autowired
    private RestTemplate rest;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    @PostMapping("/save")
    public ResponseEntity<Passport> create(@RequestBody Passport passport) {
        Passport rsl = rest.postForObject(url + "/save", passport, Passport.class);
        return new ResponseEntity<>(
                rsl,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody Passport passport) {
        rest.put(url + "/update/" + id, passport);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        rest.delete(url + "/update/" + id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/find")
    public ResponseEntity<List<Passport>> findallPassport() {

        List<Passport> passports = rest.exchange(
                url + "/find",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Passport>>() {
                }
        ).getBody();

        return new ResponseEntity<>(passports
                , passports != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("find/{series}")
    public ResponseEntity<List<Passport>> findBySeries(@PathVariable String series) {
        List<Passport> passports = rest.exchange(
                url + "/find/" + series,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Passport>>() {
                }
        ).getBody();
        return new ResponseEntity<>(passports
                , passports != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/unavaliabe")
    public ResponseEntity<List<Passport>> findUnavaliabe() {
        List<Passport> passports = rest.exchange(
                url + "/unavaliabe",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Passport>>() {
                }
        ).getBody();
        return new ResponseEntity<>(passports
                , passports != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/find-replaceable")
    public ResponseEntity<List<Passport>> findReplaceable() {
        List<Passport> passports = rest.exchange(
                url + "/find-replaceable",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Passport>>() {
                }
        ).getBody();
        return new ResponseEntity<>(passports
                , passports != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @Scheduled(fixedDelay = 10000)
    public void checkPassports() throws JsonProcessingException {
        System.out.println("Start checking");
        List<Passport> passports = rest.exchange(
                url + "/unavaliabe",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Passport>>() {
                }
        ).getBody();
        if (!passports.isEmpty()) {
            ObjectMapper objectMapper = new ObjectMapper();
            UUID uuid = UUID.randomUUID();
            var message = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(passports);
            System.out.println("unavaliabe in message " + uuid.toString() + " sending");
            this.sendOrder(uuid.toString(), message);
        }
    }

    @PostMapping("/unavaliabepasport")
    public void sendOrder(String msgId, String msg) {
        kafkaTemplate.send("unpassports", msgId, msg);
    }
}
