package org.example;

import java.util.*;
import java.util.function.Consumer;

class Book implements Comparable<Book> {
    String description;

    ArrayList<String> authorsList;
    Integer yearOfPublication;

    public Book(String description, ArrayList<String> authorsList, Integer yearOfPublication) {
        this.description = description;
        this.authorsList = authorsList;
        this.yearOfPublication = yearOfPublication;
    }

    @Override
    public int compareTo(Book o) {
        int cmpVal;
        cmpVal = description.compareTo(o.description);
        if (cmpVal != 0) {
            return cmpVal;
        }
        cmpVal = yearOfPublication.compareTo(o.yearOfPublication);
        if (cmpVal != 0) {
            return cmpVal;
        }
        for (int i = 0; i < Math.min(authorsList.size(), o.authorsList.size()); ++i) {
            cmpVal = authorsList.get(i).compareTo(o.authorsList.get(i));
            if (cmpVal != 0) {
                return cmpVal;
            }
        }
        return Integer.compare(authorsList.size(), o.authorsList.size());
    }
}

class Library {
    private final MyMap collection;
    private final MyMap onHandCollection;

    public Library(MyMap collections) {
        this.collection = collections;
        this.onHandCollection = new MyMap(collections);
    }

    public void get(String name) {
        System.out.println("Доступные книги: ");
        ArrayList<Book> books = printByName(name);
        if (books.size() == 0) {
            System.out.println("Книг с таким именем не обнаружено!");
            return;
        }
        System.out.print("Введите номер книги: ");
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt() - 1;

        var book = books.get(num);
        if (collection.get(book) - onHandCollection.get(book) <= 0) {
            System.out.printf("Книги %s нет в наличии!\n", book.description);
            return;
        }
        onHandCollection.put(book, onHandCollection.get(book) + 1);
        System.out.printf("Книга %s выдана! Осталось: %d\n",
                book.description, collection.get(book) - onHandCollection.get(book)
        );
    }

    public void put(String name) {
        System.out.println("Взятые книги: ");
        ArrayList<Book> books = printByName(name);
        Scanner input = new Scanner(System.in);
        if (books.size() == 0) {
            System.out.println("Книг с таким именем не обнаружено! Хотите отдать книгу в библиотеку? (мы вам не заплатим) (y/n)");
            String answer = input.next().toLowerCase();
            if (answer.equals("y")) {
                System.out.print("Год(yyyy): ");
                int year = input.nextInt();
                System.out.print("Авторы(через ', '): ");
                input.nextLine();
                String authorsStr = input.nextLine();
                Book book = new Book(name, new ArrayList<>(List.of(authorsStr.split(", "))), year);
                System.out.printf("Новая книга %s успешно добавлена! Спасибо!\n", book.description);
                collection.put(book, 1);
                onHandCollection.put(book, 0);
            }
            return;
        }
        System.out.print("Введите номер книги: ");
        var book = books.get(input.nextInt() - 1);
        System.out.printf("Книга %s успешно возвращена!\n", book.description);
        onHandCollection.put(book, collection.get(book) - 1);
    }

    private ArrayList<Book> printByName(String name) {
        ArrayList<Book> books = new ArrayList<>();
        int index = 0;
        for (Book book : collection) {
            if (book.description.toLowerCase().contains(name.toLowerCase())) {
                books.add(book);
                System.out.printf("  %d. %s. Год: %d. Автор(ы): %s - Кол-во: %d\n",
                        ++index, book.description, book.yearOfPublication, book.authorsList,
                        collection.get(book) - onHandCollection.get(book)
                );
            }
        }
        return books;
    }

    public void list() {
        int index = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("Все книги на руках:");
        for (Book book : collection) {
            if (onHandCollection.get(book) > 0) {
                sb.append(String.format("\n  %d. %s. Год: %d. Автор(ы): %s - Кол-во: %d",
                        ++index, book.description, book.yearOfPublication, book.authorsList,
                        onHandCollection.get(book)
                ));
            }
        }
        if (index > 0) {
            System.out.println(sb);
            return;
        }
        System.out.println("Все книги в библиотеке.");
    }

    public void all() {
        int index = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("Все книги библиотеки:");
        for (Book book : collection) {
            sb.append(String.format("\n  %d. %s. Год: %d. Автор(ы): %s - Кол-во: %d/%d",
                    ++index, book.description, book.yearOfPublication, book.authorsList,
                    collection.get(book) - onHandCollection.get(book), collection.get(book)
            ));
        }
        System.out.println(sb);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (var book : collection) {
            sb.append(String.format("\n№%d: %s : %d : %s : %d/%d", ++i,
                    book.description, book.yearOfPublication, book.authorsList.toString(),
                    onHandCollection.get(book), collection.get(book))
            );
        }
        return sb.toString();
    }
}

class MyMap extends HashMap<Book, Integer> implements Map<Book, Integer>, Iterable<Book> {
    private final HashMap<Book, Integer> map;

    MyMap() {
        map = new HashMap<>();
    }

    MyMap(MyMap original) {
        map = new HashMap<>();
        for (Map.Entry<Book, Integer> entry : original.entrySet()) {
            map.put(entry.getKey(), 0);
        }
    }

    public Integer put(Book key, Integer val) {
        return map.put(key, val);
    }

    public Integer get(Book key) {
        return map.get(key);
    }

    public boolean contains(Book book) {
        return map.containsKey(book);
    }

    public Set<Entry<Book, Integer>> entrySet() {
        return map.entrySet();
    }

    @Override
    public Iterator<Book> iterator() {
        return map.keySet().iterator();
    }

    @Override
    public void forEach(Consumer action) {
        Iterable.super.forEach(action);
    }
}

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