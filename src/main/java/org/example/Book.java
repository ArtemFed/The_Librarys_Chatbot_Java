package org.example;

import java.util.ArrayList;

public class Book implements Comparable<Book> {
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
