package practicum.courier.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;

import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import practicum.courier.Courier;
import practicum.courier.CourierClient;
import practicum.courier.CourierRandom;

public class CourierLoginTests {
    private CourierRandom random = new CourierRandom();
    private CourierClient courierClient;
    private Courier courier;
    int courierId;

    @Before
    @Step("Предусловия для логина курьера")
    public void setUp() {
        courierClient = new CourierClient();
        courier = random.basicUser();
        courierClient.createCourier(courier);
    }


    @Test
    @DisplayName("Удачный вход курьера")
    @Description("Проверяем статус код с корректными данными для входа")
    public void authorizationTest() {
        ValidatableResponse response = courierClient.loginCourier(courier);
        response.assertThat().body("id", Matchers.notNullValue()).and().statusCode(200);
        courierId = response.extract().path("id");

    }

    @Test
    @DisplayName("Попытка входа без ввода логина")
    @Description("Проверяем статус код при попытке входа без логина")
    public void authorizationWithoutLoginTest() {
        Courier withoutLogin = new Courier(null, courier.getPassword(), null);
        ValidatableResponse response = courierClient.loginCourier(withoutLogin);
        response.assertThat().body("message", Matchers.is("Недостаточно данных для входа")).
                and().statusCode(400);

    }

    @Test
    @DisplayName("Попытка входа без ввода пароля")
    @Description("Проверяем статус код при попытке входа без пароля")
    public void authorizationWithoutPasswordTest() {
        Courier withoutPassword = new Courier(courier.getLogin(), null, null);
        ValidatableResponse response = courierClient.loginCourier(withoutPassword);
        response.assertThat().statusCode(400).and().body("message", Matchers.is("Недостаточно данных для входа"));

    }

    @Test
    @DisplayName("Попытка входа с некорректным паролем")
    @Description("Проверяем статус код при попытке входа с некорректным паролем")
    public void authorizationWithWrongPasswordTest() {
        Courier invalidPassword = new Courier(courier.getLogin(), RandomStringUtils.randomAlphanumeric(6), null);
        ValidatableResponse response = courierClient.loginCourier(invalidPassword);
        response.assertThat().body("message", Matchers.is("Учетная запись не найдена")).and().statusCode(404);

    }

    @Test
    @DisplayName("Попытка входа с некорректным логином")
    @Description("Проверяем статус код при попытке входа с некорректным логином")
    public void authorizationWithWrongLoginTest() {
        Courier invalidLogin = new Courier(RandomStringUtils.randomAlphanumeric(7), courier.getPassword(),
                null);
        ValidatableResponse response = courierClient.loginCourier(invalidLogin);
        response.assertThat().body("message", Matchers.is("Учетная запись не найдена")).and().statusCode(404);

    }

    @Test
    @DisplayName("Попытка входа с некорректным логином и паролем")
    @Description("Проверяем статус код при попытке входа с некорректным логином и паролем")
    public void authorizationWithWrongLoginAndPasswordTest() {
        Courier invalidLoginAndPassword = new Courier(RandomStringUtils.randomAlphanumeric(5),
                RandomStringUtils.randomAlphanumeric(7), null);
        ValidatableResponse response = courierClient.loginCourier(invalidLoginAndPassword);
        response.assertThat().body("message", Matchers.is("Учетная запись не найдена")).and().statusCode(404);
    }

    @After
    @Step("Удалить курьера")
    public void deleteCourier() {
        if (courierId != 0) {
            courierClient.deleteCourier(courierId);
        }
    }

}
