package aboikanych.forumlviv.net.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Boykanych on 05.06.2017.
 */

public class ShopService implements Parcelable {

    public String description;
    public String hoursFrom;
    public String hoursTo;
    public String imgUrl;
    public String title;
    public String webLink;
    public String phone;

    public ShopService() {
    }

    public String getDescription() {
        return description;
    }

    public String getHoursFrom() {
        return hoursFrom;
    }

    public String getHoursTo() {
        return hoursTo;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getWebLink() {
        return webLink;
    }

    public String getPhone() {
        return phone;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.description);
        dest.writeString(this.hoursFrom);
        dest.writeString(this.hoursTo);
        dest.writeString(this.imgUrl);
        dest.writeString(this.title);
        dest.writeString(this.webLink);
        dest.writeString(this.phone);
    }

    protected ShopService(Parcel in) {
        this.description = in.readString();
        this.hoursFrom = in.readString();
        this.hoursTo = in.readString();
        this.imgUrl = in.readString();
        this.title = in.readString();
        this.webLink = in.readString();
        this.phone = in.readString();
    }

    public static final Creator<ShopService> CREATOR = new Creator<ShopService>() {
        @Override
        public ShopService createFromParcel(Parcel source) {
            return new ShopService(source);
        }

        @Override
        public ShopService[] newArray(int size) {
            return new ShopService[size];
        }
    };
}
