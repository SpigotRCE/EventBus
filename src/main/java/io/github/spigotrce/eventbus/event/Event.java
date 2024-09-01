package io.github.spigotrce.eventbus.event;

public abstract class Event {
    private String name;

    /**
     * Default constructor
     */
    public Event() {
    }

    /**
     * Convenience method for providing a user-friendly identifier. By
     * default, it is the event's class's {@linkplain Class#getSimpleName()
     * simple name}.
     *
     * @return name of this event
     */
    public String getEventName() {
        if (name == null) {
            name = getClass().getSimpleName();
        }
        return name;
    }
}
