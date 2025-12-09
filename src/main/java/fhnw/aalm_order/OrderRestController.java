package fhnw.aalm_order;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestClient;

@Controller
public class OrderRestController {


    private final String BASE_URL = "http://localhost:8080/api/books";
    private final RestClient restClient = RestClient.builder().baseUrl(BASE_URL).build();

    @GetMapping
    public String clientAllBooks(Model model) {
        BookDto[] allBooks = restClient.get().retrieve().body(BookDto[].class);
        model.addAttribute("books", allBooks);
        return "search";
    }
}
