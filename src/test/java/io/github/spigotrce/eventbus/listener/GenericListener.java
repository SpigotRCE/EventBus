package io.github.spigotrce.eventbus.listener;

import io.github.spigotrce.eventbus.event.BootEvent;
import io.github.spigotrce.eventbus.event.EventHandler;
import io.github.spigotrce.eventbus.event.EventPriority;
import io.github.spigotrce.eventbus.event.listener.Listener;

public class GenericListener implements Listener {
    @EventHandler(priority = EventPriority.LOW)
    public void onEventL(BootEvent event) {
        System.out.println("Received eventL on GenericListener: " + event);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEventH(BootEvent event) {
        System.out.println("Received eventH on GenericListener: " + event);
    }
}
