package fhnw.aalm_order;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitUntilState;

import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class PlaywrightApp {

    public static void main() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.webkit().launch( new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(300));
            Page page = browser.newPage();

            System.out.println("Opening http://localhost:8081");
            page.navigate("http://localhost:8081", new Page.NavigateOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED));

            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get("01_search.png")));

            System.out.println("Search for 'Harry'");
            Locator search = page.locator("#search-input");
            assertThat(search).hasAttribute("type", "text");
            search.fill("Harry");

            Locator button = page.locator("#search-submit");
            button.click();


            System.out.println("List result table of query 'Harry'");
            Locator resultsTable = page.locator("table tbody tr");
            assertThat(resultsTable).hasCount(3);

            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get("02_search-harry.png")));

            Locator harryBook = page.locator("tr").filter(
                    new Locator.FilterOptions().setHasText("Harry")
            ).first();
            assertThat(harryBook).isVisible();

            System.out.println("Adding first book of query 'Harry'");
            harryBook.locator("input[value='Add to cart']").click();


            System.out.println("Search for 'Tolkien tower'");
            search.fill("Tolkien tower");
            button.click();

            assertThat(resultsTable).hasCount(1);
            System.out.println("List result table of query 'Tolkien tower'");
            Locator tlotrBook = page.locator("tr").filter(
                    new Locator.FilterOptions().setHasText("Tolkien").setHasText("tower")
            ).first();

            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get("03_search-tolkien.png")));
            assertThat(tlotrBook).isVisible();

            tlotrBook.locator("input[value='Add to cart']").click();

            System.out.println("Go to cart");
            page.locator("text=Go to Cart").click();


            Locator cartRows = page.locator("table tbody tr");

            System.out.println("List cart table");

            Locator firstBook = cartRows.nth(0);
            Locator secondBook = cartRows.nth(1);

            assertThat(firstBook.locator("td").nth(2)).containsText("Harry");
            assertThat(secondBook.locator("td").nth(1)).containsText("Tolkien");
            assertThat(secondBook.locator("td").nth(2)).containsText("Towers");
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get("04_cart.png")));

            System.out.println("Removing first book");
            Locator remove = firstBook.locator("td").nth(4).locator("input[type='submit']");
            remove.click();

            cartRows = page.locator("table tbody tr");

            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get("05_cart-remove.png")));
            assertThat(cartRows).hasCount(1);
        }

        System.out.println("Everything completed");
    }
}
