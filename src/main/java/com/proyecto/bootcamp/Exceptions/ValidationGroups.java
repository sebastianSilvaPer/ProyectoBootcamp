package com.proyecto.bootcamp.Exceptions;

import javax.validation.groups.Default;

public final class ValidationGroups {

    private ValidationGroups(){}
    
    public interface Create extends Default {};
    public interface Update extends Default {};
    public interface Delete extends Default {};
    public interface UpdateNoId extends Default {};
}
