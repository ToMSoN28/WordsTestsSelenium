import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.Random;

public class WordsAutomatedTest {

    private WebDriver driver;

    @BeforeEach
    public void driver_set_up() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
    }

    @AfterEach
    public void driver_close(){
        driver.quit();
    }

    @Test
    public void redirect_after_click_on_add_word_button() {
        String url = "http://localhost:8080/";
        driver.get(url);

        WebElement add_btn = driver.findElement(By.id("addWordButton"));
        add_btn.click();

        String current_url = driver.getCurrentUrl();
        String expected_url = "http://localhost:8080/add";

        Assertions.assertEquals(expected_url, current_url);
    }

    @Test
    public void correct_adding_word() {
        String url = "http://localhost:8080/add";
        driver.get(url);

        WebElement word_input = driver.findElement(By.id("addWordInputName"));
        WebElement definition_input = driver.findElement(By.id("addWordInputDefinition"));
        WebElement example_input = driver.findElement(By.id("addWordInputExample"));
        WebElement password_input = driver.findElement(By.id("addWordInputPassword"));

        word_input.clear();
        word_input.sendKeys("AZS");
        definition_input.clear();
        definition_input.sendKeys("Akademicki Związek Sportowy");
        example_input.clear();
        example_input.sendKeys("Gram w AZSie PW");
        password_input.clear();
        password_input.sendKeys("password");

        WebElement add_btn = driver.findElement(By.id("addWordSubmit"));
        add_btn.click();

        List<WebElement> azs_words_list = driver.findElements(By.xpath("//td[contains(text(), 'AZS')]"));
        Boolean correct_added;
        if (!azs_words_list.isEmpty()){
            correct_added = Boolean.TRUE;
        } else {
            correct_added = Boolean.FALSE;
        }
        Boolean expected = Boolean.TRUE;

        Assertions.assertEquals(expected, correct_added);
    }

    @Test
    public void redirect_after_click_on_edit_button() {
        String url = "http://localhost:8080/";
        driver.get(url);

        WebElement add_btn = driver.findElement(By.id("editButton-252"));
        add_btn.click();

        String current_url = driver.getCurrentUrl();
        String expected_url = "http://localhost:8080/edit/252";

        Assertions.assertEquals(expected_url, current_url);
    }

    @Test
    public void correct_edit_word() {
        String url = "http://localhost:8080/edit/252";
        driver.get(url);


        WebElement word_input = driver.findElement(By.id("${'editWordInputName-' + word.id}"));
        WebElement definition_input = driver.findElement(By.id("${'editWordInputDefinition-' + word.id}"));
        WebElement example_input = driver.findElement(By.name("example"));
        WebElement password_input = driver.findElement(By.id("${'editWordInputPassword-' + word.id}"));

        Random random = new Random();
        int losowaLiczba = random.nextInt(900) + 100;

        word_input.clear();
        word_input.sendKeys("edycja"+losowaLiczba);
        definition_input.clear();
        definition_input.sendKeys("test"+losowaLiczba);
        example_input.clear();
        example_input.sendKeys("automatyczny"+losowaLiczba);
        password_input.clear();
        password_input.sendKeys("password");

//        try {
//            Thread.sleep(5000); // Czas w milisekundach (10 sekund = 10000 milisekund)
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        WebElement update_btn = driver.findElement(By.id("${'editWordSubmit-' + word.id}"));
        update_btn.click();

        String searched_str = "edycja"+losowaLiczba;
        List<WebElement> azs_words_list = driver.findElements(By.xpath("//td[contains(text(), '"+searched_str+"')]"));
        Boolean correct_added;
        if (!azs_words_list.isEmpty()){
            correct_added = Boolean.TRUE;
        } else {
            correct_added = Boolean.FALSE;
        }
        Boolean expected = Boolean.TRUE;

        Assertions.assertEquals(expected, correct_added);
    }

    @Test
    public void correct_delete_word() {
        String url = "http://localhost:8080/";
        driver.get(url);

        WebElement azs_word = driver.findElement(By.xpath("//td[contains(text(), 'AZS')]"));
        String[] id_info = azs_word.getAttribute("id").split("-");
        String word_id = id_info[1];

        WebElement password_input = driver.findElement(By.id("passwordInput-"+word_id));
        password_input.clear();
        password_input.sendKeys("password");

        WebElement delete_btn = driver.findElement(By.id("deleteButton-"+word_id));
        delete_btn.click();

        try {
            Thread.sleep(300); // Czas w milisekundach (10 sekund = 10000 milisekund)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Boolean is_present;
        List<WebElement> word = driver.findElements(By.id("wordRow-"+word_id));
        if (word.size()>0) {
            is_present = Boolean.TRUE;
        } else {
            is_present = Boolean.FALSE;
        }

        Boolean expected = Boolean.FALSE;
        Assertions.assertEquals(expected, is_present);
    }

    @Test
    public void incorrect_adding_word_wrong_password() {
        String url = "http://localhost:8080/add";
        driver.get(url);

        WebElement word_input = driver.findElement(By.id("addWordInputName"));
        WebElement definition_input = driver.findElement(By.id("addWordInputDefinition"));
        WebElement example_input = driver.findElement(By.id("addWordInputExample"));
        WebElement password_input = driver.findElement(By.id("addWordInputPassword"));

        word_input.clear();
        word_input.sendKeys("AddingTest");
        definition_input.clear();
        definition_input.sendKeys("Incorrect");
        example_input.clear();
        example_input.sendKeys("Password");
        password_input.clear();
        password_input.sendKeys("qwerty1234");

        WebElement add_btn = driver.findElement(By.id("addWordSubmit"));
        add_btn.click();

        List<WebElement> azs_words_list = driver.findElements(By.xpath("//td[contains(text(), 'AddingTest')]"));
        Boolean correct_added;
        if (azs_words_list.isEmpty()){
            correct_added = Boolean.TRUE;
        } else {
            correct_added = Boolean.FALSE;
        }
        Boolean expected = Boolean.TRUE;

        Assertions.assertEquals(expected, correct_added);
    }

    @Test
    public void incorrect_adding_word_empty_password() {
        String url = "http://localhost:8080/add";
        driver.get(url);

        WebElement word_input = driver.findElement(By.id("addWordInputName"));
        WebElement definition_input = driver.findElement(By.id("addWordInputDefinition"));
        WebElement example_input = driver.findElement(By.id("addWordInputExample"));
        WebElement password_input = driver.findElement(By.id("addWordInputPassword"));

        word_input.clear();
        word_input.sendKeys("AddingTest");
        definition_input.clear();
        definition_input.sendKeys("Empty");
        example_input.clear();
        example_input.sendKeys("Password");
        password_input.clear();

        WebElement add_btn = driver.findElement(By.id("addWordSubmit"));
        add_btn.click();

        List<WebElement> azs_words_list = driver.findElements(By.xpath("//td[contains(text(), 'AddingTest')]"));
        Boolean correct_added;
        if (azs_words_list.isEmpty()){
            correct_added = Boolean.TRUE;
        } else {
            correct_added = Boolean.FALSE;
        }
        Boolean expected = Boolean.TRUE;

        Assertions.assertEquals(expected, correct_added);
    }

    @Test
    public void incorrect_edit_word_wrong_password(){
        String url = "http://localhost:8080/edit/252";
        driver.get(url);


        WebElement word_input = driver.findElement(By.id("${'editWordInputName-' + word.id}"));
        WebElement definition_input = driver.findElement(By.id("${'editWordInputDefinition-' + word.id}"));
        WebElement example_input = driver.findElement(By.name("example"));
        WebElement password_input = driver.findElement(By.id("${'editWordInputPassword-' + word.id}"));

        Random random = new Random();
        int losowaLiczba = random.nextInt(900) + 100;

        word_input.clear();
        word_input.sendKeys("edycja"+losowaLiczba);
        definition_input.clear();
        definition_input.sendKeys("wrong"+losowaLiczba);
        example_input.clear();
        example_input.sendKeys("password"+losowaLiczba);
        password_input.clear();
        password_input.sendKeys("qwerty1234");

        WebElement update_btn = driver.findElement(By.id("${'editWordSubmit-' + word.id}"));
        update_btn.click();

        String searched_str = "edycja"+losowaLiczba;
        List<WebElement> azs_words_list = driver.findElements(By.xpath("//td[contains(text(), '"+searched_str+"')]"));
        Boolean correct_added;
        if (azs_words_list.isEmpty()){
            correct_added = Boolean.TRUE;
        } else {
            correct_added = Boolean.FALSE;
        }
        Boolean expected = Boolean.TRUE;

        Assertions.assertEquals(expected, correct_added);
    }

    @Test
    public void incorrect_edit_word_empty_password(){
        String url = "http://localhost:8080/edit/252";
        driver.get(url);


        WebElement word_input = driver.findElement(By.id("${'editWordInputName-' + word.id}"));
        WebElement definition_input = driver.findElement(By.id("${'editWordInputDefinition-' + word.id}"));
        WebElement example_input = driver.findElement(By.name("example"));
        WebElement password_input = driver.findElement(By.id("${'editWordInputPassword-' + word.id}"));

        Random random = new Random();
        int losowaLiczba = random.nextInt(900) + 100;

        word_input.clear();
        word_input.sendKeys("edycja"+losowaLiczba);
        definition_input.clear();
        definition_input.sendKeys("empty"+losowaLiczba);
        example_input.clear();
        example_input.sendKeys("password"+losowaLiczba);
        password_input.clear();

        WebElement update_btn = driver.findElement(By.id("${'editWordSubmit-' + word.id}"));
        update_btn.click();

        String searched_str = "edycja"+losowaLiczba;
        List<WebElement> azs_words_list = driver.findElements(By.xpath("//td[contains(text(), '"+searched_str+"')]"));
        Boolean correct_added;
        if (azs_words_list.isEmpty()){
            correct_added = Boolean.TRUE;
        } else {
            correct_added = Boolean.FALSE;
        }
        Boolean expected = Boolean.TRUE;

        Assertions.assertEquals(expected, correct_added);
    }

    @Test
    public void incorrect_delete_word_wrong_password() {
        String url = "http://localhost:8080/";
        driver.get(url);

        WebElement azs_word = driver.findElement(By.xpath("//td[contains(text(), 'AZS')]"));
        String[] id_info = azs_word.getAttribute("id").split("-");
        String word_id = id_info[1];

        WebElement password_input = driver.findElement(By.id("passwordInput-"+word_id));
        password_input.clear();
        password_input.sendKeys("qwerty1234");

        WebElement delete_btn = driver.findElement(By.id("deleteButton-"+word_id));
        delete_btn.click();

        try {
            Thread.sleep(300); // Czas w milisekundach (10 sekund = 10000 milisekund)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Boolean is_present;
        List<WebElement> word = driver.findElements(By.id("wordRow-"+word_id));
        if (word.size()>0) {
            is_present = Boolean.TRUE;
        } else {
            is_present = Boolean.FALSE;
        }

        Boolean expected = Boolean.TRUE;
        Assertions.assertEquals(expected, is_present);
    }

    @Test
    public void incorrect_delete_word_empty_password() {
        String url = "http://localhost:8080/";
        driver.get(url);

        WebElement azs_word = driver.findElement(By.xpath("//td[contains(text(), 'AZS')]"));
        String[] id_info = azs_word.getAttribute("id").split("-");
        String word_id = id_info[1];

        WebElement password_input = driver.findElement(By.id("passwordInput-"+word_id));
        password_input.clear();

        WebElement delete_btn = driver.findElement(By.id("deleteButton-"+word_id));
        delete_btn.click();

        try {
            Thread.sleep(300); // Czas w milisekundach (10 sekund = 10000 milisekund)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Boolean is_present;
        List<WebElement> word = driver.findElements(By.id("wordRow-"+word_id));
        if (word.size()>0) {
            is_present = Boolean.TRUE;
        } else {
            is_present = Boolean.FALSE;
        }

        Boolean expected = Boolean.TRUE;
        Assertions.assertEquals(expected, is_present);
    }

    @Test
    public void redirect_from_adding_to_list_after_click_on_beck_button() {
        String url = "http://localhost:8080/add";
        driver.get(url);

        WebElement add_btn = driver.findElement(By.id("addWordBackButton"));
        add_btn.click();

        String current_url = driver.getCurrentUrl();
        String expected_url = "http://localhost:8080/";

        Assertions.assertEquals(expected_url, current_url);
    }

    @Test
    public void redirect_from_edit_to_list_after_click_on_beck_button() {
        String url = "http://localhost:8080/edit/252";
        driver.get(url);

        WebElement add_btn = driver.findElement(By.id("${'editWordBackButton-' + word.id}"));
        add_btn.click();

        String current_url = driver.getCurrentUrl();
        String expected_url = "http://localhost:8080/";

        Assertions.assertEquals(expected_url, current_url);
    }

    @Test
    public void redirect_from_search_to_list_after_click_on_beck_button() {
        String url = "http://localhost:8080/search?name=a";
        driver.get(url);

        WebElement add_btn = driver.findElement(By.id("searchResultsBackButton"));
        add_btn.click();

        String current_url = driver.getCurrentUrl();
        String expected_url = "http://localhost:8080/";

        Assertions.assertEquals(expected_url, current_url);
    }

    @Test
    public void correct_redirect_after_search_word() {
        String url = "http://localhost:8080/";
        driver.get(url);

        WebElement search_input = driver.findElement(By.id("searchInput"));
        search_input.clear();
        search_input.sendKeys("Kalkulator");

        WebElement search_btn = driver.findElement(By.id("searchButton"));
        search_btn.click();

        String current_url = driver.getCurrentUrl();
        String expected_url = "http://localhost:8080/search?name=Kalkulator";

        Assertions.assertEquals(expected_url, current_url);
    }

    @Test
    public void search_word_with_empty_word() {
        String url = "http://localhost:8080/";
        driver.get(url);

        WebElement search_input = driver.findElement(By.id("searchInput"));
        search_input.clear();

        WebElement search_btn = driver.findElement(By.id("searchButton"));
        search_btn.click();

        List<WebElement> rows = driver.findElements(By.cssSelector("#searchResultsTableBody tr"));
        int rows_number = rows.size();
        int expected = 0;

        Assertions.assertEquals(expected, rows_number);
    }

    @Test
    public void search_word_with_not_matched_word() {
        String url = "http://localhost:8080/";
        driver.get(url);

        WebElement search_input = driver.findElement(By.id("searchInput"));
        search_input.clear();
        search_input.sendKeys("Kraken");

        WebElement search_btn = driver.findElement(By.id("searchButton"));
        search_btn.click();

        List<WebElement> rows = driver.findElements(By.cssSelector("#searchResultsTableBody tr"));
        int rows_number = rows.size();
        int expected = 0;

        Assertions.assertEquals(expected, rows_number);
    }

    @Test
    public void search_word_with_one_matched_word() {
        String url = "http://localhost:8080/";
        driver.get(url);

        WebElement search_input = driver.findElement(By.id("searchInput"));
        search_input.clear();
        search_input.sendKeys("edycja");

        WebElement search_btn = driver.findElement(By.id("searchButton"));
        search_btn.click();

        List<WebElement> rows = driver.findElements(By.cssSelector("#searchResultsTableBody tr"));

        Boolean many_rows;
        if (rows.size()==1) {
            many_rows = Boolean.TRUE;
        } else {
            many_rows = Boolean.FALSE;
        }

        Boolean expected = Boolean.TRUE;
        Assertions.assertEquals(expected, many_rows);
    }

    @Test
    public void search_word_with_meny_matched_word() {
        String url = "http://localhost:8080/";
        driver.get(url);

        WebElement search_input = driver.findElement(By.id("searchInput"));
        search_input.clear();
        search_input.sendKeys("a");

        WebElement search_btn = driver.findElement(By.id("searchButton"));
        search_btn.click();

        List<WebElement> rows = driver.findElements(By.cssSelector("#searchResultsTableBody tr"));

        Boolean many_rows;
        if (rows.size()>1) {
            many_rows = Boolean.TRUE;
        } else {
            many_rows = Boolean.FALSE;
        }

        Boolean expected = Boolean.TRUE;
        Assertions.assertEquals(expected, many_rows);
    }




}
