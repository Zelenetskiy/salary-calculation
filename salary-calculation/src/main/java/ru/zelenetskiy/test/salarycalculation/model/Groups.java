package ru.zelenetskiy.test.salarycalculation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Группы сотрудников
 */
@AllArgsConstructor
@Getter
public enum Groups {
    EMPLOYEE("EMPLOYEE", 0.03, 10, 0),
    MANAGER("MANAGER", 0.05, 8, 0.005),
    SALESMAN("SALESMAN", 0.01, 35, 0.003);

    /**
     * Название группы
     */
    private String name;
    /**
     * процент надбавки к зарплате
     */
    private double percentBonus;
    /**
     * предельный стаж за который начисляется надбавка
     */
    private int limitYearsOfExpirience;
    /**
     * Процент надбавки за подчиненных
     */
    private double percentForSubordinates;

    /**
     * Получение типа группы по названию
     *
     * @param groupName Название группы
     * @return Тип группы
     */
    public static Groups getGroupByName(final String groupName) {
        for (Groups groups : Groups.values())
            if (groups.getName().equals(groupName))
                return groups;
        return null;
    }
}
