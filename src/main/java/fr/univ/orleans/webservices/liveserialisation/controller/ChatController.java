package fr.univ.orleans.webservices.liveserialisation.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.univ.orleans.webservices.liveserialisation.modele.Message;
import fr.univ.orleans.webservices.liveserialisation.modele.Utilisateur;
import fr.univ.orleans.webservices.liveserialisation.modele.Views;
import fr.univ.orleans.webservices.liveserialisation.service.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
public class ChatController {
    @Autowired
    private Services services;

    @JsonView(Views.MessageComplet.class)
    @PostMapping("/utilisateurs/{idUser}/messages")
    public ResponseEntity<Message> create(@PathVariable String idUser, @RequestBody Message message) {
        Utilisateur utilisateur = services.findUtilisateurById(idUser)
                .orElseThrow(()->new RuntimeException("Utilisateur non trouvé"));
        message.setUtilisateur(utilisateur);
        Message messageRec = services.saveMessage(message);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(messageRec.getId()).toUri();

        return ResponseEntity.created(location).body(messageRec);
    }

    @JsonView(Views.MessageComplet.class)
    @GetMapping("/utilisateurs/{idUser}/messages")
    public ResponseEntity<Collection<Message>>  getAll(@PathVariable String idUser) {
        return ResponseEntity.ok().body(services.findUtilisateurById(idUser).get().getMessages());
    }

    @JsonView(Views.UtilisateurComplet.class)
    @GetMapping("/utilisateurs/{idUser}")
    public ResponseEntity<Utilisateur>  getUtilisateur(@PathVariable String idUser) {
        return ResponseEntity.ok().body(services.findUtilisateurById(idUser).get());
    }

}
