package com.company.commands.game_command;

import com.company.commands.Command;
import com.company.entities.human.combat.CombatInterface;

public class AttackCommand extends Command {

    public AttackCommand(byte type, int key, byte mode) {
        super(type, key, mode);
    }

    @Override
    public void execute(Object element) {
        ((CombatInterface) element).meleeAttack();
    }
}
