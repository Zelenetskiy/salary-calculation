package ru.zelenetskiy.test.salarycalculation.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс сотрудника
 */
@Table(name = "employees")
@Entity
@Data
@NoArgsConstructor
public class Person {
    /**
     * ID сотрудника
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    /**
     * Имя сотрудника
     */
    @Column(name = "name")
    private String name;
    /**
     * Дата трудоустройства
     */
    @Column(name = "date_employment")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateEmployment;
    /**
     * Тип группы сотрудника
     */
    @Column(name = "group_name")
    private String group;
    /**
     * Базовая ставка
     */
    @Column(name = "rate_salary")
    private int rateSalary;
    /**
     * Список подчиненных сотрудника
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "boss_subordinate", joinColumns = @JoinColumn(name = "id_boss"),
            inverseJoinColumns = @JoinColumn(name = "id_subordinate"))
    private List<Person> subordinate;

    /**
     * Получение списка подчиненных
     *
     * @return Список подчиненных или пустой список если их нет
     */
    public List<Person> getSubordinate() {
        return subordinate != null
                ? subordinate
                : new ArrayList<>();
    }

    /**
     * Конструктор для класса Person
     * @param name Имя сотрудника
     * @param dateEmployment Дата трудоустройства
     * @param group Тип группы
     * @param rateSalary Базовая ставка
     */
    public Person(String name, LocalDate dateEmployment, String group, int rateSalary) {
        this.name = name;
        this.dateEmployment = dateEmployment;
        this.group = group;
        this.rateSalary = rateSalary;
    }
}

