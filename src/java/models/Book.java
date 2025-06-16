package models;

public class Book {

    private int id;
    private String title;
    private String author;
    private String isbn;
    private int quantity;
    private String category;
    private String status;
    private int publicationYear;
    private String coverImage;
    private String description;

    // ➕ Tambahan: jumlah pinjaman aktif
    private int activeLoanCount = 0;

    // Default constructor
    public Book() {}

    public Book(int id, String title, String author, String isbn, int quantity,
                String category, String status, int publicationYear, String coverImage, String description) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.quantity = quantity;
        this.category = category;
        this.status = status;
        this.publicationYear = publicationYear;
        this.coverImage = coverImage;
        this.description = description;
    }

    // ===== Getters & Setters =====

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // ➕ Getter/Setter activeLoanCount
    public int getActiveLoanCount() {
        return activeLoanCount;
    }

    public void setActiveLoanCount(int activeLoanCount) {
        this.activeLoanCount = activeLoanCount;
    }

    // ✅ Perhitungan otomatis ketersediaan
    public boolean isAvailable() {
        return quantity > activeLoanCount;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", quantity=" + quantity +
                ", activeLoanCount=" + activeLoanCount +
                ", category='" + category + '\'' +
                ", status='" + status + '\'' +
                ", publicationYear=" + publicationYear +
                ", coverImage='" + coverImage + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
