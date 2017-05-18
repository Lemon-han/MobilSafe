// IOnNewBookArrivedListener.aidl
package com.emma.www.myapplication;

import com.emma.www.myapplication.Book;

interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book newBook);
}