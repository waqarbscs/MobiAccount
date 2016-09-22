package com.OneWindow.sol.MobiAccount.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by waqarbscs on 9/16/2016.
 */
public class Item_Subtype implements Parcelable {
    private int type;
    private String subType;

    public Item_Subtype() {
        type=0;
        subType="NA";
    }

    public void setInitialValues() {

        type=0;
        subType="NA";

    }
    public Item_Subtype(Parcel in) {
        type =  in.readInt();
        subType = in.readString();
    }

    public void setType(int i){ type=i; }
    public int getType(){ return type;}

    public void setSubType(String s){ subType=s; }
    public String getSubType(){ return subType; }


    public static final Parcelable.Creator<Item_Subtype> CREATOR = new Parcelable.Creator<Item_Subtype>() {
        @Override
        public Item_Subtype createFromParcel(Parcel in) {
            return new Item_Subtype(in);
        }

        @Override
        public Item_Subtype[] newArray(int size) {
            return new Item_Subtype[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(type);

        dest.writeString(subType);

    }

}
