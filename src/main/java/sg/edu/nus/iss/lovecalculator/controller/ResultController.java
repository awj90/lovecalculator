package sg.edu.nus.iss.lovecalculator.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.iss.lovecalculator.model.Result;
import sg.edu.nus.iss.lovecalculator.service.ResultService;

@Controller
public class ResultController {
    
    @Autowired
    ResultService resultService;
    
    @GetMapping(path="/result")
    public String fetchAndShowResults(Model model, @RequestParam(required=true) String sname, @RequestParam(required=true) String fname) throws IOException {
        Optional<Result> r = resultService.getResult(sname, fname);
        model.addAttribute("response", r.get());
        return "result";
    }

    @GetMapping(path="/results")
    public String fetchAndShowAllResults(Model model) throws IOException {
        List<Result> listOfResults = resultService.getAllResults();
        model.addAttribute("listOfResults", listOfResults);
        return "results";
    }
}
