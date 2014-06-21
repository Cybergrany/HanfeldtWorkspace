package com.hanfeldt.game.menu;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.menu.screen.MenuScreen;
import com.hanfeldt.game.menu.screen.MenuScreenOption;
import com.hanfeldt.game.menu.screen.MenuScreenOptionAction;
import com.hanfeldt.game.state.OptionState;
import com.hanfeldt.game.state.Playing;

/**
 * We need a splash screen.. I guess...
 * Actually, nevermind. It turned into a main menu.
 * @author David Ofeldt
 *
 */
public class SplashScreen extends MenuScreen{
	
	private static final int optionsX = 10;
	private static MenuScreenOption[] options = new MenuScreenOption[] {new MenuScreenOption("Start Game", optionsX, 80, MenuScreenOptionAction.startGame),
																 new MenuScreenOption("Options", optionsX, 100 , MenuScreenOptionAction.openOptions), 
																 new MenuScreenOption("Load Game", optionsX, 120, MenuScreenOptionAction.loadGame),
																 new MenuScreenOption("Quit", optionsX, 140, MenuScreenOptionAction.quitGame)};
	
	public SplashScreen(String backgroundPath){
		super(backgroundPath, options);
		Main.splashShowing = true;
	}
	
	public void tick() {
//		Rectangle mouseRect = new Rectangle(Main.mouseX, Main.mouseY, 1, 1);
//		for(int i=0; i<options.length; i++) {
//			options[i].selected = options[i].getBounds().intersects(mouseRect);
//		}
	}
	
	
}
