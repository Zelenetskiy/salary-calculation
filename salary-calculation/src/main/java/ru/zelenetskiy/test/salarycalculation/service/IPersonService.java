package ru.zelenetskiy.test.salarycalculation.service;

import ru.zelenetskiy.test.salarycalculation.model.Person;

import java.time.LocalDate;
import java.util.List;

/**
 * Интерфейс для класса сервиса , добавляет более абстрактный уровень бизнес логике
 */
public interface IPersonService {
    /**
     * Метод получения списка всех сотрудников хранящихся в БД с сортировкой по ID
     */
    List<Person> getAllPersons();

    /**
     * Добавляет сотрудника в базу данных
     *
     * @param person сотрудник
     */
    void addPerson(Person person);

    /**
     * Метод вычисления заработной платы сотрудника
     *
     * @param person     сотрудник
     * @param selectDate выбранная дата
     */
    double getSalary(Person person, LocalDate selectDate);

    /**
     * получение Сотрудника по ID
     *
     * @param id ID сотрудника
     */
    Person getPersonById(Long id);

    /**
     * получение подчиненных сотрудника
     *
     * @param person сотрудник
     */
    List<Person> getSubordinatesByPerson(Person person);

    /**
     * Получение зарплаты всех подчиненных сотрудника
     *
     * @param person     сотрудник
     * @param selectDate выбранная дата
     */
    double getSalarySubordinates(Person person, LocalDate selectDate);

    /**
     * Получение зарплаты всех сотрудников
     *
     * @param allPersons список всех сотрудников
     * @param selectDate выбранная дата
     */
    double getSalaryAllPersons(List<Person> allPersons, LocalDate selectDate);

    /**
     * Получение списка сотрудников которые могут иметь подчиненных(MANGER и SALESMAN)
     */
    List<Person> getPersonsPossibleBoss();

    /**
     * Получение списка для добавления новых подчиненных
     * @param id сотрудник которому будет добавляться подчиненный
     * @return список сотрудников без начальника
     */
    List<Person> getListNewSubordinate(Long id);

    /**
     * добавление подчиненного
     * @param id ID подчиненного
     * @param idBoss ID начальника
     */
    void addSubordinate(Long id, Long idBoss);

}
