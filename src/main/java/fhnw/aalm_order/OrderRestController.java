package fhnw.aalm_order;

import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.awt.print.Book;
import java.util.Arrays;

@Controller
public class OrderRestController {

    private final RestClient restClient = RestClient.builder().build();

    private static final String SEARCH_URL = "http://localhost:8080/api/books/search?keyword=";

    @GetMapping("/")
    public String clientAllBooks(Model model) {

        BookDto[] allBooks = restClient.get()
                .uri(SEARCH_URL)   // No keywords, backend returns all
                .retrieve()
                .body(BookDto[].class);

        model.addAttribute("searchResult", allBooks);
        return "search";
    }

    @PostMapping()
    public String clientSearchBooks(@RequestParam String keyword, Model model) {
        String[] keywords = keyword.split(" ");
        String finalUrl = SEARCH_URL;

        for (String key: keywords) {
            finalUrl += key + "&keyword=";
        }

        BookDto[] search = restClient.get()
                .uri(finalUrl)
                .retrieve()
                .body(BookDto[].class);

        model.addAttribute("searchResult", search);

        return "search";
    }

    @PostMapping("/cart/add")
    public String bookToCart(@RequestParam String isbn, Model model) {
        String finalUrl = SEARCH_URL + isbn;
        BookDto[] book = restClient.get().uri(finalUrl).retrieve().body(BookDto[].class);

        assert book != null;
        System.out.println(book[0]);
        return "redirect:/";
    }
}

