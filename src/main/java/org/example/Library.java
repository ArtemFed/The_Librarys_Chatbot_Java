package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Library {
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
