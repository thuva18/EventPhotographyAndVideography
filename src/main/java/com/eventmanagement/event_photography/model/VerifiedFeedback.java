package com.eventmanagement.event_photography.model;

public class VerifiedFeedback extends Feedback {
    private boolean verified;

    public VerifiedFeedback() {
        super();
    }

    public VerifiedFeedback(Long id, Long userId, Long photographerId, String comment, int rating, boolean verified) {
        super(id, userId, photographerId, comment, rating);
        this.verified = verified;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    @Override
    public String displayFeedback() {
        return "Feedback [ID=" + getId() + ", UserID=" + getUserId() + ", PhotographerID=" + getPhotographerId() +
                ", Comment=" + getComment() + ", Rating=" + getRating() + ", Verified=" + verified + "]";
    }
}