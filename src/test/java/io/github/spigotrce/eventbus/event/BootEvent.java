package io.github.spigotrce.eventbus.event;

public class BootEvent extends Event implements Cancellable {
    private boolean cancel;

    public BootEvent() {
        cancel = false;
    }

    @Override
    public boolean isCancel() {
        return cancel;
    }

    @Override
    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }
}
