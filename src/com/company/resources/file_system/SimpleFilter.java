package com.company.resources.file_system;

public class SimpleFilter extends Filter {

    @Override
    protected Object decrypt(Object data) {
        return data;
    }

    @Override
    protected Object encrypt(Object object) {
        return object;
    }
}
