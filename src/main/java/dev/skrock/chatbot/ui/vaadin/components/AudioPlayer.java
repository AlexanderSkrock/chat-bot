package dev.skrock.chatbot.ui.vaadin.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.dom.DomEventListener;
import com.vaadin.flow.server.AbstractStreamResource;

@Tag("audio")
public class AudioPlayer extends Component {

    public AudioPlayer() {
    }

    public AudioPlayer(String source) {
        setSource(source);
    }

    public AudioPlayer(final AbstractStreamResource resource) {
        setSource(resource);
    }

    public  void setSource(String source){
        getElement().setProperty("src", source);
    }

    public  void setSource(final AbstractStreamResource resource) {
        getElement().setAttribute("src", resource);
    }

    public void enableControls() {
        getElement().setAttribute("controls", true);
    }

    public void disableControls() {
        getElement().setAttribute("controls", false);
    }

    public void enableAutoplay() {
        getElement().setAttribute("autoplay", true);
    }

    public void disableAutoplay() {
        getElement().setAttribute("autoplay", false);
    }

    public void play() {
        getElement().callJsFunction("play");
    }

    public void pause() {
        getElement().callJsFunction("pause");
    }

    public void onFinished(DomEventListener listener) {
        getElement().addEventListener("ended", listener);
    }
}
