package practicum.orders;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class OrderClient {
    @Step("Отменить заказ")
    public Response cancelOrder(int track){
        return given().log().all()
                .body(Map.of("track", track))
                .when()
                .put("orders/cancel");

    }
}
