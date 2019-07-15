package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sun.java2d.pipe.SpanShapeRenderer;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class HomeController {

    @Autowired
    ToDoRepository toDoRepository;

    @RequestMapping("/")
    public String listToDO(Model model){
        model.addAttribute("toDos", toDoRepository.findAll());
        return "list";
    }

    @GetMapping("/add")
    public String toDOForm(Model model){
        model.addAttribute("toDo", new ToDo());
        return "todoform";
    }

    @PostMapping("/process")
    public String processToDoForm(@Valid ToDo toDo, BindingResult result, @RequestParam("dueDate") String dueDate){
        if(result.hasErrors()){
            return "todoform";
        }
        Date date= new Date();

        try{
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dueDate);
        }

        catch(Exception e){
            e.printStackTrace();
        }
        toDo.setDueDate(date);
        toDoRepository.save(toDo);
        return "redirect:/";
    }

}
