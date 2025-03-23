package practicum.orders.tests;

import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import practicum.EnvConfig;
import practicum.orders.Colors;
import practicum.orders.Order;
import practicum.orders.OrderClient;

import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;

@RunWith(Parameterized.class)
public class CreateOrderTests {
    private Order order;

    public CreateOrderTests(Order order) {
        this.order = order;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = EnvConfig.BASE_URL;
        RestAssured.filters(new AllureRestAssured());
    }

    @Parameterized.Parameters(name = "Тестовые данные")
    public static Object[][] getOrderParameters() {
        return new Object[][]{
                {new Order("Иван", "Иванов", "Москва", "2",
                        "89990000000", "2025-10-11", "Привезешь не серый-не возьму",
                        List.of(Colors.GRAY.name()), 6)},
                {new Order("Анна-франческа", "Попова", "Малые Деревньки", "4",
                        "+7 800 345 543 43", "2025-06-07",
                        "Я не знаю,что тут писать", List.of(Colors.BLACK.name()), 5)},
                {new Order("Вася", "Пупкин", "Moscow", "10",
                        "+7-999-999-99-99", "2022-07-25", "",
                        List.of(Colors.GRAY.name(),Colors.BLACK.name()), 2)},
                {new Order("Яков Педрос", "Альварес", "Спб", "1",
                        "+7-123-455-55-55", "2017-02-28",
                        "Могу принять только с 2:30 до 2:33", Collections.emptyList(), 8)}
        };
    }

    @Test
    @DisplayName("Тесты на создание заказа с разными параметрами")
    public void createOrderTest() {
        Response response = given().log().all()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post("/orders");
        response.then().log().all()
                .assertThat().body("track", Matchers.notNullValue()).and().statusCode(201);
    }

    @After
    public void cancelOrder(){
        OrderClient orderStep = new OrderClient();
        orderStep.cancelOrder(order.getTrack());
    }

}
