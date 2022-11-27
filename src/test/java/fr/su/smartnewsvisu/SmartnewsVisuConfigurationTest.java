package fr.su.smartnewsvisu;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.NoCredentialsProvider;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.contrib.nio.testing.LocalStorageHelper;

@Configuration
public class SmartnewsVisuConfigurationTest {
    @Configuration
    @ActiveProfiles("test")
    @PropertySource("classpath:env-test.properties")
    static class TestProperty {
    }

    @Configuration
    @ActiveProfiles("ti-local")
    @PropertySource("classpath:env-ti-local.properties")
    static class TIProperty {
    }

    /**
     * on surcharge l'authentification pour ne pas envoyer de credentials
     *
     * @return les credentials utilisés
     */
    @Bean
    CredentialsProvider googleCredentials() {
        return NoCredentialsProvider.create();
    }

    @Bean
    Storage storage() {
        return LocalStorageHelper.getOptions().getService();
    }

}
