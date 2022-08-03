package com.proyecto.bootcamp.Services.DTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.proyecto.bootcamp.Exceptions.ValidationGroups.Create;
import com.proyecto.bootcamp.Exceptions.ValidationGroups.Update;

public class BasicDTO<ID> {
    @Null(groups = Create.class)
    @NotNull(groups = {Update.class})
    private ID id;

    public ID getId() {
        return this.id;
    }

    public void setId(ID id) {
        this.id = id;
    }
}
