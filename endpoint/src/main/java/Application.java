import de.holtmeyer.niklas.spotify.data.service.configuration.ClientConfiguration;
import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

@SpringBootApplication
@ComponentScan(basePackages = {"de.holtmeyer.niklas.spotify"})
public class Application {
    @Autowired Environment env;
    @Autowired ClientConfiguration clientConfiguration;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    private void setSecrets() {
        @NonNull var CLIENT_ID = env.getProperty("CLIENT_ID");
        @NonNull var CLIENT_SECRET = env.getProperty("CLIENT_SECRET");
        clientConfiguration.setID(CLIENT_ID);
        clientConfiguration.setSECRET(CLIENT_SECRET);
    }
}