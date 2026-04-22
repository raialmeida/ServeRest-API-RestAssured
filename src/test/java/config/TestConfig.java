package config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;

public class TestConfig {

    static {
        RestAssured.filters(new AllureRestAssured());
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    public static void propertyAllure() {
        System.setProperty("allure.link.issue.pattern", "https://jira.seu-dominio.com/browse/{}");
        System.setProperty("allure.link.tms.pattern", "https://testrail.seu-dominio.com/cases/view/{}");

        try {
            Properties props = new Properties();
            props.setProperty("Ambiente", "ServeRest");
            props.setProperty("BaseURL", RestAssured.baseURI);
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
