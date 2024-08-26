package helpers;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"system:properties",
        "system:env",
        "file:src/main/tests.properties"
})
public interface TestsProperties extends Config {
    @Config.Key("google")
    String googleUrl();

    @Config.Key("default.timeout")
    int defaultTimeout();
}
