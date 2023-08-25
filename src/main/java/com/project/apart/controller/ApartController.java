package com.project.apart.controller;

import com.project.apart.service.ApartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ApartController {

    @Autowired
    private ApartService apartService;

    @GetMapping("")
    public String index()  {

        return "apart/index";
    }

    @GetMapping("/results")
    public String apart(@RequestParam("region") String code,@RequestParam("selectedValue") String selectedValue ,Model model) throws Exception {

        List<?> a = apartService.getCode(code,selectedValue);
        model.addAttribute("apart", a);

        return "apart/results";
    }

    @GetMapping("/rentResults")
    public String test(@RequestParam("region") String code,@RequestParam("selectedValue") String selectedValue ,Model model) throws Exception {

        List<?> a = apartService.getRentals(code,selectedValue);
        model.addAttribute("apart", a);

        return "apart/rentResults";
    }

}
