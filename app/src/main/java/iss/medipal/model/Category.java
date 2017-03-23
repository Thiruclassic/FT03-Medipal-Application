package iss.medipal.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Thirumal on 2/20/2017.
 */

public class Category implements Parcelable {
    int id;
    String code;
    String category;
    String description;
    boolean remind;

    protected Category(Parcel in)
    {
        id = in.readInt();
        code = in.readString();
        category=in.readString();
        description = in.readString();
        remind = in.readByte()!=0;
    }

    public Category()
    {

    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRemind() {
        return remind;
    }

    public void setRemind(boolean remind) {
        this.remind = remind;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeString(code);
        parcel.writeString(category);
        parcel.writeString(description);
        parcel.writeByte((byte)(remind?1:0));
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    @Override
    public boolean equals(Object object)
    {
        if(object instanceof Category) {
        if(this.getId()==((Category) object).getId())
        {
            return true;
        }
        }
        return false;
    }
}
