package org.parabot.scriptwriter.revival.afiremaker.core;

import org.parabot.core.ui.Logger;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.Category;
import org.parabot.environment.scripts.Script;
import org.parabot.environment.scripts.ScriptManifest;
import org.parabot.environment.scripts.framework.Strategy;
import org.parabot.scriptwriter.revival.afiremaker.UI.GUI;
import org.parabot.scriptwriter.revival.afiremaker.data.Settings;
import org.parabot.scriptwriter.revival.afiremaker.strategies.Banking;
import org.parabot.scriptwriter.revival.afiremaker.strategies.Burning;

import java.util.ArrayList;

@ScriptManifest(author = "Atex",
        category = Category.FIREMAKING,
        description = "Burns logs at ::fossil",
        name = "aFiremaker", servers = { "Revival" },
        version = 0.1)
public class Core extends Script {
    ArrayList<Strategy> strategies = new ArrayList<>();
    private static Settings settings;
    private static Script core;
    @Override
    public boolean onExecute() {
        strategies.add(new Banking());
        strategies.add(new Burning());
        provide(strategies);
        core = this;

        GUI gui = new GUI();
        while(gui.isVisible()) {
            Time.sleep(500);
        }
        if(gui.getSettings() == null) {
            Logger.addMessage("Invalid input, stopping script");
            stopScript();
        }

        settings = gui.getSettings();

        return true;
    }

    public static Settings getSettings() {
        return settings;
    }

    public static void stopScript() {
        core.setState(STATE_STOPPED);
    }
}