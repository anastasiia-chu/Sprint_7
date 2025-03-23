package practicum.orders.tests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import practicum.EnvConfig;
import practicum.orders.OrdersList;


import static io.restassured.RestAssured.given;

public class GetOrderListTests {
    @Before
    public void setUp() {
        RestAssured.baseURI = EnvConfig.BASE_URL;
    }

    @Test
    @DisplayName("Получить список заказов")
    public void getOrdersListTest(){
        given()
                .contentType(ContentType.JSON)
                .log().all()
                .get("/orders")
                        .then()
                .assertThat()
                .statusCode(200);
        OrdersList OrdersList = given()
                .contentType(ContentType.JSON)
                .log().all()
                .get("/orders")
                .body()
                .as(OrdersList.class);
        Assert.assertThat(OrdersList.getOrders(), Matchers.not(Matchers.empty()));
    }
}
