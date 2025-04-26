package controlador;

import java.util.ArrayList;
import Modelo.Producto;

public class ProductoControlador {
	private ArrayList<Producto> productos;

    public ProductoControlador() {
        productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public ArrayList<Producto> listarProductos() {
        return productos;
    }

    public void eliminarProducto(Producto producto) {
        productos.remove(producto);
    }
}
