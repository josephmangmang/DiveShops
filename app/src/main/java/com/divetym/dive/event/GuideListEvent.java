package com.divetym.dive.event;

import com.divetym.dive.models.Guide;

import java.util.List;

/**
 * Created by kali_root on 6/10/2017.
 */

public class GuideListEvent {
    private List<Guide> guides;

    public GuideListEvent(List<Guide> guides) {
        this.guides = guides;
    }

    public List<Guide> getGuides() {
        return guides;
    }

    public void setGuides(List<Guide> guides) {
        this.guides = guides;
    }
}
