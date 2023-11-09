package ru.zelenetskiy.test.salarycalculation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zelenetskiy.test.salarycalculation.model.Person;
import ru.zelenetskiy.test.salarycalculation.service.*;

import java.time.LocalDate;
import java.util.List;

/**
 * REST контроллер для обработки HTTP запросов
 */
@RestController
@RequestMapping("/rest")
public class PersonRestController {
    /**
     * Интерфейс сервиса для обращения к его методам
     */
    private IPersonService personService;

    /**
     * связывание контроллера с сервисом через конструктор
     *
     * @param personService Интерфейс сервиса
     */
    @Autowired
    public PersonRestController(IPersonService personService) {
        this.personService = personService;
    }

    /**
     * метод передает список всех сотрудников в ответ на GET запрос
     *
     * @return JSON список всех сотрудников и статус выполнения запроса
     */
    @GetMapping(value = "/main")
    public ResponseEntity<?> getAllPersons() {
        final List<Person> persons = personService.getAllPersons();
        return persons != null && !persons.isEmpty()
                ? new ResponseEntity<>(persons, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * метод добавляет нового сотрудника в ответ на POST запрос
     *
     * @param person новый сотрудник
     * @return JSON статус выполнения запроса
     */
    @PostMapping(value = "/addPerson")
    public ResponseEntity<?> addPerson(@RequestBody Person person) {
        personService.addPerson(person);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * метод передает зарплату сотрудника в ответ на GET запрос
     *
     * @param id         id сотрудника
     * @param selectDate выбранная дата
     * @return JSON зарплата сотрудников и статус выполнения запроса
     */
    @GetMapping(value = "/getSalary/{id}/{selectDate}")
    public ResponseEntity<?> getSalaryByIdAndSelectDate(@PathVariable(name = "id") long id,
                                                        @PathVariable(name = "selectDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate selectDate) {
        final double salary = personService.getSalary(personService.getPersonById(id), selectDate);
        return new ResponseEntity<>(salary, HttpStatus.OK);
    }

    /**
     * метод передает суммарную зарплату всех сотрудников в ответ на GET запрос
     *
     * @param selectDate выбранная дата
     * @return JSON зарплата всех сотрудников и статус выполнения запроса
     */
    @GetMapping(value = "/getSalaryAllPersons/{selectDate}")
    public ResponseEntity<?> getSalaryAllPersons(@PathVariable(name = "selectDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate selectDate) {
        final double salaryAllPersons = personService.getSalaryAllPersons(personService.getAllPersons(), selectDate);
        return new ResponseEntity<>(salaryAllPersons, HttpStatus.OK);
    }

    /**
     * метод передает список подчиненных сотрудника в ответ на GET запрос
     *
     * @param id ID сотрудника
     * @return JSON список подчиненных сотрудника и статус выполнения запроса
     */
    @GetMapping(value = "/getSubordinates/{id}")
    public ResponseEntity<?> getSubordinates(@PathVariable(name = "id") long id) {
        final List<Person> subordinates = personService.getSubordinatesByPerson(personService.getPersonById(id));
        return subordinates != null
                ? new ResponseEntity<>(subordinates, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * метод передает список сотрудников которые могут иметь подчиненных в ответ на GET запрос
     *
     * @return список сотрудников которые могут иметь подчиненных и статус выполнения запроса
     */
    @GetMapping(value = "/possibleboss")
    public ResponseEntity<?> getPersonsPossibleBoss() {
        final List<Person> possibleBoss = personService.getPersonsPossibleBoss();
        return possibleBoss != null && !possibleBoss.isEmpty()
                ? new ResponseEntity<>(possibleBoss, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * метод передает список сотрудников которые могут добавлены как подчиненные в ответ на GET запрос
     *
     * @param id ID сотрудника к которому будет добавляться подчиненный
     * @return
     */
    @GetMapping(value = "/possibleboss/{id}")
    public ResponseEntity<?> getListNewSubordinate(@PathVariable(name = "id") long id) {
        final List<Person> listNewSubordinate = personService.getListNewSubordinate(id);
        return listNewSubordinate != null
                ? new ResponseEntity<>(listNewSubordinate, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * метод добавляет подчиненного в ответ на Put запрос
     * @param id ID добавляемого подчиненного
     * @param idBoss ID сотрудника которому добавляется подчиненный
     * @return
     */
    @PutMapping(value = "/addsubordinate/{id}/{idboss}")
    public ResponseEntity<?> addSubordinate(@PathVariable(name = "id") long id, @PathVariable(name = "idboss") long idBoss) {
        personService.addSubordinate(id, idBoss);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
