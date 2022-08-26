package dev.skrock.chatbot.ui.vaadin.pages;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.SucceededEvent;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.Route;
import dev.skrock.chatbot.storage.Sound;
import dev.skrock.chatbot.storage.SoundRepository;
import dev.skrock.chatbot.ui.vaadin.MainLayout;
import dev.skrock.chatbot.ui.vaadin.components.AudioPlayer;
import dev.skrock.chatbot.util.StreamResourceUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Route(value = "sounds", layout = MainLayout.class)
public class SoundView extends VerticalLayout {

    private final SoundRepository soundRepository;

    private final DataProvider<Sound, Void> soundProvider;

    private final MemoryBuffer memoryBuffer = new MemoryBuffer();

    @Autowired
    public SoundView(SoundRepository soundRepository) {
        this.soundRepository = soundRepository;
        this.soundProvider = DataProvider.fromCallbacks(
                query -> soundRepository.findAll(PageRequest.of(query.getOffset(), query.getPageSize())).stream(),
                query -> Math.toIntExact(soundRepository.count())
        );
    }

    @PostConstruct
    public void initView() {
        Grid<Sound> grid = new Grid<>();
        grid.setItems(soundProvider);
        grid.addColumn(Sound::getName).setHeader("Name");
        grid.addComponentColumn(sound -> {
            AudioPlayer player = new AudioPlayer(StreamResourceUtils.ofSound(sound));
            player.disableControls();
            Button playButton = new Button("Play");
            playButton.addClickListener(event -> player.play());
            return new HorizontalLayout(playButton, player);
        });
        add(grid);

        Upload upload = new Upload();
        upload.setAcceptedFileTypes("audio/*");
        upload.setReceiver(memoryBuffer);
        upload.addSucceededListener(this::handleFileUpload);
        add(upload);
    }

    private void handleFileUpload(SucceededEvent event) {
        String fileName = event.getFileName();
        String mimeType = event.getMIMEType();
        InputStream fileData = memoryBuffer.getInputStream();

        try {
            persistSound(fileName, mimeType,  IOUtils.toByteArray(fileData));
        } catch (IOException e) {
            log.error("Fehler beim Speichern der Datei", e);
        }
    }

    private void persistSound(String soundName, String mimeType, byte[] soundData) {
        Sound soundToPersist = new Sound();
        soundToPersist.setName(soundName);
        soundToPersist.setMimeType(mimeType);
        soundToPersist.setData(soundData);
        soundRepository.save(soundToPersist);
    }

    private static class SoundEntry extends HorizontalLayout {
        public SoundEntry(Sound sound) {
            add(sound.getName());
            add(new Button("Abspielen"));
            add(new Button("Löschen"));
        }
    }
}
