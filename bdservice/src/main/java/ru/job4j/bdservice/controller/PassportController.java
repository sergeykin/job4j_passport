package ru.job4j.bdservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.bdservice.model.Passport;
import ru.job4j.bdservice.service.ServicePassport;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController

public class PassportController {


    private final ServicePassport servicePassport;

    public PassportController(ServicePassport servicePassport) {
        this.servicePassport = servicePassport;
    }

    @PostMapping("/save")
    public ResponseEntity<Passport> create(@RequestBody Passport passport) {
        return new ResponseEntity<Passport>(
                this.servicePassport.save(passport),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> update(@PathVariable Integer id,@RequestBody Passport passport) {
        Optional<Passport> pasp = servicePassport.findById(id);
        if (pasp.isPresent()) {
            passport.setId(id);
            this.servicePassport.save(passport);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        this.servicePassport.delete(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteall() {
        this.servicePassport.deleteall();
        return ResponseEntity.ok().build();
    }
    @GetMapping("/find")
    public ResponseEntity<List<Passport>> findallPassport() {
        List<Passport> passports = StreamSupport.stream(servicePassport.findall()
                .spliterator(), false).collect(Collectors.toList());
        return new ResponseEntity<>(passports
                , passports != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("find/{series}")
    public ResponseEntity<List<Passport>> findBySeries(@PathVariable String series) {
        List<Passport> passports = servicePassport.findBySeries(series);
        return new ResponseEntity<>(passports
                , passports != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/unavaliabe")
    public ResponseEntity<List<Passport>> findUnavaliabe() {
        List<Passport> passports = servicePassport.getAllUnavaliabe();
        return new ResponseEntity<>(passports
                , passports != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/find-replaceable")
    public ResponseEntity<List<Passport>> findReplaceable() {
        List<Passport> passports = servicePassport.getClosePassportNearThreeMonth();
        return new ResponseEntity<>(passports
                , passports != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

}
