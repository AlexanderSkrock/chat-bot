package dev.skrock.chatbot.ui.vaadin;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import dev.skrock.chatbot.ui.vaadin.pages.CommandsView;
import dev.skrock.chatbot.ui.vaadin.pages.CounterView;
import dev.skrock.chatbot.ui.vaadin.pages.MainView;
import dev.skrock.chatbot.ui.vaadin.pages.SoundView;

import java.util.List;

public class MainLayout extends AppLayout {

    public MainLayout() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 title = new H1("Chatbot");
        title.addClassNames("text-l", "m-m");

        HorizontalLayout header = new HorizontalLayout(
                new DrawerToggle(),
                title
        );

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.BASELINE);
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);
    }

    private void createDrawer() {
        List<RouterLink> links = List.of(
                new RouterLink("Startseite", MainView.class),
                new RouterLink("Commands", CommandsView.class),
                new RouterLink("Sounds", SoundView.class),
                new RouterLink("Counters", CounterView.class)
        );
        links.forEach(link -> link.setHighlightCondition(HighlightConditions.sameLocation()));

        VerticalLayout layout = new VerticalLayout();
        links.forEach(layout::add);
        addToDrawer(layout);
    }
}
