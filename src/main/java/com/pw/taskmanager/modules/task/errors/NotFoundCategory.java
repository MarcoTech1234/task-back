package com.pw.taskmanager.modules.task.errors;

import com.pw.taskmanager.modules.shared.errors.ApplicationException;
import io.swagger.v3.oas.annotations.media.Schema;


public class NotFoundCategory extends ApplicationException {
    public NotFoundCategory() {
        super("Categoria não encontrada", 404);
    }
}
