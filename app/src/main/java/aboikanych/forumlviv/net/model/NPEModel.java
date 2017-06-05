package aboikanych.forumlviv.net.model;

import android.os.Parcel;
import android.os.Parcelable;

public class NPEModel implements Parcelable {

    public String dateFrom;
    public String datePublish;
    public String dateTo;
    public String description;
    public String imgUrl;
    public String title;
    public String type;
    public String publisherId;

    public NPEModel() {
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public String getDatePublish() {
        return datePublish;
    }

    public String getDateTo() {
        return dateTo;
    }

    public String getDescription() {
        return description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getPublisherId() {
        return publisherId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.dateFrom);
        dest.writeString(this.datePublish);
        dest.writeString(this.dateTo);
        dest.writeString(this.description);
        dest.writeString(this.imgUrl);
        dest.writeString(this.title);
        dest.writeString(this.type);
        dest.writeString(this.publisherId);
    }

    protected NPEModel(Parcel in) {
        this.dateFrom = in.readString();
        this.datePublish = in.readString();
        this.dateTo = in.readString();
        this.description = in.readString();
        this.imgUrl = in.readString();
        this.title = in.readString();
        this.type = in.readString();
        this.publisherId = in.readString();
    }

    public static final Creator<NPEModel> CREATOR = new Creator<NPEModel>() {
        @Override
        public NPEModel createFromParcel(Parcel source) {
            return new NPEModel(source);
        }

        @Override
        public NPEModel[] newArray(int size) {
            return new NPEModel[size];
        }
    };
}
