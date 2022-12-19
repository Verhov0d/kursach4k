package com.example.bigsevaup.Controller;

import com.example.bigsevaup.Model.*;
import com.example.bigsevaup.Repository.CheckRepository;
import com.example.bigsevaup.Repository.UslugaCheckRepository;
import com.example.bigsevaup.Repository.UslugaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/uslugacheck")
public class UslugaCheckController {
    @Autowired
    UslugaCheckRepository uslugaCheckRepository;
    @Autowired
    UslugaRepository uslugaRepository;
    @Autowired
    CheckRepository checkRepository;

    @GetMapping("")
    public String zooMain(Model model) {
        Iterable<UslugaCheck> listUslugaCheck = uslugaCheckRepository.findAll();
        model.addAttribute("listUslugaCheck", listUslugaCheck);
        return "uslugacheck/index";
    }

    @GetMapping("/add")
    public String zooAddView(UslugaCheck uslugacheck, Model model) {
        Iterable<Usluga> uslugas = uslugaRepository.findAll();
        model.addAttribute("uslugas", uslugas);
        Iterable<Check> checks = checkRepository.findAll();
        model.addAttribute("checks", checks);
        return "uslugacheck/add";
    }

    @PostMapping("/add")
    public String zooAdd(Model model, @Valid UslugaCheck uslugacheck,
                         BindingResult result,
                         @RequestParam(name = "usluga_name") String usl,
                         @RequestParam(name = "check_name") String che){
        if(result.hasErrors()){
            Iterable<Usluga> uslugaIterable = uslugaRepository.findAll();
            model.addAttribute("uslugas", uslugaIterable);
            Iterable<Check> checkIterable = checkRepository.findAll();
            model.addAttribute("checks", checkIterable);
            return "uslugacheck/add";
        }
        Usluga uslugas1 = uslugaRepository.findById(Long.parseLong(usl)).orElseThrow();
        uslugacheck.setUsluga(uslugas1);
        Check checks1 = checkRepository.findById(Long.parseLong(che)).orElseThrow();
        uslugacheck.setCheck2(checks1);
        uslugaCheckRepository.save(uslugacheck);
        return "redirect:/uslugacheck";
    }
    @GetMapping("/edit/{id}")
    public String zooEdit(Model model,
                          @PathVariable long id) {
        UslugaCheck uslugaCheck = uslugaCheckRepository.findById(id).orElseThrow();
        model.addAttribute("uslugaCheck", uslugaCheck);
        Iterable<Usluga> uslugaIterable = uslugaRepository.findAll();
        model.addAttribute("uslugas", uslugaIterable);
        Iterable<Check> checkIterable = checkRepository.findAll();
        model.addAttribute("checks", checkIterable);
        return("/uslugacheck/edit");
    }

    @PostMapping("/edit/{id}")
    public String zooEdit(Model model, @Valid UslugaCheck uslugacheck, BindingResult result,
                          Long check_name, Long usluga_name) {
        if(result.hasErrors()){
            Iterable<Usluga> productIterable = uslugaRepository.findAll();
            model.addAttribute("uslugas", productIterable);
            Iterable<Check> checkIterable = checkRepository.findAll();
            model.addAttribute("checks", checkIterable);
            return "/uslugacheck/edit";
        }
        uslugacheck.setUsluga(uslugaRepository.findById(check_name).orElseThrow());
        uslugacheck.setCheck2(checkRepository.findById(usluga_name).orElseThrow());
        uslugaCheckRepository.save(uslugacheck);
        return("redirect:/uslugacheck/details/" + uslugacheck.getId());
    }

    @GetMapping("/details/{id}")
    public String zooDetails(Model model,
                             @PathVariable long id) {
        UslugaCheck uslugacheck = uslugaCheckRepository.findById(id).orElseThrow();
        model.addAttribute("uslugacheckUGU", uslugacheck);
        return ("/uslugacheck/details");
    }
    @GetMapping("/delete/{id}")
    public String zooDelete(@PathVariable long id) {
        uslugaCheckRepository.deleteById(id);
        return("redirect:/uslugacheck");
    }
}