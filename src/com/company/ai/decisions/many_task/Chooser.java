package com.company.ai.decisions.many_task;

import com.company.ai.AiInterface;

public class Chooser extends Selector {

    @Override
    public void start(AiInterface entity) {
        control.getSubtasks().sort((t1, t2) -> {
            double v1 = t1.getValue(entity);
            double v2 = t2.getValue(entity);
            if (v1 > v2)
                return -1;
            else if (v1 == v2)
                return 0;
            return 1;
        });
        super.start(entity);
    }
}
