package com.android.practice;

import android.graphics.Bitmap;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.FileDescriptor;

/**
 * TODO
 *
 * @author dev.liang <a href="mailto:dev.liang@outlook.com">Contact me.</a>
 * @version 1.0.0
 * @since 2021/12/20 11:53 PM
 */
public class BitmapBinder extends Binder {
    private Bitmap mBitmap;

    public BitmapBinder(Bitmap bitmap) {
        this.mBitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }


}
