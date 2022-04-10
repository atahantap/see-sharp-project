package domain.game;

import java.util.HashMap;

public interface IRunModeListener {

	public void onClickEvent(HashMap<String, Double> runSettings, String username);
}
