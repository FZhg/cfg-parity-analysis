package ece654.fan.cfgparityanalysis;

public interface NumberLiteral<T> {
    T add(T other);
    T substract(T other);
    T divide(T other);
    T multiply(T other);
    T modulo(T other);
}
