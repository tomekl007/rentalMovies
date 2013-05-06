package my.rental.mainP.controllers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import my.rental.mainP.domain.Film;
import my.rental.mainP.domain.Wypozyczenie;
import my.rental.mainP.services.RentalService;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.googlecode.charts4j.Color;
import com.googlecode.charts4j.GChart;
import com.googlecode.charts4j.GCharts;
import com.googlecode.charts4j.PieChart;
import com.googlecode.charts4j.Slice;


@Controller
@RequestMapping("/gcharts")
public class GoogleChartController {
	
	private RentalService rentalService;
	
	 @Inject
	  public GoogleChartController(RentalService rentalService) {
		  System.out.println("injecting rentalService : " + rentalService);
	    this.rentalService = rentalService;
	  }
	
	@RequestMapping(value="/piechart",  method = RequestMethod.GET)
	public String drawPiechart(ModelMap model){
		Slice s1 = Slice.newSlice(15, Color.newColor("CACACA"), "mac", "Mac");
		Slice s2 = Slice.newSlice(50, Color.newColor("DF7417"), "windows", "windows");
		Slice s3 = Slice.newSlice(25, Color.newColor("951800"), "Linux", "linux");
		Slice s4 = Slice.newSlice(10, Color.newColor("01A1DB"), "Others", "Others");
		
		PieChart pieChart = GCharts.newPieChart(s1,s2,s3,s4);
		pieChart.setTitle("Google Pie Chart", Color.BLACK,15);
		pieChart.setSize(500, 240);
		pieChart.setThreeD(true);
		
		model.addAttribute("pieUrl", pieChart.toURLString());
		
		return "GPieChart";
		
		
	}
	
	@RequestMapping(value="/stats",  method = RequestMethod.GET)
	public String drawRentalStats(ModelMap model){
		
		List<Wypozyczenie> wypozyczenia = rentalService.getAllWypozyczenia();
		
		Map<Film, Integer> stats = new HashMap<Film, Integer>();
		
		int index = 0;
		for(Wypozyczenie w : wypozyczenia){
			
		Film f = wypozyczenia.get(index).getPlyta().getFilm();
		
		if (stats.containsKey(f) ){
			Integer count = stats.get(f);
			count++;
			stats.put(f, count);
		}else{
			stats.put(f, 1);
		}
		
		
		index++;
		}
		
		int totalNumber = 0;
		for(Integer count : stats.values()){
			totalNumber+=count;
		}
		int step = 100/totalNumber;
		
		
		System.out.println("stats after count : " + stats + " step  " + step);
		
		index = 0;
		for(Film f : stats.keySet()){
			//Film f = wypozyczenia.get(index).getPlyta().getFilm();
			Integer nr = stats.get(f);
			Integer percentage = nr*step;
			
			stats.put(f,percentage);
			index++;
		}
		
		System.out.println("after couted percentage : " + stats);
		
		List<Slice> slices = new LinkedList<Slice>();
		List<Color> colors = new LinkedList<Color>();
		colors.add(Color.newColor("CACACA"));
		colors.add(Color.newColor("DF7417"));
		colors.add(Color.newColor("951800"));
		colors.add(Color.newColor("01A1DB"));
		
		
		
		index = 0;
		for(Film f: stats.keySet()){
		
			String label = f.getTytulFilmu();
			Slice s1 = Slice.newSlice(stats.get(f), colors.get(index) , label, label);
			slices.add(s1);
			index ++;
			if(index >= 4)
				index =0;
			
		}
		
		PieChart pieChart = GCharts.newPieChart(slices);
		pieChart.setTitle("statystyki wypozyczen", Color.BLACK,15);
		pieChart.setSize(500, 240);
		pieChart.setThreeD(true);
		
		model.addAttribute("pieUrl", pieChart.toURLString());
		
		
		
		return "GPieChart";
		
		
	}
	

}
