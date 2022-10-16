package com.example.proiectafacerielectronice;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class User implements Parcelable {
    @SerializedName("user_id")
    //@Expose
    private String id;
    @SerializedName("name")
    //@Expose
    private String name;
    @SerializedName("email")
    // @Expose
    private String email;
    @SerializedName("password")
    // @Expose
    private String password;
    @SerializedName("address")
    //@Expose
    private String address;

    public User() {
    }

    public User(String id,String name, String email, String password, String address) {
        this.id=id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
    }
    public User(String name, String email, String password,  String address) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
    }

    protected User(Parcel in) {
        id=in.readString();
        name = in.readString();
        email = in.readString();
        password = in.readString();
        address = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }


    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", address=" + address +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(address);

    }
}
