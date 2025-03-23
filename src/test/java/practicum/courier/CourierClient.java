package practicum.courier;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;
import java.util.Map;

import static io.restassured.RestAssured.given;


public class CourierClient {
    private static final String COURIER = "courier";


    @Step("Логин курьера")
    public Response loginCourier(Courier courier){
        return given().log().all()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post("/courier/login");
    }

    @Step("Создать курьера")
    public Response createCourier(Courier courier){
        return given().log().all()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post(COURIER);

    }

    @Step("Успешный логин")
    public int loginSuccess(ValidatableResponse loginResponse) {
        int id = loginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract()
                .path("id")
                ;
        return id;
    }

    @Step("Удалить курьера")
    public Response deleteCourier(int id) {
        return given().log().all()
                .body(Map.of("id", id))
                .when()
                .post(COURIER + "/" + id);

    }
}

