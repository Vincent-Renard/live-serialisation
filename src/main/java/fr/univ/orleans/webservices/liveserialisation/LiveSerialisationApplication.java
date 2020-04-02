package fr.univ.orleans.webservices.liveserialisation;

import fr.univ.orleans.webservices.liveserialisation.modele.Message;
import fr.univ.orleans.webservices.liveserialisation.modele.Utilisateur;
import fr.univ.orleans.webservices.liveserialisation.service.Services;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LiveSerialisationApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiveSerialisationApplication.class, args);
    }

    @Bean
    CommandLineRunner initialisation(Services services) {
        return args -> {
            Utilisateur fred = new Utilisateur("fred","fred", false);
            Message post = new Message(null,"hello world !",fred);
            services.saveUtilisateur(fred);
            services.saveMessage(post);

            services.saveUtilisateur(new Utilisateur("admin","admin", true));
        };
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
