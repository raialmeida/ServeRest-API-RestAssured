package config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import utils.Environment;

public final class TestConfig {

    private static boolean initialized = false;

    private TestConfig() {
    }

    public static synchronized void init() {
        if (initialized) {
            return;
        }

        RestAssured.filters(new AllureRestAssured());
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        writeAllureEnvironment();
        initialized = true;
    }

    private static void writeAllureEnvironment() {
        try {
            Properties props = new Properties();
            props.setProperty("Ambiente", Environment.getName());
            props.setProperty("BaseURL", Environment.getEnv("BASE_URI"));
            props.setProperty("OS", System.getProperty("os.name"));
            props.setProperty("User", System.getProperty("user.name"));

            File resultsDir = new File("target/allure-results");
            if (!resultsDir.exists()) {
                resultsDir.mkdirs();
            }

            try (FileOutputStream fos = new FileOutputStream("target/allure-results/environment.properties")) {
                props.store(fos, "Allure Environment Properties");
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao criar environment.properties do Allure", e);
        }
    }
}
