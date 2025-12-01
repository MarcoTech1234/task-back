package com.pw.taskmanager.modules.task.errors;

import java.util.NoSuchElementException;

public class NotFoundCategory extends NoSuchElementException {
    public NotFoundCategory(String mgs) {
        super(mgs);
    }
}
