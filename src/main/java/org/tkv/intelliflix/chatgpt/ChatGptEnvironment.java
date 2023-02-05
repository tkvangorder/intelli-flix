package org.tkv.intelliflix.chatgpt;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ChatGptEnvironment {

    public static final String OPENAI_API_PERSONAL_TOKEN_KEY = "openai.api.access.key";
    public static final String OPENAI_API_URL_KEY = "openai.api.url";

    Properties properties;

    public ChatGptEnvironment(Properties additionalProperties) {
        properties = additionalProperties;
    }

    public String getOpenaiApiAccessKey() {
        return getValue(OPENAI_API_PERSONAL_TOKEN_KEY);
    }

    public void setOpenaiApiAccessKey(String openaiApiAccessKey) {
        properties.setProperty(OPENAI_API_PERSONAL_TOKEN_KEY, openaiApiAccessKey);
    }

    public String getOpenaiApiUrl() {
        return getValue(OPENAI_API_URL_KEY);
    }

    public void setOpenaiApiUrl(String openaiApiUrl) {
        properties.setProperty(OPENAI_API_URL_KEY, openaiApiUrl);
    }

    public String getValue(String key) {
        String value = System.getProperty(key);
        return value != null ? value : properties.getProperty(key);
    }

    public static Properties loadProperties(String filename) {
        try (InputStream input = new FileInputStream(filename)) {
            Properties properties = new Properties();
            properties.load(input);
            return properties;
        } catch (IOException ex) {
            throw new IllegalStateException("Cannot load properties from " + filename, ex);
        }
    }
}
