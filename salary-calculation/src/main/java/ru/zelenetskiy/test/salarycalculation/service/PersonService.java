package ru.zelenetskiy.test.salarycalculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zelenetskiy.test.salarycalculation.model.*;
import ru.zelenetskiy.test.salarycalculation.repository.IPersonRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Сервис для реализации бизнес-логики проекта
 */
@Service
public class PersonService implements IPersonService {
    /**
     * Интерфейс репозитория сотрудника для взаимодействия с БД
     */
    private final IPersonRepository personRepository;
    /**
     * Интерфейс сервиса сотрудника для обращения к методам сервиса
     */
    private final IPersonService personService;

    /**
     * связывание сервиса с репозиторием имеет анатацию @Lazy для избежания циклических зависимостей
     *
     * @param personRepository
     * @param personService
     */
    @Autowired
    @Lazy
    public PersonService(IPersonRepository personRepository, IPersonService personService) {
        this.personRepository = personRepository;
        this.personService = personService;
    }

    @Override
    @Transactional
    public List<Person> getAllPersons() {
        List<Person> allPerson = personRepository.findAll();
        allPerson.sort(Comparator.comparing(Person::getId));
        return allPerson;
    }

    @Override
    @Transactional
    public void addPerson(Person person) {
        personRepository.save(person);
    }

    /**
     * возвращает стаж работы в годах
     *
     * @param dateEmployment дата трудоустройства
     * @param selectDate     выбранная дата
     * @return количество лет стажа
     */
    public static int getExperience(LocalDate dateEmployment, LocalDate selectDate) {
        return (int) ChronoUnit.YEARS.between(dateEmployment, selectDate);
    }

    @Override
    @Transactional
    public double getSalary(Person person, LocalDate selectDate) {
        Groups group = Groups.getGroupByName(person.getGroup());
        int yearsOfExperience = getExperience(person.getDateEmployment(), selectDate);
        int rateSalary = person.getRateSalary();
        int limitYearsOfExpirience = group.getLimitYearsOfExpirience();
        double percentBonus = group.getPercentBonus();
        double percentForSubordinates = group.getPercentForSubordinates();
        double salarySubordinates = personService.getSalarySubordinates(person, selectDate);

        int years = yearsOfExperience < limitYearsOfExpirience ? yearsOfExperience : limitYearsOfExpirience;
        return (rateSalary + (rateSalary * percentBonus * years) + percentForSubordinates * salarySubordinates);
    }

    @Override
    @Transactional
    public Person getPersonById(Long id) {
        return personRepository.getOne(id);
    }

    @Override
    public List<Person> getSubordinatesByPerson(Person person) {
        return person.getSubordinate();
    }

    @Override
    @Transactional
    public double getSalarySubordinates(Person person, LocalDate selectDate) {
        double salarySubordinates = 0;
        List<Person> subordinates = person.getSubordinate();
        for (Person subordinate : subordinates) {
            salarySubordinates += personService.getSalary(subordinate, selectDate);
            if (person.getGroup().equals(Groups.SALESMAN)) {
                salarySubordinates += personService.getSalarySubordinates(subordinate, selectDate);
            }
        }
        return salarySubordinates;
    }

    @Override
    @Transactional
    public double getSalaryAllPersons(List<Person> allPersons, LocalDate selectDate) {
        double salaryAllPersons = 0;
        for (Person person : allPersons) {
            salaryAllPersons += personService.getSalary(person, selectDate);
        }
        return salaryAllPersons;
    }

    @Override
    @Transactional
    public List<Person> getPersonsPossibleBoss() {
        List<Person> personsPossibleBoss = new ArrayList<>();
        List<Person> allPerson = personRepository.findAll();
        for (Person person : allPerson)
            if (!person.getGroup().equals(Groups.EMPLOYEE)) {
                personsPossibleBoss.add(person);
            }
        return personsPossibleBoss;
    }

    @Override
    @Transactional
    public List<Person> getListNewSubordinate(Long id) {
        List<Person> excludeListSubordinate = new ArrayList<>();
        List<Person> listAllPerson = personRepository.findAll();
        for (Person person : listAllPerson) {
            List<Person> subordinates = person.getSubordinate();
            for (Person subordinate : subordinates) {
                if(subordinate.getId().equals(id)){
                    excludeListSubordinate.add(person);
                }
            }
            excludeListSubordinate.addAll(subordinates); //Исключаем подчинённых, которые уже являются чьими то подчинёнными
        }
        listAllPerson.removeAll(excludeListSubordinate);
        listAllPerson.remove(personService.getPersonById(id)); //Исключаем самого себя
        return listAllPerson;
    }

    @Override
    @Transactional
    public void addSubordinate(Long id, Long idBoss) {
        Person boss = personService.getPersonById(idBoss);
        List<Person> listSubordinate = boss.getSubordinate();
        listSubordinate.add(personService.getPersonById(id));
        boss.setSubordinate(listSubordinate);
        personRepository.save(boss);
    }

}




