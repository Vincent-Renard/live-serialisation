package fr.univ.orleans.webservices.liveserialisation.modele;

public class Message {
    private Long id;
    private String texte;
    private Utilisateur utilisateur;

    public Message(Long id, String texte, Utilisateur utilisateur) {
        this.id = id;
        this.texte = texte;
        this.utilisateur = utilisateur;
        //this.utilisateur.addMessage(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}
