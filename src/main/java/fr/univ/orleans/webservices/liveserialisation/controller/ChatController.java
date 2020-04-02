package fr.univ.orleans.webservices.liveserialisation.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.univ.orleans.webservices.liveserialisation.dto.MessageDTO;
import fr.univ.orleans.webservices.liveserialisation.dto.UtilisateurDTO;
import fr.univ.orleans.webservices.liveserialisation.modele.Message;
import fr.univ.orleans.webservices.liveserialisation.modele.Utilisateur;
import fr.univ.orleans.webservices.liveserialisation.service.Services;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ChatController {
    @Autowired
    private Services services;

    @Autowired
    private ModelMapper mapper;

    @PostMapping("/utilisateurs/{idUser}/messages")
    public ResponseEntity<MessageDTO> create(@PathVariable String idUser, @RequestBody MessageDTO messageDTO) {
        Message message = mapper.map(messageDTO, Message.class);
        Utilisateur utilisateur = services.findUtilisateurById(idUser)
                .orElseThrow(()->new RuntimeException("Utilisateur non trouvé"));
        message.setUtilisateur(utilisateur);
        Message messageRec = services.saveMessage(message);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(messageRec.getId()).toUri();

        MessageDTO messageRecDTO = mapper.map(messageRec, MessageDTO.class);
        return ResponseEntity.created(location).body(messageRecDTO);
    }


    @GetMapping("/utilisateurs/{idUser}/messages")
    public ResponseEntity<Collection<MessageDTO>>  getAll(@PathVariable String idUser) {

        final List<Message> messages = services.findUtilisateurById(idUser).get().getMessages();
        final List<MessageDTO> messagesDTO = messages.stream()
                .map(message -> mapper.map(message,MessageDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(messagesDTO);
    }

    @GetMapping("/utilisateurs/{idUser}")
    public ResponseEntity<UtilisateurDTO>  getUser(@PathVariable String idUser) {
        final Utilisateur utilisateur = services.findUtilisateurById(idUser).get();
        final UtilisateurDTO utilisateurDTO = mapper.map(utilisateur,UtilisateurDTO.class);
        return ResponseEntity.ok().body(utilisateurDTO);
    }

}
