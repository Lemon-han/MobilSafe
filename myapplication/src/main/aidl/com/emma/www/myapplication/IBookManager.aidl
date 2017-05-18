// IBookManager.aidl
package com.emma.www.myapplication;
import com.emma.www.myapplication.Book;

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
}
