package com.example.md5ss1csdlorm.controller;

import com.example.md5ss1csdlorm.model.dto.MusicDtoForm;
import com.example.md5ss1csdlorm.model.service.IMusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@PropertySource("classpath:upload.properties")
public class MusicController {
    @Autowired
    private IMusicService iMusicService;
    @GetMapping
    public String home(Model model){
        model.addAttribute("list",iMusicService.findAll());
        return "home";
    }
    @GetMapping("/add")
    public ModelAndView add(){
        return new ModelAndView("add","music",new MusicDtoForm());
    }
    @GetMapping("/edit/{id}")
    public ModelAndView findById(@PathVariable Long id){
        Long id1 = id;
        return new ModelAndView("edit","music",iMusicService.findById(id));
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id ){
        iMusicService.delete(id);
        return "redirect:/";
    }
    @PostMapping("/add")
    public String doAdd(@ModelAttribute MusicDtoForm musicDtoForm){
        iMusicService.save(musicDtoForm);
        return "redirect:/";
    }
    @PostMapping("/update")
    public String doUpdate(@ModelAttribute MusicDtoForm musicDtoForm){
        iMusicService.save(musicDtoForm);
        return "redirect:/";
    }
}
