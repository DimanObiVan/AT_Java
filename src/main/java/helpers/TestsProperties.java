package helpers;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"system:properties",
        "system:env",
        "file:src/main/resources/tests.properties"
})
public interface TestsProperties extends Config {
    @Config.Key("google")
    String googleUrl();

    @Config.Key("default.timeout")
    int defaultTimeout();

    @Config.Key("yandexmarketUrl")
    String yandexmarketUrl();

    @Config.Key("sleepTime")
    int sleepTime();
}
