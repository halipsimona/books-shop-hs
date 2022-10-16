package com.example.proiectafacerielectronice;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class BooksUser implements Parcelable {
    @SerializedName("book_id")
    //@Expose
    private Integer bid;

    @SerializedName("bu_id")
    //@Expose
    private Integer buid;

    @SerializedName("user_id")
    //@Expose
    private String uid;

    public BooksUser() {
    }

    protected BooksUser(Parcel in) {
        bid = in.readInt();
        buid = in.readInt();
        uid = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (bid == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(bid);
        }
        if (buid == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(buid);
        }
        dest.writeString(uid);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BooksUser> CREATOR = new Creator<BooksUser>() {
        @Override
        public BooksUser createFromParcel(Parcel in) {
            return new BooksUser(in);
        }

        @Override
        public BooksUser[] newArray(int size) {
            return new BooksUser[size];
        }
    };

    @Override
    public String toString() {
        return "BooksUser{" +
                "bid=" + bid +
                ", buid=" + buid +
                ", uid='" + uid + '\'' +
                '}';
    }

    public BooksUser(Integer bid, Integer buid, String uid) {
        this.bid = bid;
        this.buid = buid;
        this.uid = uid;
    }

    public BooksUser(Integer bid, String uid) {
        this.bid = bid;
        this.uid = uid;
    }

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public Integer getBuid() {
        return buid;
    }

    public void setBuid(Integer buid) {
        this.buid = buid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
