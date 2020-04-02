package fr.univ.orleans.webservices.liveserialisation.modele;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.ArrayList;
import java.util.List;

public class Utilisateur {
    private final String login;
    private final String password;
    private final boolean isAdmin;
    private final List<Message> messages = new ArrayList<>();

    public Utilisateur(String login, String password, boolean isAdmin) {
        this.login = login;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public List<Message> getMessages() {
        return messages;
    }
    public void addMessage(Message message) {
        message.setUtilisateur(this);
        messages.add(message);
    }
}
