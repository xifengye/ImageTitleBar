package com.moregood.imagetitlebar;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * description :
 * author : yexifeng
 * email : ye_xi_feng@163.com
 * date : 2019/5/20 15:49
 */
class Data implements Parcelable {
    private String name;

    public Data(String hello) {
        this.name = hello;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
    }

    public Data() {
    }

    protected Data(Parcel in) {
        this.name = in.readString();
    }

    public static final Parcelable.Creator<Data> CREATOR = new Parcelable.Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel source) {
            return new Data(source);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };
}
