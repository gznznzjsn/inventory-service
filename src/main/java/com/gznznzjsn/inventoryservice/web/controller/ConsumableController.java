package com.gznznzjsn.inventoryservice.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
public class ConsumableController {

    @GetMapping
    public Boolean isAccepted(){
        return false;
    }

}
