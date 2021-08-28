package ru.job4j.bdservice.service;

import org.springframework.stereotype.Service;
import ru.job4j.bdservice.model.Passport;
import ru.job4j.bdservice.repository.PassportRepository;

import java.util.*;

@Service
public class ServicePassport {
    private final PassportRepository passportRepository;

    public ServicePassport(PassportRepository passportRepository) {
        this.passportRepository = passportRepository;
    }

    public Passport save(Passport passport) {
        return passportRepository.save(passport);
    }

    public void delete(Integer id) {
        passportRepository.deleteById(id);
    }

    public Iterable<Passport> findall() {
        return passportRepository.findAll();
    }

    public Optional<Passport> findById(Integer id) {
        return passportRepository.findById(id);
    }

    public List<Passport> findBySeries(String series) {
        return passportRepository.findBySeries(series);
    }

    public List<Passport> getAllUnavaliabe() {
        Date date = new Date();
        return passportRepository.findByDateEndBefore(date);
    }

    public List<Passport> getClosePassportNearThreeMonth() {
        Calendar datestart = new GregorianCalendar();
        Calendar dateend = new GregorianCalendar();
        dateend.add(Calendar.MONTH, 3);
        return passportRepository.findByDateEndBetween(datestart.getTime(), dateend.getTime());
    }
}
