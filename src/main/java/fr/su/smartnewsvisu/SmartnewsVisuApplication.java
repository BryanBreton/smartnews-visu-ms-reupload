package fr.su.smartnewsvisu;

import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import fr.su.back.api.headers.HeaderUtil;
import fr.su.back.api.security.openapidoc.IrisOperationCustomizer;

import io.micrometer.core.instrument.logging.LoggingMeterRegistry;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SpringBootApplication
@ComponentScan(basePackages = { "fr.su.smartnewsvisu", "fr.su.smartnewscommun", "fr.su.back" })
@EntityScan(basePackageClasses = { SmartnewsVisuApplication.class, Jsr310JpaConverters.class }, basePackages = { "fr.su.smartnewscommun" })
@EnableJpaRepositories(basePackages = { "fr.su.smartnewsvisu.repositories", "fr.su.smartnewscommun.repositories" })
@PropertySource({ "classpath:authentification.properties", "classpath:ldap_ws.properties" })
@EnableCaching
@OpenAPIDefinition(
        info =
        @Info(
                description = "Module du projet smartnews-visu",
                contact = @Contact(email = "claude.croguennoc@systeme-u.fr"))
)
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)
public class SmartnewsVisuApplication extends SpringBootServletInitializer {

    @Autowired
    Environment env;

    @Autowired
    private HeaderUtil headerUtil;

    @Configuration
    @Profile("dev")
    @PropertySource("classpath:env.properties")
    static class DevProperty {
    }

    /**
     * Gestion des fichiers de messages de l'application
     *
     * @return Bean spring pour les messages
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:/messages", "classpath:/defaultErrorMessages");

        return messageSource;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOriginPatterns("*").allowedMethods("*").allowCredentials(true).exposedHeaders(headerUtil.getExposedHeader());
            }
        };
    }

    /**
     * Déclare un Bean permettant de customiser la documentation Open API générée par Springdoc pour chaque opération.
     * Automatise l'ajout des habilitations et scopes (à partir du @Secured) dans la description de l'Opération.
     *
     * @return un OperationCustomizer (Springdoc) Iris
     */
    @Bean
    public OperationCustomizer operationCustomizer() {
        return new IrisOperationCustomizer().getIrisOperationCustomizer();
    }

    @Bean
    public LoggingMeterRegistry registry() {
        return new LoggingMeterRegistry();
    }

    public static void main(String[] args) {
        SpringApplication.run(SmartnewsVisuApplication.class, args);
    }
}
