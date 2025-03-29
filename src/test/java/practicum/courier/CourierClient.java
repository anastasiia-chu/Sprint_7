package practicum.courier;

import io.qameta.allure.Step;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static practicum.EnvConfig.BASE_URI;


public class CourierClient {

    private static final String COURIER = "/api/v1/courier";
    protected final String DELETE_COURIER = "api/v1/courier/";

    @Step("Создание курьера")
    public ValidatableResponse createCourier(Courier courier) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(courier)
                .when()
                .post(COURIER)
                .then().log().all();
    }

    @Step("Логин курьера")
    public ValidatableResponse loginCourier(Courier courier) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(courier)
                .when()
                .post(COURIER + "/login")
                .then().log().all();
    }

    @Step("Удалить курьера")
    public ValidatableResponse deleteCourier(int courierId) {
        String json = String.format("{\"id\": \"%d\"}", courierId);
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(json)
                .when()
                .delete(DELETE_COURIER + courierId)
                .then().log().all();

    }

    @Step("Успешный логин")
    public int loginSuccess(ValidatableResponse loginResponse) {
        return loginResponse.assertThat()
                .statusCode(200)
                .body("id", greaterThan(0))
                .extract()
                .path("id");
    }

}

