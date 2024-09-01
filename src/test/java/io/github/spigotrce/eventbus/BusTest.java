package io.github.spigotrce.eventbus;

import io.github.spigotrce.eventbus.event.BootEvent;
import io.github.spigotrce.eventbus.event.EventHandler;
import io.github.spigotrce.eventbus.event.EventManager;
import io.github.spigotrce.eventbus.event.listener.Listener;
import io.github.spigotrce.eventbus.listener.GenericListener;

public class BusTest implements Listener {
    private final EventManager eventManager;

    public BusTest() {
        this.eventManager = new EventManager();

        eventManager.registerListener(this);
        eventManager.registerListener(new GenericListener());

        try {
            eventManager.fireEvent(new BootEvent());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        new BusTest();
    }

    @EventHandler
    public void onEvent(BootEvent event) {
        System.out.println("Received eventN on BusTest: " + event);
    }
}
