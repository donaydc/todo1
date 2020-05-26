package modelos;

import java.util.List;

import conexion.ConexionBD;

public class GestionInventario {
	
	private ConexionBD conexionBD;

	public GestionInventario() {
		conexionBD = new ConexionBD();
	}

	public Producto buscarProductoPorId(int id) {
		return conexionBD.buscarProductoPorId(id);
	}

	public void crearProducto(Producto producto) {
		conexionBD.crearProducto(producto);
	}

	public void actualizarProducto(Producto producto) {
		conexionBD.actualizarProducto(producto);
	}

	public void eliminarProductoPorId(int id) {
		conexionBD.eliminarProductoPorId(id);
	}

	public Producto[] obtenerProductos() {
		List<Producto> productos = conexionBD.obtenerProductos();
		
		Producto[] productosCopia = new Producto[productos.size()];
		productos.toArray(productosCopia);

		return productosCopia;
	}

	public void crearMovimiento(Movimiento movimiento) {
		conexionBD.crearMovimiento(movimiento);		
	}

}