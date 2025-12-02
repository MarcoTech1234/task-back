package com.pw.taskmanager.modules.task.errors;

import com.pw.taskmanager.modules.shared.errors.ApplicationException;

public class NotFoundTask extends ApplicationException {
    public NotFoundTask() {
        super("Task não encontrada", 404);
    }
}
