package com.example.projectpas;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class EncapField implements Parcelable {

    private String image;
    private String Name;
    private String Data;

    protected EncapField(Parcel in) {
        image = in.readString();
        Name = in.readString();
        Data = in.readString();
    }

    EncapField(){

    }

    public static final Creator<EncapField> CREATOR = new Creator<EncapField>() {
        @Override
        public EncapField createFromParcel(Parcel in) {
            return new EncapField(in);
        }

        @Override
        public EncapField[] newArray(int size) {
            return new EncapField[size];
        }
    };

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeString(Name);
        dest.writeString(Data);
    }
}
