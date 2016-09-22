package com.OneWindow.sol.MobiAccount.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by waqarbscs on 9/19/2016.
 */
public class Item implements Parcelable {

    private int id;
    private int type;
    private String subType;
    private String date;
    private Long amount;
    private String description;
    private String userId;

    public int getId(){return  id ;}
    public int getType(){return type;}
    public Long getAmount(){return amount;}
    public String getSubType(){return subType;}
    public String getDate(){return date;}
    public String getDescription(){return description;}
    public String getUserId(){ return  userId; }

    public void setId(int i){ id=i; }
    public void setType(int t){ type=t; }
    public void setSubType(String s){ subType = s ; }
    public void setAmount(Long a){ amount=a; }
    public void setDate(String d){ date=d; }
    public void setDescription(String d){ description = d; }
    public void setUserId(String u){ userId=u; }

    public Item() {
        id=0;
        type=0;
        subType="NA";
        date="NA";
        amount= Long.valueOf(0);
        description="NA";
        userId="NA";
    }

    public void setInitialValues() {
        id=0;
        type=0;
        subType="NA";
        date="NA";
        amount= Long.valueOf(0);
        description="NA";
        userId="NA";
    }
    public Item(Parcel in) {
        id=in.readInt();
        type =  in.readInt();
        subType = in.readString();
        date=in.readString();
        amount=in.readLong();
        description=in.readString();
        userId=in.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(type);
        dest.writeString(subType);
        dest.writeString(date);
        dest.writeLong(amount);
        dest.writeString(description);
        dest.writeString(userId);

    }
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
}
