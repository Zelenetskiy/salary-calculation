package ru.zelenetskiy.test.salarycalculation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zelenetskiy.test.salarycalculation.model.Person;

/**
 * Репозиторий для сущностей сотрудников для взаимодействия с БД
 */
@Repository
public interface IPersonRepository extends JpaRepository<Person, Long> {
}
