package practicum.courier.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import practicum.EnvConfig;
import practicum.courier.Courier;
import practicum.courier.CourierClient;


public class CreateCourierTests {
    CourierClient clientStep = new CourierClient();
    private int courierId;

    @Before
    public void setUp() {
        RestAssured.baseURI = EnvConfig.BASE_URL;
    }

    @Test
    @DisplayName("Успешное создание курьера")
    public void createCourierTest() {
        String login = RandomStringUtils.randomAlphanumeric(1, 10);
        String password = RandomStringUtils.randomAlphanumeric(6, 8);
        String firstName = RandomStringUtils.randomAlphabetic(3, 10);
        Courier courier = new Courier(login, password, firstName);
        Response response = clientStep.createCourier(courier);
        response.then().log().all()
                .assertThat().body("ok", Matchers.is(true)).and().statusCode(201);
        clientStep.loginCourier(courier);
        response.then().log().all()
                .assertThat().body("id", Matchers.notNullValue()).and().statusCode(200);
    }

    @Test
    @DisplayName("Попытка создания курьера без логина и пароля")
    public void createCourierWithoutLoginAndPasswordTest() {
        CourierClient clientStep = new CourierClient();
        String firstName = RandomStringUtils.randomAlphabetic(3, 10);
        Courier courier = new Courier();
        courier.setFirstName(firstName);
        Response response = clientStep.createCourier(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.notNullValue()).and().statusCode(400);
    }


    @Test
    @DisplayName("Попытка создания курьера без пароля и имени")
    public void createCourierWithoutPasswordAndFirstNameTest() {
        CourierClient clientStep = new CourierClient();
        String login = RandomStringUtils.randomAlphabetic(3, 10);
        Courier courier = new Courier();
        courier.setLogin(login);
        Response response = clientStep.createCourier(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.notNullValue()).and().statusCode(400);
    }

    @Test
    @DisplayName("Попытка создания курьера без пароля")
    public void createCourierWithoutPasswordTest() {
        CourierClient clientStep = new CourierClient();
        String login = RandomStringUtils.randomAlphabetic(1, 10);
        String firstName = RandomStringUtils.randomAlphabetic(3, 10);
        Courier courier = new Courier();
        courier.setLogin(login);
        courier.setFirstName(firstName);
        Response response = clientStep.createCourier(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.notNullValue()).and().statusCode(400);
    }

    @Test
    @DisplayName("Попытка создания ркуьера без логина")
    public void createCourierWithoutLoginTest() {
        CourierClient clientStep = new CourierClient();
        String firstName = RandomStringUtils.randomAlphabetic(3, 10);
        String password = RandomStringUtils.randomAlphanumeric(6, 8);
        Courier courier = new Courier();
        courier.setFirstName(firstName);
        courier.setPassword(password);
        Response response = clientStep.createCourier(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.notNullValue()).and().statusCode(400);
    }

    @After
    public void deleteCourier() {
        CourierClient courierStep = new CourierClient();
        Courier courier = new Courier();
        courierId = courier.getId();
        if (courierId > 0) {
            courierStep.deleteCourier(courierId);
        }
    }
}