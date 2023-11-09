package ru.zelenetskiy.test.salarycalculation.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Класс конфигурации Spring MVC
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    /**
     * Добавляет ViewController для отображения данных полбзователю
     *
     * @param registry
     */
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/main").setViewName("main");
    }
}
