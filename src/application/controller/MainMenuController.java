package application.controller;

import application.model.GameSettings;
import application.controller.GameController;
import application.controller.SettingsMenuController;
/**
 * The first controller called by main
 *
 * This will create a GameSettings object and set it's defaults then pass it to the
 * GameController object when the start button is press. Should also handle a button press to open
 * the settingsMenu view.
 *
 */
public class MainMenuController {
	private GameSettings settings;
}
