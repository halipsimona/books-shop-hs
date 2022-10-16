package com.example.proiectafacerielectronice;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class BookApi implements Parcelable {
    @SerializedName("book_id")
    //@Expose
    private Integer id;

    @SerializedName("title")
    //@Expose
    private String title;

    @SerializedName("author")
    // @Expose
    private String author;

    @SerializedName("details")
    // @Expose
    private String details;

    @SerializedName("price")
    //@Expose
    private Double price;

    @SerializedName("photo")
    //@Expose
    private String photo;

    public BookApi(Integer id, String title, String author, String details, Double price, String photo ) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.details = details;
        this.price = price;
        this.photo = photo;
    }

    public BookApi() {
    }

    protected BookApi(Parcel in) {
        id = in.readInt();
        title = in.readString();
        author = in.readString();
        details = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readDouble();
        }
    }

    public static final Creator<BookApi> CREATOR = new Creator<BookApi>() {
        @Override
        public BookApi createFromParcel(Parcel in) {
            return new BookApi(in);
        }

        @Override
        public BookApi[] newArray(int size) {
            return new BookApi[size];
        }
    };

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", details='" + details + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(details);
        dest.writeDouble(price);

    }
}
