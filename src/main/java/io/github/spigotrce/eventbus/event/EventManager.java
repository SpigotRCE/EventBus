package io.github.spigotrce.eventbus.event;

import io.github.spigotrce.eventbus.event.listener.Listener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * EventManager to fire events and handle listeners
 */
public class EventManager {
    private final Map<String, ArrayList<EventHandlerEntry>> eventHandlers;

    /**
     * Default constructor
     */
    public EventManager() {
        this.eventHandlers = new HashMap<>();
    }

    /**
     * Register a listener for a specific event
     *
     * @param listener The listener to register
     */
    public void registerListener(Listener listener) {
        for (Method method : listener.getClass().getMethods()) {
            EventHandler handler = method.getAnnotation(EventHandler.class);
            if (handler != null) {
                if (!eventHandlers.containsKey(method.getParameterTypes()[0].getSimpleName())) {
                    eventHandlers.put(method.getParameterTypes()[0].getSimpleName(), new ArrayList<>());
                }
                eventHandlers.get(method.getParameterTypes()[0].getSimpleName()).add(new EventHandlerEntry(listener, method, handler));
            }
        }
    }

    /**
     * Unregister a listener for a specific event
     *
     * @param listener The listener to unregister
     */
    public void unregisterListener(Listener listener) {
        eventHandlers.forEach((key, entries) -> entries.removeIf(entry -> entry.listener.equals(listener)));
    }

    /**
     * Fire an event
     *
     * @param event The event to fire
     */
    public void fireEvent(Event event) throws InvocationTargetException, IllegalAccessException {
        ArrayList<EventHandlerEntry> handlers = eventHandlers.get(event.getEventName());

        if (handlers == null) {
            return;
        }

        // Sort handlers by priority (LOWEST first)
        handlers.sort(Comparator.comparingInt(e -> e.handler.priority().getSlot()));

        for (EventHandlerEntry entry : handlers) {
            EventHandler handler = entry.handler;
            Method method = entry.method;
            Listener listener = entry.listener;

            // Check if the event is cancellable and if the handler should ignore cancelled events
            if (event instanceof Cancellable) {
                Cancellable cancellableEvent = (Cancellable) event;
                if (cancellableEvent.isCancel() && handler.ignoreCancelled()) {
                    continue; // Skip this handler if the event is cancelled and it should ignore cancelled events
                }
            }

            method.invoke(listener, event);
        }
    }

    /**
     * Inner class to store event handlers and their metadata
     */
    private static class EventHandlerEntry {
        final Listener listener;
        final Method method;
        final EventHandler handler;

        EventHandlerEntry(Listener listener, Method method, EventHandler handler) {
            this.listener = listener;
            this.method = method;
            this.handler = handler;
        }
    }
}
