package models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Catalog implements Searchable {
    private List<Book> books;

    public Catalog() {
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    public void removeBook(Book book) {
        this.books.remove(book);
    }

    @Override
    public List<Book> searchByTitle(String title) {
        return this.books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> searchByAuthor(String author) {
        return this.books.stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

}