package fhnw.aalm_order;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    List<BookDto> bookCart = new ArrayList<>();

    public void add(BookDto bookDto) {
        if (!bookCart.contains(bookDto)) bookCart.add(bookDto);
    }

    public List<BookDto> getBookCart() {
        return this.bookCart;
    }

    public void remove(BookDto bookDto) {
        bookCart.remove(bookDto);
    }

    public void clear() {
        bookCart.clear();
    }
}
