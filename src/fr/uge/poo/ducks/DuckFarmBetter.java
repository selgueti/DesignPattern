package fr.uge.poo.ducks;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class DuckFarmBetter {
	private final ArrayList<DuckFactory> factorys = new ArrayList<>();
	
	
	public void load() {
		ServiceLoader<DuckFactory> loader = ServiceLoader.load(DuckFactory.class);
		for(DuckFactory duckFactory : loader) {
			factorys.add(duckFactory);
		}
	}
		
	public List<Duck> createTreeEach(List<String> names){
		return names.stream().flatMap(name -> factorys.stream().map(f -> f.withName(name))).toList();
	}
		
	
	public static void main(String[] args) {
		
		List<Duck> ducks;
		ArrayList<String> names = new ArrayList<String>();
		DuckFarmBetter dfb = new DuckFarmBetter();
		
		names.add("Riri");
		names.add("Fifi");
		names.add("Loulou");
		
		dfb.load();
		
		ducks = dfb.createTreeEach(names);
		
		ducks.forEach(d -> System.out.println(d.quack()));
	}	
}
