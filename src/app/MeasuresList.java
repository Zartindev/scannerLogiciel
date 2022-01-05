package app;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * List of measures. 
 * 
 * This iterable object contains objects of class Measure in order to store all the
 * data concerning the connex components founded in the analyzed picture. 
 * 
 * @author thierrybrouard
 * @see Measure
 * @see CCLabeler
 */
public class MeasuresList implements Iterable<Measure> {

	private final String image_name;
	LinkedList<Measure> measures;


	/**
	 * Create e list of measures.
	 * When created, the list store the name of the image associated to the measures. 
	 * @param name name of the image on which the measures have been made 
	 */
	public MeasuresList(String name) {
		super();
		this.image_name = name;
		this.measures = new LinkedList<>();
	}

	/**
	 * Add data to the set of measures.
	 * @param measure data to store 
	 * @see Measure
	 */
	public void add(Measure measure) {
		this.measures.add(measure);
	}


	public int get_nb_measures() {
		return this.measures.size();
	}
	
	@Override
	public Iterator iterator() {
		return this.measures.iterator();
	}

	@Override
	public String toString() {
		return ""+measures;
	}
}
