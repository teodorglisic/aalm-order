package fhnw.aalm_order;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;


@Service
@SessionScope
public class ShoppingCartService {

    private ShoppingCart shoppingCart = new ShoppingCart();

    public void addBookToCart(BookDto bookDto) {
        this.shoppingCart.add(bookDto);
    }

    public void removeBookFromCart(BookDto bookDto) {
        this.shoppingCart.remove(bookDto);
    }

    public void clearCart() {
        this.shoppingCart.clear();
    }

    public List<BookDto> getCart() {
        return this.shoppingCart.getBookCart();
    }
}
