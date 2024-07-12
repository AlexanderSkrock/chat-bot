package dev.skrock.chatbot.ui.vaadin.pages;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.Route;
import dev.skrock.chatbot.storage.CustomizableCommand;
import dev.skrock.chatbot.storage.CustomizableCommandRepository;
import dev.skrock.chatbot.storage.SoundEntity;
import dev.skrock.chatbot.storage.SoundRepository;
import dev.skrock.chatbot.ui.vaadin.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Route(value = "commands", layout = MainLayout.class)
public class CommandsView extends VerticalLayout {

    private final CustomizableCommandRepository commandRepository;

    private final SoundRepository soundRepository;

    private final DataProvider<CustomizableCommand, Void> commandProvider;

    @Autowired
    public CommandsView(CustomizableCommandRepository commandRepository, SoundRepository soundRepository) {
        this.commandRepository = commandRepository;
        this.soundRepository = soundRepository;
        this.commandProvider = DataProvider.fromCallbacks(
                query -> commandRepository.findAll(PageRequest.of(query.getOffset(), query.getPageSize())).stream(),
                query -> Math.toIntExact(commandRepository.count())
        );
    }

    @PostConstruct
    public void initView() {
        Grid<CustomizableCommand> grid = new Grid<>();
        grid.setItems(commandProvider);
        grid.addColumn(CustomizableCommand::getTrigger).setHeader("Trigger");
        grid.addColumn(CustomizableCommand::getResponse).setHeader("Response");
        grid.addColumn(command -> Optional.ofNullable(command.getSound()).map(SoundEntity::getName).orElse(null)).setHeader("Sound");
        add(grid);

        add(new CommandForm(commandRepository, soundRepository));
    }
}
