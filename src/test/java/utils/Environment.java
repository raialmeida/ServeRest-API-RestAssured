package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Environment {
    private static final String DEFAULT_ENV = "dev";
    private static final String CONFIG_FILE_PATTERN = "config-%s.properties";
    private static final Properties properties = new Properties();
    private static boolean loaded;
    private static String loadedEnv;
    private static String loadedResource;

    private Environment() {
    }

    private static synchronized void loadProperties() {
        if (loaded) {
            return;
        }

        loadedEnv = System.getProperty("env", DEFAULT_ENV).trim();
        if (loadedEnv.isBlank()) {
            loadedEnv = DEFAULT_ENV;
        }

        loadedResource = CONFIG_FILE_PATTERN.formatted(loadedEnv);

        try (InputStream input = Environment.class.getClassLoader().getResourceAsStream(loadedResource)) {
            if (input == null) {
                throw new IllegalStateException(
                        "Arquivo de configuração não encontrado em resource: " + loadedResource);
            }
            properties.load(input);
            loaded = true;
            System.out.println("Ambiente carregado: " + loadedEnv + " (" + loadedResource + ")");
        } catch (IOException e) {
            throw new IllegalStateException("Erro ao carregar configuração: " + loadedResource, e);
        }
    }

    public static String getEnv(String key) {
        loadProperties();

        String value = System.getProperty(key);
        if (value == null || value.isBlank()) {
            value = System.getenv(key);
        }
        if (value == null || value.isBlank()) {
            value = properties.getProperty(key);
        }

        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Variável '" + key + "' não encontrada para o ambiente: " + loadedEnv);
        }
        return value.trim();
    }

    public static String getName() {
        loadProperties();
        return loadedEnv;
    }
}
