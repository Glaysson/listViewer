package listViewer.controller;


import listViewer.entity.ListElement;
import listViewer.service.ListElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@org.springframework.stereotype.Controller
public class MainController {

    private final ListElementService listElementService;

    @Autowired
    public MainController(ListElementService listElementService) {
        this.listElementService = listElementService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<ListElement> list = listElementService.findAll();
        if (!list.isEmpty()) {
            model.addAttribute("list", list);
        }
        return "index";
    }

    @GetMapping("/index")
    public String index_slash() {
        return "redirect:/";
    }
}
