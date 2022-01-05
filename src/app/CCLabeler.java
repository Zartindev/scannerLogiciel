package app;

import ij.IJ;
import ij.ImagePlus;
import ij.measure.ResultsTable;
import ij.plugin.filter.ParticleAnalyzer;
import ij.process.ImageProcessor;

public class CCLabeler {

    private MeasuresList measures_list = null;
    private String image_name = null;

    /**
     * Binarise l'image, fond noir, caractères blancs.
     *
     * @param imOriginale Image à binariser
     * @return image binaire
     */
    private ImagePlus binarise(ImagePlus imOriginale) {
        ImagePlus imBinary = imOriginale.duplicate();
        imBinary.getProcessor().setAutoThreshold("Otsu");
        imBinary.getProcessor().autoThreshold();
        return imBinary;
    }


    /**
     * Détection des composantes connexes dans l'image.
     *
     */
    private ResultsTable findParticles(ImagePlus imBinary) {

        int options = ij.plugin.filter.ParticleAnalyzer.SHOW_NONE;
        int measurements = ij.measure.Measurements.AREA + ij.measure.Measurements.CENTROID +
                ij.measure.Measurements.RECT;

        ResultsTable data = new ResultsTable();
        ParticleAnalyzer pa = new ParticleAnalyzer(options, measurements, data,0,100000);
        pa.analyze(imBinary);

        return (data);
    }


    private MeasuresList getCCInformations(ResultsTable data, int idPage) {
        // allocation structure
        MeasuresList measures_list = new MeasuresList(this.image_name);
        // recupère les données dans des tableaux
        double[] cenx = data.getColumnAsDoubles(ResultsTable.X_CENTROID);
        double[] ceny = data.getColumnAsDoubles(ResultsTable.Y_CENTROID);
        double[] area = data.getColumnAsDoubles(ResultsTable.AREA);
        double[] xmin = data.getColumnAsDoubles(ResultsTable.ROI_X);
        double[] ymin = data.getColumnAsDoubles(ResultsTable.ROI_Y);
        double[] height = data.getColumnAsDoubles(ResultsTable.ROI_HEIGHT);
        double[] width = data.getColumnAsDoubles(ResultsTable.ROI_WIDTH);

        // parcours des résultats
        for (int idx = 0; idx < cenx.length; idx++) {
            Measure measure = new Measure(area[idx], cenx[idx], ceny[idx], (int)xmin[idx], (int)ymin[idx], (int)width[idx], (int)height[idx], idPage);
            measures_list.add(measure);
        }
        this.measures_list = measures_list;
        return measures_list;
    }


    /**
     * Liste des mesures.
     * Cette liste contient les mesures faites sur l'image. Elle est vidée à chaque nouveau calcul,
     * et ne contient donc que les mesures réalisées par le dernier appel à la fonction process().
     *
     * @return MeasuresList
     * @see MeasuresList
     * @see CCLabeler
     */
    public MeasuresList getMeasures() {
        return this.measures_list;
    }

    /**
     * Fabrique l'image de sortie.
     */
    private void generate_output(ImagePlus imInput) {

        // copie de l'originale, améliore la dynamique
        ImagePlus imOutput = imInput.duplicate();
        ImageProcessor ip = imOutput.getProcessor();
        ip.gamma(0.41);
        imOutput.setProcessor(ip);

        // dessine les bbox et les centres
        ImageProcessor ipOut = imOutput.getProcessor();
        ipOut.setColor(java.awt.Color.RED);
        int delta = 5; // taille croix
        int width = imOutput.getWidth();
        int height = imOutput.getHeight();

        for (Measure info : this.measures_list) {
            int xcell = (int) info.getCentre_x();
            int ycell = (int) info.getCentre_y();
            // coordonnées croix
            int x1 = Math.max(0, xcell - delta);
            int x2 = Math.min(width, xcell + delta);
            int y3 = Math.max(0, ycell - delta);
            int y4 = Math.min(height, ycell + delta);
            // trace centre
            ipOut.drawLine(x1, ycell, x2, ycell);
            ipOut.drawLine(xcell, y3, xcell, y4);
            // trace bounding box
            ipOut.drawRect(info.getXstart(), info.getYstart(), info.getWidth(), info.getHeight());

        }
		IJ.save( imOutput, "output.jpg" );
    }


    /**
     * Inventorie toutes les composantes de l'image, en vue de les compter.
     *
     */
    public void process(String image_name,int idPage) {

        this.image_name = image_name;
        // Load photo
        ImagePlus imOriginale = IJ.openImage(this.image_name);
        //System.out.println("[" + imOriginale.getWidth() + " x " + imOriginale.getHeight() + "] " + this.image_name);

        // Preprocessing 3 - binariser
        ImagePlus imBinary = this.binarise(imOriginale);
		//IJ.save( imBinary, "3-bin.jpg" );

        // composantes connexes
        ResultsTable data = findParticles(imBinary);

        // acquisition des mesures
        MeasuresList measure_list = getCCInformations(data, idPage);

        // affiche les mesures
       // System.out.println(measure_list);

        // récupération des données dans la liste des mesures
/*
        for (Object o : measure_list) {
            Measure m =  (Measure)o;
            System.out.println(m.getCentre_x());
        }
*/
        // enregistre l'image avec centres et bounding boxes
        //this.generate_output(imOriginale);
    }
}
