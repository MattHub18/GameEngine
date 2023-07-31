package com.company.util;

import java.util.HashMap;

public interface Serializable {

    String serialize();

    HashMap<String, String> deserialize(String serial);
}
