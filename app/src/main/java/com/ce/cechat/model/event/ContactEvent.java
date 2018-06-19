package com.ce.cechat.model.event;

/**
 * @author CE Chen
 *
 * 联系人变化的事件
 */
public class ContactEvent {

    private String mContactEvent;

    public ContactEvent(String pContactEvent) {
        this.mContactEvent = pContactEvent;
    }

    public String getContactEvent() {
        return mContactEvent;
    }

    public void setContactEvent(String pContactEvent) {
        this.mContactEvent = pContactEvent;
    }
}
