package org.example;

import java.util.*;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        Book book1, book2, book3;
        MyMap map;
        Library lib;
        // Подготовка и создание библиотеки
        {
            System.out.println("""
                    Доступны команды:
                    Команда “get <имя книги>”
                      - Если книг много - печатается список книг\s
                      - После чего пользователь вводит номер нужной
                    Команда “put <книга>” - возвращает книгу в библиотеку
                    Команда “list” - показывает, какие книги Вами уже взяты \s
                    Команда “all” - полный список книг и их количество \s
                    -------
                    Команда “end” - завершает работу с библиотекой"""
            );
            book1 = new Book(
                    "The Lord of the Rings | LOTR",
                    new ArrayList<>(Collections.singleton("Толкиен")),
                    1954
            );
            book2 = new Book(
                    "Dune",
                    new ArrayList<>() {
                        {
                            add("Ф. Герберт");
                            add("Б. Герберт");
                        }
                    },
                    1976
            );
            book3 = new Book(
                    "Brave New World",
                    new ArrayList<>(Collections.singleton("Хаксли")),
                    1932
            );

            map = new MyMap();
            map.put(book1, 2);
            map.put(book2, 1);
            map.put(book3, 0);

            lib = new Library(map);
        }

        String answer, name;
        Scanner input = new Scanner(System.in);

        System.out.print("Действие: ");
        answer = input.next().toLowerCase();
        while (!answer.equals("end")) {
            try {
                switch (answer) {
                    case "get" -> {
                        System.out.print("Название: ");
                        name = input.next();
                        lib.get(name);
                    }
                    case "put" -> {
                        System.out.print("Название: ");
                        name = input.next();
                        lib.put(name);
                    }
                    case "list" -> lib.list();
                    case "all" -> lib.all();
                    default -> System.out.println("Команда не распознана!");
                }
            } catch (Exception ignored) {
            }

            System.out.print("Действие: ");
            answer = input.next();
        }
    }
}