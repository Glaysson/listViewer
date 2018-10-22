package listViewer.controller;

import listViewer.entity.ListElement;
import listViewer.service.ListElementService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Optional;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private final ListElementService service;

    @Autowired
    public RestController(ListElementService service) {
        this.service = service;
    }

    @RequestMapping(value = "/add-text" , method = RequestMethod.POST)
    @ResponseBody
    public String addText(@RequestBody String text){
        HashMap<String, String> map = new HashMap<>();
        System.out.println(text);
        ListElement element = new ListElement();
        element.setText(text.replace("=",""));

        service.save(element);
        JSONObject object = new JSONObject();
        object.put(String.valueOf(element.getId()),element.getText());
        System.out.println(object);

        return object.toString();
    }

    @RequestMapping(value = "/edit",consumes = "application/json",method = RequestMethod.POST)
    public String edit(@RequestBody String text){

        JSONObject jsonObject = new JSONObject(text);
        Optional<ListElement> element = service.findById(Integer.parseInt(jsonObject.getString("currentValue")));
        element.ifPresent(listElement -> {
            listElement.setText(jsonObject.getString("value"));
            service.save(element.get());
        });
        return element.get().getText();
    }

    @RequestMapping(value = "/delete-row", method = RequestMethod.POST)
    public void  delete(@RequestBody String text){
        Optional<ListElement> element = service.findById(Integer.parseInt(text.replace("=","")));
        service.delete(element.get());
    }

    @RequestMapping(value = "/move-up", method = RequestMethod.POST)
    public void moveUp(@RequestBody String number){
        System.out.println(number);
        Integer previous = service.getPrevious(Integer.parseInt(number));
        swap(number, previous);
    }

    @RequestMapping(value = "/move-down", method = RequestMethod.POST)
    public void moveDown(@RequestBody String number){
        System.out.println(number);
        Integer next = service.getNext(Integer.parseInt(number));
        swap(number, next);
    }

    private void swap(@RequestBody String number, Integer next) {
        if (next != null) {
            Optional<ListElement> curr = service.findById(Integer.parseInt(number));
            Optional<ListElement> nextElem = service.findById(next);
            String temp = curr.get().getText();
            curr.get().setText(nextElem.get().getText());
            nextElem.get().setText(temp);
            service.save(curr.get());
            service.save(nextElem.get());
        }
    }
}
