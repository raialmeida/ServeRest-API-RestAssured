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

        System.setProperty("allure.link.issue.pattern", "https://jira.seu-dominio.com/browse/{}");
        System.setProperty("allure.link.tms.pattern", "https://testrail.seu-dominio.com/cases/view/{}");

        writeAllureEnvironment();
        initialized = true;
    }

    private static void writeAllureEnvironment() {
        try {
            Properties props = new Properties();
            props.setProperty("Ambiente", System.getProperty("profile.name"));
            props.setProperty("BaseURL", Environment.getEnv("baseURI"));
            props.setProperty("OS", System.getProperty("os.name"));
            props.setProperty("User", getEnvOrDefault("PIPELINE_USER", System.getProperty("user.name")));
            props.setProperty("UserEmail", getEnvOrDefault("PIPELINE_USER_EMAIL", "unknown"));

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

    private static String getEnvOrDefault(String name, String defaultValue) {
        String value = System.getenv(name);
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        return value;
    }
}
