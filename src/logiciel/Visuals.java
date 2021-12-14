package logiciel;

import javafx.scene.control.Button;

public class Visuals extends MainLog {
	
	// METHODES ----------------------------------------------------------------------------------->

		/** 
		 *  {@link #visualButton()}
		 *   Set all the main buttons
		 * @param button
		 * @see visualButton() Use this method to set the main buttons
		 */
		public static void visualMainButtons(Button button) {

			button.setMinWidth(50);
			button.setMinHeight(50);
			button.setStyle(visualButton());
		}

		/**
		 * {@link #visualAdminButton()} Set the admin buttons
		 * 
		 * @param button
		 * @see visualAdminButton() Use this method to set the buttons which are used to
		 *      the admin part (the 2 top right)
		 */
		public static void visualAdminButtons(Button button) {

			button.setMinHeight(20);
			button.setStyle(visualAdminButton());
		}

		/**
		 * Return all the visual CSS characteristics of main Buttons.
		 * 
		 * @return a String contains all the -fx visuals
		 */
		public static String visualButton() {

			return "" + "-fx-background-color:" +  "-fx-outer-border;" + "-fx-background-radius: 15px, 3px, 2px, 1px;"
					+ "-fx-font-weight: bold;" + "-fx-text-fill: #b33939;" + "-fx-font-size: 23;";
		}

		/**
		 * Return all the visual CSS characteristics of admin Buttons.
		 * 
		 * @return a String contains all the -fx visuals
		 */

		public static String visualAdminButton() {

			return "-fx-background-color: -fx-outer-border; " + "-fx-background-radius: 0px, 3px, 2px, 1px; "
					+ "-fx-font-weight: bold; " + "-fx-text-fill: #b33939 ; " + "-fx-font-size: 15;";
		}


}
