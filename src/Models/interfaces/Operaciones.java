package Models.interfaces;

import java.util.ArrayList;

public interface Operaciones<T,V> {
    void agregar(ArrayList<T> array, V elemento);
    void editar(ArrayList<T> array, int index, V elemento);
    void eliminar(ArrayList<T> array, int index);
    void eliminarTodo(ArrayList<T> array);
}
