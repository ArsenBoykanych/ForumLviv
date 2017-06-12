package aboikanych.forumlviv.net.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

/**
 * Created by Boykanych on 05.06.2017.
 */

public class ShopService implements Parcelable, SearchSuggestion {

    public String description;
    public String hoursFrom;
    public String hoursTo;
    public String imgUrl;
    public String title;
    public String webLink;
    public String phone;
    public String category;

    public ShopService() {
    }

    public ShopService(String title) {
        this.title = title;
    }

    public ShopService(String title, String imgUrl) {
        this.title = title;
        this.imgUrl = imgUrl;
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

    public String getCategory() {
        return category;
    }

    @Override
    public String getBody() {
        return title;
    }

    @Override
    public String toString() {
        return title;
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
        dest.writeString(this.category);
    }

    protected ShopService(Parcel in) {
        this.description = in.readString();
        this.hoursFrom = in.readString();
        this.hoursTo = in.readString();
        this.imgUrl = in.readString();
        this.title = in.readString();
        this.webLink = in.readString();
        this.phone = in.readString();
        this.category = in.readString();
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
