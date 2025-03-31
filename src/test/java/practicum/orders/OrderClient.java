package practicum.orders;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;

import io.restassured.response.ValidatableResponse;


import static io.restassured.RestAssured.given;
import static practicum.EnvConfig.BASE_URI;

public class OrderClient {

    private static final String ORDER = "api/v1/orders";
    private static final String CANCEL_ORDER = "api/v1/orders/cancel";


    @Step("Отменить заказ")
    public ValidatableResponse cancelOrder(int track) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .queryParam("track", track)
                .when()
                .put(CANCEL_ORDER)
                .then();
    }

    @Step("Получить список заказов")
    public ValidatableResponse getOrderList() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .when()
                .get(ORDER)
                .then();
    }

    @Step("Создать заказ")
    public ValidatableResponse createNewOrder(Order order) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(order)
                .when()
                .post(ORDER)
                .then();

    }
}
