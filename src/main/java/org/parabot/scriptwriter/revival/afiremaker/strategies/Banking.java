package org.parabot.scriptwriter.revival.afiremaker.strategies;

import org.parabot.core.ui.Logger;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.parabot.scriptwriter.revival.afiremaker.core.Core;
import org.parabot.scriptwriter.revival.afiremaker.data.Constants;
import org.rev317.min.api.methods.Bank;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.methods.Menu;
import org.rev317.min.api.methods.SceneObjects;
import org.rev317.min.api.wrappers.SceneObject;

public class Banking implements Strategy {
    @Override
    public boolean activate() {
        return Inventory.getCount(Core.getSettings().getLogId()) == 0;
    }

    @Override
    public void execute() {
        if(Bank.isOpen()) {
            if(Bank.getCount(Core.getSettings().getLogId()) == 0) {
                Logger.addMessage("-------- Out of logs, stopping script --------");
                Core.stopScript();
                return;
            }
            if(Inventory.getCount(Core.getSettings().getLogId()) == 0) {
                Menu.clickButton(Constants.DEPOSIT_ALL_ID);
                Time.sleep(new SleepCondition() {
                    @Override
                    public boolean isValid() {
                        return Inventory.isEmpty();
                    }
                },2000);
            }
            if(Inventory.isEmpty()) {
                Menu.clickButton(5387);
                Time.sleep(1000);
                Bank.withdraw(Core.getSettings().getLogId(), 28, 1000);
                Time.sleep(new SleepCondition() {
                    @Override
                    public boolean isValid() {
                        return Inventory.isFull();
                    }
                }, 3000);
                if(Inventory.isFull()) {
                    Bank.close();
                }
            }
        } else {
            SceneObject bank = SceneObjects.getClosest(Constants.BANK_ID);
            Bank.open(bank);
            Time.sleep(new SleepCondition() {
                @Override
                public boolean isValid() {
                    return Bank.isOpen();
                }
            }, 4000);
        }
    }

}