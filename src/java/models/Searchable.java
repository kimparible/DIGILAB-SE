package models;

import java.util.List;

public interface Searchable {
    List<Book> searchByTitle(String title);
    List<Book> searchByAuthor(String author);
    // ... other search methods definitions
}