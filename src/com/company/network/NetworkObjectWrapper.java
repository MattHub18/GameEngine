package com.company.network;

import java.io.Serializable;

public interface NetworkObjectWrapper extends Serializable {
    long serialVersionUID = 1L;

    void setObjectGame(Object obj);

    Object getObjectGame();
}
