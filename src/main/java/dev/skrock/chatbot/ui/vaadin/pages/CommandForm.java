package dev.skrock.chatbot.ui.vaadin.pages;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.PropertyId;
import dev.skrock.chatbot.storage.CustomizableCommand;
import dev.skrock.chatbot.storage.CustomizableCommandRepository;
import dev.skrock.chatbot.storage.Sound;
import dev.skrock.chatbot.storage.SoundRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommandForm extends FormLayout {

    private final CustomizableCommandRepository commandRepository;
    private final SoundRepository soundRepository;

    @PropertyId("trigger")
    private final TextField trigger = new TextField("Trigger");
    @PropertyId("response")
    private final TextField response = new TextField("Response");
    @PropertyId("sound")
    private final ComboBox<Sound> sound = new ComboBox<>("Sound");

    private final Binder<CustomizableCommand> binder = new Binder<>(CustomizableCommand.class);

    public CommandForm(CustomizableCommandRepository commandRepository, SoundRepository soundRepository) {
        this.commandRepository = commandRepository;
        this.soundRepository = soundRepository;

        sound.setItems(soundRepository.findAll());
        sound.setItemLabelGenerator(Sound::getName);

        add(trigger);
        add(response);
        add(sound);

        Button saveButton = new Button("Save command");
        saveButton.addClickListener(event -> {
            if (binder.isValid()) {
                CustomizableCommand command = new CustomizableCommand();
                binder.writeBeanIfValid(command);
                commandRepository.save(command);
            }
        });
        add(saveButton);

        binder.forField(trigger).asRequired().bind(CustomizableCommand::getTrigger, CustomizableCommand::setTrigger);
        binder.forField(response).asRequired().bind(CustomizableCommand::getResponse, CustomizableCommand::setResponse);
        binder.forField(sound).bind(CustomizableCommand::getSound, CustomizableCommand::setSound);
    }
}
