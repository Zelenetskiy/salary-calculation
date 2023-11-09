package ru.zelenetskiy.test.salarycalculation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.zelenetskiy.test.salarycalculation.model.Person;
import ru.zelenetskiy.test.salarycalculation.service.IPersonService;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

/**
 * Класс для наполнения таблицы Сотрудников тестовыми данными при инициализации
 */
@Component
public class InitData {
    private IPersonService personService;
    @Value("${spring.jpa.hibernate.ddl-auto}")
    String ddl_auto;

    /**
     * связывание контроллера с сервисом через конструктор
     *
     * @param personService Интерфейс сервиса
     */
    @Autowired
    public void setUserService(IPersonService personService) {
        this.personService = personService;
    }

    /**
     * наполнение таблицы Сотрудников тестовыми данными при инициализации
     */
    @PostConstruct
    public void loadTestData() {
        if (ddl_auto.equals("create-drop") || ddl_auto.equals("create")) {
            personService.addPerson(new Person("Иван", LocalDate.of(2015, 2, 13), "EMPLOYEE", 35000));
            personService.addPerson(new Person("Петр", LocalDate.of(2018, 3, 24), "EMPLOYEE", 34000));
            personService.addPerson(new Person("Семен", LocalDate.of(2020, 6, 3), "EMPLOYEE", 37000));
            personService.addPerson(new Person("Андрей", LocalDate.of(2019, 12, 15), "EMPLOYEE", 36000));
            personService.addPerson(new Person("Василий", LocalDate.of(2014, 1, 28), "MANAGER", 42000));
            personService.addPerson(new Person("Роман", LocalDate.of(2010, 9, 14), "MANAGER", 45000));
            personService.addPerson(new Person("Борис", LocalDate.of(2022, 3, 9), "MANAGER", 43000));
            personService.addPerson(new Person("Сергей", LocalDate.of(2014, 7, 5), "SALESMAN", 47000));
            personService.addPerson(new Person("Владимир", LocalDate.of(2016, 11, 22), "SALESMAN", 49000));
            System.out.println("test data loading completed!");
        }
    }
}
