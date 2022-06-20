package Config;


import Client.RandomNumberApiClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Executor;

public class AppConfiguration {
    public AppConfiguration() {
    }

    public Properties getProperties() {
        Properties prop = new Properties();
        String fileName = "/Users/kacper.swis/Desktop/SpaceShipInvaders-master/src/main/resources/application.properties";
        try (FileInputStream fis = new FileInputStream(fileName)) {
            prop.load(fis);
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }

        return prop;
    }

    @Configuration
    @EnableAsync
    public static class AsyncConfiguration {

        @Bean
        public RandomNumberApiClient service() {
            return new RandomNumberApiClient(new RestTemplate());
        }

        @Bean(name = "asyncExecutor")
        public Executor asyncExecutor() {
            ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
            executor.setCorePoolSize(3);
            executor.setMaxPoolSize(3);
            executor.setQueueCapacity(100);
            executor.initialize();
            return executor;
        }
    }
}
