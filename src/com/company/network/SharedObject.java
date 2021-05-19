package com.company.network;

import java.io.Serializable;

public interface SharedObject extends Serializable {
    SharedObject copy(Object copy);
}
