package com.petproject.workflow.api.controllers;

import com.petproject.workflow.api.dtos.AccessDto;
import com.petproject.workflow.api.services.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/accesses", produces = "application/json")
public class AccessController {

    private final AccessService accessService;

    @Autowired
    public AccessController(AccessService accessService) {
        this.accessService = accessService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccessDto> getAccessById(@PathVariable("id") UUID id) {
        return accessService.getAccessById(id)
                .map(access -> new ResponseEntity<>(access, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/issuer/{issuerId}")
    public Iterable<AccessDto> getAllAccessesByIssuerId(@PathVariable("issuerId") UUID issuerId) {
        return accessService.getAllAccessesByIssuerId(issuerId);
    }

    @GetMapping("/holder/{holderId}")
    public Iterable<AccessDto> getAllAccessesByHolderId(@PathVariable("holderId") UUID holderId) {
        return accessService.getAllAccessesByHolderId(holderId);
    }
}
