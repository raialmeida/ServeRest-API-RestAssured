package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Environment {
    private static final Properties properties = new Properties();
    private static boolean loaded = false;
    private static String loadedEnv;

    private Environment() {
    }

    private static void loadProperties() {
        if (loaded) {
            return;
        }

        String env = System.getProperty("env");
        if (env == null || env.isBlank()) {
            env = "src/test/resources/dev.properties";
        }

        try (FileInputStream input = new FileInputStream(env)) {
            properties.load(input);
            loaded = true;
            loadedEnv = env;
            System.out.println("Arquivo de ambiente carregado: " + env);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar arquivo de ambiente: " + env, e);
        }
    }

    public static String getEnv(String key) {
        loadProperties();
        String value = properties.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new RuntimeException("Chave '" + key + "' não encontrada no arquivo: " + loadedEnv);
        }
        return value;
    }
}
