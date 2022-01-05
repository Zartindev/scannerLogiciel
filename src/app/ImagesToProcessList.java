package app;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Liste d'images à traiter. 
 * On peut ajouter les noms des images un par un, ou bien donner un nom de dossier, avec une extension,
 * pour ajouter toutes les images du dossier, possédant la bonne extension.
 * @author thierrybrouard
 *
 */
public class ImagesToProcessList implements Iterable {

	private LinkedList<String> image_list;
	
	public ImagesToProcessList() {
		super();
		this.image_list = new LinkedList<String>();
	}

	/** 
	 * Ajoute une image dans la liste, d'après son nom
	 * @param image_name nom du fichier à ajouter
	 */
	public void addImageName(String image_name) {
		this.image_list.add(image_name);
	}

	/**
	 * Ajoute toutes les images d'un dossier dans la liste
	 * @param image_path chemin d'accès au dossier
	 */
	public void addImagesFromFolder(String image_path) {
		File dir = new File(image_path);
		File[] liste = dir.listFiles();
		for (File item : liste) {
			if (item.isFile()) {
				addImageName(item.getAbsolutePath());
			}
		}
	}

	@Override
	/**
	 * Renvoie un itérateur pour parcourir la liste des noms d'images.
	 */
	public Iterator iterator() {
		return this.image_list.iterator();
	}

	
}
