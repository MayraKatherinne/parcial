package com.kathar.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/publicidad")
public class PublicidadLandingController {

    @GetMapping("/landing")
    public String mostrarLanding() {
        return "publicidad/index"; // busca en templates/publicidad/index.html
    }
}
