package com.example.projectpas;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class EncapField implements Parcelable {

    private String imageBadge;
    private String Name;
    private String firstEvent;
    private String LeagueAlternate;
    private String Sports;
    private String Country;
    private String DescEN;

    protected EncapField(Parcel in) {
        imageBadge = in.readString();
        Name = in.readString();
        firstEvent = in.readString();
        LeagueAlternate = in.readString();
        Sports = in.readString();
        Country = in.readString();
        DescEN = in.readString();
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

    public String getImageBadge() {
        return imageBadge;
    }

    public void setImageBadge(String imageBadge) {
        this.imageBadge = imageBadge;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getFirstEvent() {
        return firstEvent;
    }

    public void setFirstEvent(String firstEvent) {
        this.firstEvent = firstEvent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(imageBadge);
        dest.writeString(Name);
        dest.writeString(firstEvent);
        dest.writeString(LeagueAlternate);
        dest.writeString(Sports);
        dest.writeString(Country);
        dest.writeString(DescEN);
    }

    public String getLeagueAlternate() {
        return LeagueAlternate;
    }

    public void setLeagueAlternate(String leagueAlternate) {
        LeagueAlternate = leagueAlternate;
    }

    public String getSports() {
        return Sports;
    }

    public void setSports(String sports) {
        Sports = sports;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getDescEN() {
        return DescEN;
    }

    public void setDescEN(String descEN) {
        DescEN = descEN;
    }
}
