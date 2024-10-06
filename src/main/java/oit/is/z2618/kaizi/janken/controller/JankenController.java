package oit.is.z2618.kaizi.janken.controller;

import oit.is.z2618.kaizi.janken.model.Janken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class JankenController {
    private Janken janken;

    public JankenController() {
        this.janken = new Janken();
    }

    @GetMapping("/janken")
    public String janken(@RequestParam String username, Model model) {
        model.addAttribute("username", username);
        return "janken";
    }

    @GetMapping("/janken/play")
    public String playJanken(@RequestParam(name = "hand") String yourHand, Model model) {
        janken.setPlayerHand(yourHand);
        String result = janken.judge();

        model.addAttribute("yourHand", janken.getPlayerHand());
        model.addAttribute("cpuHand", janken.getCpuHand());
        model.addAttribute("result", result);

        return "janken";
    }
}
