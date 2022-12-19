package com.example.bigsevaup.Controller;

import com.example.bigsevaup.Model.*;
import com.example.bigsevaup.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/productcheck")
public class ProductCheckController {
    @Autowired
    ProductCheckRepository productCheckRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CheckRepository checkRepository;

    @GetMapping("")
    public String zooMain(Model model) {
        Iterable<ProductCheck> listProductCheck = productCheckRepository.findAll();
        model.addAttribute("listProductCheck", listProductCheck);
        return "productcheck/index";
    }

    @GetMapping("/add")
    public String zooAddView(ProductCheck productcheck, Model model) {
        Iterable<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        Iterable<Check> checks = checkRepository.findAll();
        model.addAttribute("checks", checks);
        return "productcheck/add";
    }

    @PostMapping("/add")
    public String zooAdd(Model model, @Valid ProductCheck productcheck,
                         BindingResult result,
                         @RequestParam(name = "product_name") String pro,
                         @RequestParam(name = "check_name") String che){
        if(result.hasErrors()){
            Iterable<Product> productIterable = productRepository.findAll();
            model.addAttribute("products", productIterable);
            Iterable<Check> checkIterable = checkRepository.findAll();
            model.addAttribute("checks", checkIterable);
            return "productcheck/add";
        }
        Product products1 = productRepository.findById(Long.parseLong(pro)).orElseThrow();
        productcheck.setProduct(products1);
        Check checks1 = checkRepository.findById(Long.parseLong(che)).orElseThrow();
        productcheck.setCheck(checks1);
        productCheckRepository.save(productcheck);
        return "redirect:/productcheck";
    }
    @GetMapping("/edit/{id}")
    public String zooEdit(Model model,
                          @PathVariable long id) {
        ProductCheck productCheck = productCheckRepository.findById(id).orElseThrow();
        model.addAttribute("productCheck", productCheck);
        Iterable<Product> productIterable = productRepository.findAll();
        model.addAttribute("products", productIterable);
        Iterable<Check> checkIterable = checkRepository.findAll();
        model.addAttribute("checks", checkIterable);
        return("/productcheck/edit");
    }

    @PostMapping("/edit/{id}")
    public String zooEdit(Model model, @Valid ProductCheck productcheck, BindingResult result,
                          Long check_name, Long product_name) {
        if(result.hasErrors()){
            Iterable<Product> productIterable = productRepository.findAll();
            model.addAttribute("products", productIterable);
            Iterable<Check> checkIterable = checkRepository.findAll();
            model.addAttribute("checks", checkIterable);
            return "/productcheck/edit";
        }
        productcheck.setProduct(productRepository.findById(check_name).orElseThrow());
        productcheck.setCheck(checkRepository.findById(product_name).orElseThrow());
        productCheckRepository.save(productcheck);
        return("redirect:/productcheck/details/" + productcheck.getId());
    }

    @GetMapping("/details/{id}")
    public String zooDetails(Model model,
                             @PathVariable long id) {
        ProductCheck productcheck = productCheckRepository.findById(id).orElseThrow();
        model.addAttribute("productcheckUGU", productcheck);
        return ("/productcheck/details");
    }
    @GetMapping("/delete/{id}")
    public String zooDelete(@PathVariable long id) {
        productCheckRepository.deleteById(id);
        return("redirect:/productcheck");
    }
}