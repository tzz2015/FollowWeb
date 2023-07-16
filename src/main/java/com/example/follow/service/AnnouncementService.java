package com.example.follow.service;

import com.example.follow.model.Announcement;

import java.util.List;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/16
 **/
public interface AnnouncementService {
    Announcement updateAnnouncement(Announcement annotation);

    void deleteAnnouncement(long id);

    List<Announcement> findAll();
}
