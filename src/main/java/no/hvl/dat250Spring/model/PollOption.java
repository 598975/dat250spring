package no.hvl.dat250Spring.model;

public class PollOption {
    private String caption;
    private String presentationOrder;

    public PollOption() {
    }

    public String getPresentationOrder() {
        return presentationOrder;
    }

    public void setPresentationOrder(String presentationOrder) {
        this.presentationOrder = presentationOrder;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
