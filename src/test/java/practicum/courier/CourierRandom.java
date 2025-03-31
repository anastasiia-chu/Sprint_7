package practicum.courier;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierRandom {

    public Courier basicUser() {
        return new Courier("nastya4u", "171717", null);
    }

    public Courier random() {
        return new Courier(RandomStringUtils.randomAlphanumeric(10),
                RandomStringUtils.randomAlphanumeric(8),
                RandomStringUtils.randomAlphanumeric(10));

    }
}

