package com.gridnine.testing;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Класс FlightFilterChain реализует паттерн Fluent Interface
 * для последовательного применения нескольких фильтров к списку полетов.
 * Порядок применения фильтров не гарантируется
 */
public class FlightFilterChain {
    private final Set<FlightFilter> filters = new HashSet<>();

    private List<Flight> flights;

    /**
     * Конструктор класса FlightFilterChain.
     *
     * @param flights Исходный список полетов, который будет фильтроваться.
     */
    public FlightFilterChain(List<Flight> flights) {
        this.flights = flights;
    }

    /**
     * Добавляет фильтр в цепочку фильтров.
     *
     * @param filter Фильтр, который нужно добавить в цепочку.
     * @return Текущий объект FlightFilterChain для возможности цепочки вызовов.
     */
    public FlightFilterChain addFilter(FlightFilter filter) {
        filters.add(filter);
        return this;
    }

    /**
     * Выполняет цепочку фильтров, последовательно применяя каждый фильтр к списку полетов.
     *
     * @return Отфильтрованный список полетов.
     */
    public List<Flight> filter() {
        for (FlightFilter filter : filters) {
            flights = filter.filter(flights);
        }
        return flights;
    }
}
