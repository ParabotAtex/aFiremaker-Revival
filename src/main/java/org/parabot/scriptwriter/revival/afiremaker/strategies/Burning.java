package org.parabot.scriptwriter.revival.afiremaker.strategies;

import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.parabot.scriptwriter.revival.afiremaker.core.Core;
import org.parabot.scriptwriter.revival.afiremaker.data.Constants;
import org.rev317.min.api.methods.*;
import org.rev317.min.api.wrappers.SceneObject;
import org.rev317.min.api.wrappers.Tile;

import static org.rev317.min.api.methods.Walking.walkTo;

public class Burning implements Strategy {

    @Override
    public boolean activate() {
        return Players.getMyPlayer().getAnimation() != Constants.BURNING_ANIMATION_ID
                && Inventory.getCount(Core.getSettings().getLogId()) > 0;
    }

    @Override
    public void execute() {
        SceneObject fire = SceneObjects.getClosest(Constants.FIRE_ID);
        final Tile burningSpot = new Tile(fire.getLocation().getX() - 1,fire.getLocation().getY());
        if(Players.getMyPlayer().getLocation().equals(burningSpot)) {
            if(Players.getMyPlayer().getAnimation() != Constants.BURNING_ANIMATION_ID) {
                Inventory.getItem(Core.getSettings().getLogId()).interact(Items.Option.USE);
                Time.sleep(500);
                Menu.sendAction(62, fire.getHash(), fire.getLocalRegionX(), fire.getLocalRegionY());
                Time.sleep(new SleepCondition() {
                    @Override
                    public boolean isValid() {
                        return Players.getMyPlayer().getAnimation() == Constants.BURNING_ANIMATION_ID;
                    }
                }, 2000);
            }
        } else {
            walkTo(burningSpot);
            Time.sleep(new SleepCondition() {
                @Override
                public boolean isValid() {
                    return Players.getMyPlayer().getLocation().equals(burningSpot);
                }
            },4000);
        }
    }
}