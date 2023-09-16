package com.example.follow.service;

import com.example.follow.except.BusinessException;
import com.example.follow.model.Announcement;
import com.example.follow.repository.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/16
 **/
@Service
public class AnnouncementServiceImpl implements AnnouncementService {
    @Autowired
    private AnnouncementRepository announcementRepository;

    @Override
    public Announcement updateAnnouncement(Announcement announcement) {
        Announcement save;
        if (announcement.getId() != null) {
            Announcement first = announcementRepository.findFirstById(announcement.getId());
            if (first == null) {
                throw new BusinessException("该条数据不存在");
            }
            save = first;
            save.setContent(announcement.getContent());
            save.setTitle(announcement.getTitle());
            save.setEnContent(announcement.getEnContent());
            save.setEnTitle(announcement.getEnTitle());
            save.setWeight(announcement.getWeight());
        } else {
            save = announcement;
        }
        return announcementRepository.save(save);
    }

    @Override
    public void deleteAnnouncement(long id) {
        Announcement spread = announcementRepository.findFirstById(id);
        if (spread == null) {
            throw new BusinessException("该条数据不存在");
        }
        announcementRepository.deleteById(id);
    }

    @Override
    public List<Announcement> findAll() {
        return announcementRepository.findAll();
    }
}
