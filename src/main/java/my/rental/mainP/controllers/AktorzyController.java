package my.rental.mainP.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/aktorzy")
public class AktorzyController {
	
	
	 @RequestMapping(value="/{imieAktora}", method=RequestMethod.GET)
	  public String getSpittle(@PathVariable("imieAktora") String imieAktora,
	          Model model) {
		 
		 System.out.println("dostalem: " + imieAktora);
	  //  model.addAttribute(spitterService.getSpittleById(id));
	   // return "spittles/view";
	    return "home";
	 }

}
