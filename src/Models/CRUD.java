package Models;

import Models.interfaces.Operaciones;

import java.util.ArrayList;

public class CRUD implements Operaciones {
    @Override
    public void agregar(ArrayList array, Object elemento) {
        array.add(elemento);
    }
    @Override
    public void editar(ArrayList array, int index, Object elemento) {
        array.set(index,elemento);
    }
    @Override
    public void eliminar(ArrayList array, int index) {
        array.remove(index);
    }
    @Override
    public void eliminarTodo(ArrayList array) {
        array.clear();
    }
}
