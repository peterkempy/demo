package pk.demo.containers.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import pk.demo.containers.demo.service.HostnameService;

import static pk.demo.containers.demo.service.HostnameService.getMachineDetails;

@RestController
public class DemoController {

    @GetMapping("/info")
    public String getInfo() {
        return getMachineDetails();
    }
}

