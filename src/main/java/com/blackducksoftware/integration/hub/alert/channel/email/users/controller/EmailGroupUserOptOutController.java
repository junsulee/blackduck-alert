package com.blackducksoftware.integration.hub.alert.channel.email.users.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.blackducksoftware.integration.hub.alert.web.controller.BaseController;

@RequestMapping(BaseController.BASE_PATH + "/optout")
public class EmailGroupUserOptOutController extends BaseController {
    private final EmailGroupUserOptOutHandler emailGroupUserOptOutHandler;

    @Autowired
    public EmailGroupUserOptOutController(final EmailGroupUserOptOutHandler emailGroupUserOptOutHandler) {
        this.emailGroupUserOptOutHandler = emailGroupUserOptOutHandler;
    }

    @GetMapping
    public List<EmailGroupUserOptOutRestModel> getConfig(final Long id) {

    }

    @PostMapping
    public ResponseEntity<String> postConfig(final EmailGroupUserOptOutRestModel restModel) {

    }

    @PutMapping
    public ResponseEntity<String> putConfig(final EmailGroupUserOptOutRestModel restModel) {

    }

    @DeleteMapping
    public ResponseEntity<String> deleteConfig(final EmailGroupUserOptOutRestModel restModel) {

    }
}
