package dev.skrock.chatbot.storage;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
@Entity
public class CustomizableCommand {

    @Id
    private String trigger;

    private String response;

    @OneToOne
    private Sound sound;
}
