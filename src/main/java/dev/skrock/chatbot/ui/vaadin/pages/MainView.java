package dev.skrock.chatbot.ui.vaadin.pages;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import dev.skrock.chatbot.ui.vaadin.MainLayout;
import dev.skrock.chatbot.ui.vaadin.VaadinUserInterfaceBridge;

@PageTitle("Startseite")
@Route(layout = MainLayout.class)
public class MainView extends VerticalLayout {

    private final VaadinUserInterfaceBridge uiBridge;

    public MainView(VaadinUserInterfaceBridge uiBridge) {
        this.uiBridge = uiBridge;
        add(new Text("Welcome to ChatBot"));
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        uiBridge.setUI(attachEvent.getUI());
    }
}
