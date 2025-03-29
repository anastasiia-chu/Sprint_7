package practicum.courier.tests;


import io.qameta.allure.Step;


import io.restassured.response.ValidatableResponse;

import org.hamcrest.Matchers;
import org.junit.After;

import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;

import practicum.courier.Courier;
import practicum.courier.CourierClient;
import practicum.courier.CourierRandom;


public class CreateCourierTests {
    protected final CourierRandom random = new CourierRandom();
    private CourierClient courierClient;
    private Courier courier;

    int courierId;

    @Before
    @Step("Креды для создания курьера")
    public void setUp() {
        courierClient = new CourierClient();
        courier = random.random();

    }

    @Test
    @DisplayName("Успешное создание курьера")
    public void createCourierTest() {
        ValidatableResponse response = courierClient.createCourier(courier);
        response.assertThat().body("ok", Matchers.is(true)).and().statusCode(201);
        ValidatableResponse loginResponse = courierClient.loginCourier(courier);
        courierId = loginResponse.extract().path("id");
        System.out.println("Вот это айди удаляем: " + courierId);
    }

    @Test
    @DisplayName("Попытка создания курьера без логина и пароля")
    public void createCourierWithoutLoginAndPasswordTest() {
        courier.setLogin(null);
        courier.setPassword(null);
        ValidatableResponse response = courierClient.createCourier(courier);
        response.assertThat().body("message", Matchers.notNullValue()).and().statusCode(400);
    }


    @Test
    @DisplayName("Попытка создания курьера без пароля и имени")
    public void createCourierWithoutPasswordAndFirstNameTest() {
        courier.setFirstName(null);
        courier.setPassword(null);
        ValidatableResponse response = courierClient.createCourier(courier);
        response.assertThat().body("message", Matchers.notNullValue()).and().statusCode(400);
    }

    @Test
    @DisplayName("Попытка создания курьера без пароля")
    public void createCourierWithoutPasswordTest() {
        courier.setPassword(null);
        ValidatableResponse response = courierClient.createCourier(courier);
        response.assertThat().body("message", Matchers.notNullValue()).and().statusCode(400);
    }

    @Test
    @DisplayName("Попытка создания курьера без логина")
    public void createCourierWithoutLoginTest() {
        courier.setLogin(null);
        ValidatableResponse response = courierClient.createCourier(courier);
        response.assertThat().body("message", Matchers.notNullValue()).and().statusCode(400);
    }

    @Test
    @DisplayName("Попытка создания курьера с уже использованными ранее данными")
    public void createCourierWithAlreadyUsedDataTest() {
        courierClient.createCourier(courier);
        ValidatableResponse response = courierClient.createCourier(courier);
        response.assertThat().body("message", Matchers.notNullValue()).and().statusCode(409);
    }


    @After
    public void deleteCourier() {
        if (courierId > 0) {
            courierClient.deleteCourier(courierId);
        }

    }
}