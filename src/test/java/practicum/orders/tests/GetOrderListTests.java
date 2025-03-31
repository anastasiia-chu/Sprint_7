package practicum.orders.tests;

import io.qameta.allure.junit4.DisplayName;


import io.restassured.response.ValidatableResponse;

import org.junit.Before;
import org.junit.Test;

import practicum.orders.OrderClient;

import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrderListTests {
    @Before

    @Test
    @DisplayName("Получить список заказов")
    public void getOrdersListTest() {
        OrderClient orderClient = new OrderClient();
        ValidatableResponse responseOrderList = orderClient.getOrderList();
        responseOrderList.statusCode(200).and().body("orders", notNullValue());
    }
}
