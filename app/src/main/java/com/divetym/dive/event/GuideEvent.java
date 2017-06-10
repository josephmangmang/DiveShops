package com.divetym.dive.event;

import com.divetym.dive.models.Guide;

/**
 * Created by kali_root on 6/9/2017.
 */

public class GuideEvent {
    private Guide guide;

    public GuideEvent(Guide guide) {
        this.guide = guide;
    }

    public Guide getGuide() {
        return guide;
    }

    public void setGuide(Guide guide) {
        this.guide = guide;
    }
}
