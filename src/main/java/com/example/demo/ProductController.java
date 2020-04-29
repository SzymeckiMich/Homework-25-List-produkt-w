package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static java.util.Objects.nonNull;


@Controller
@RequestMapping("/")
public class ProductController {

    private ProductsRepository productsRepository;

    public ProductController(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @GetMapping
    String home() {
        return "addProduct";
    }

    @PostMapping("/add")
    String add(@RequestParam(required = true) String name, @RequestParam(required = true) String price) {
        double priceValue;
        String prodName = name;

        if (!nonNull(prodName)) return "nullError";
        try {
            priceValue = Double.parseDouble(price);
        } catch (NumberFormatException ex) {
            return "numberFormatError";
        }
        if (priceValue == 0) {
            return "zeroPriceError";
        } else {
            productsRepository.addProduct(new Product(prodName, priceValue));
            return "success";
        }
    }

    @GetMapping("/list")
    String list(Model model) {
        String sum = "Suma: " + String.valueOf(productsRepository.getPriceSum() + "zł");
        model.addAttribute("list", productsRepository.getAll());
        model.addAttribute("priceSum", sum);
        return "list";
    }


}
