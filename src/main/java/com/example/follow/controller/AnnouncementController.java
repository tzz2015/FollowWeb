package com.example.follow.controller;

import com.example.follow.model.Announcement;
import com.example.follow.model.response.ResponseResult;
import com.example.follow.model.response.ResultCode;
import com.example.follow.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author LYF
 * @dec: 公告
 * @date 2023/7/16
 **/
@RestController
@RequestMapping("/api/announcement")
@ResponseResult
public class AnnouncementController {
    @Autowired
    private AnnouncementService announcementService;

    @PostMapping("/update")
    public Announcement update(@RequestBody Announcement announcement) {
        return announcementService.updateAnnouncement(announcement);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id) {
        announcementService.deleteAnnouncement(id);
        return ResultCode.SUCCESS.getMessage();
    }

    @GetMapping("/list")
    public List<Announcement> findAll() {
        return announcementService.findAll();
    }

}
