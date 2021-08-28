package ru.job4j.bdservice.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.bdservice.model.Passport;

import java.util.Date;
import java.util.List;

public interface PassportRepository extends CrudRepository<Passport, Integer> {
    public List<Passport> findBySeries(String series);

    public List<Passport> findByDateEndBefore(Date date);

    public List<Passport> findByDateEndBetween(Date dateStart, Date dateEnd);
}
