package practicum.courier.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import practicum.EnvConfig;
import practicum.courier.Courier;
import practicum.courier.CourierClient;

public class CourierLoginTests {
    @Before
    public void setUp() {
        RestAssured.baseURI = EnvConfig.BASE_URL;
    }

    @Test
    @DisplayName("Уданчый вход курьера")
    @Description("Проверяем статус код с корректными данными для входа")
    public void authorizationTest() {
        CourierClient clientStep = new CourierClient();
        Courier courier = new Courier();
        courier.setLogin("ninja1453");
        courier.setPassword("1234");
        Response response = clientStep.loginCourier(courier);
        response.then().log().all()
                .assertThat().body("id", Matchers.notNullValue()).and().statusCode(200);

    }

    @Test
    @DisplayName("Попытка входа без ввода логина")
    @Description("Проверяем статус код при попытке входа без логина")
    public void authorizationWithoutLoginTest() {
        CourierClient clientStep = new CourierClient();
        Courier courier = new Courier();
        courier.setPassword("1234");
        Response response = clientStep.loginCourier(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.is("Недостаточно данных для входа")).
                and().statusCode(400);

    }

    @Test
    @DisplayName("Попытка входа без ввода пароля")
    @Description("Проверяем статус код при попытке входа без пароля")
    public void authorizationWithoutPasswordTest() {
        CourierClient clientStep = new CourierClient();
        Courier courier = new Courier();
        courier.setLogin("ninja1453");
        Response response = clientStep.loginCourier(courier);
        response.then().log().all()
                .assertThat().statusCode(400).and().body("message", Matchers.is("Недостаточно данных для входа"));

    }

    @Test
    @DisplayName("Попытка входа с некорректным паролем")
    @Description("Проверяем статус код при попытке входа с некорректным паролем")
    public void authorizationWithWrongPasswordTest() {
        CourierClient clientStep = new CourierClient();
        Courier courier = new Courier();
        courier.setLogin("ninja1453");
        courier.setPassword("5555");
        Response response = clientStep.loginCourier(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.is("Учетная запись не найдена")).and().statusCode(404);

    }

    @Test
    @DisplayName("Попытка входа с некорректным логином")
    @Description("Проверяем статус код при попытке входа с некорректным логином")
    public void authorizationWithWrongLoginTest() {
        CourierClient clientStep = new CourierClient();
        Courier courier = new Courier();
        courier.setLogin("ninja5555!");
        courier.setPassword("1234");
        Response response = clientStep.loginCourier(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.is("Учетная запись не найдена")).and().statusCode(404);

    }

    @Test
    @DisplayName("Попытка входа с некорректным логином и паролем")
    @Description("Проверяем статус код при попытке входа с некорректным логином и паролем")
    public void authorizationWithWrongLoginAndPasswordTest() {
        CourierClient clientStep = new CourierClient();
        Courier courier = new Courier();
        courier.setLogin("werdvdefe1235dfbgngn");
        courier.setPassword("werdvdefe1235dfbgngn");
        Response response = clientStep.loginCourier(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.is("Учетная запись не найдена")).and().statusCode(404);
    }

}
