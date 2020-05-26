package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import modelos.Movimiento;
import modelos.Producto;

public class ConexionBD {
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	private Connection conectar() {
		final String URL = "jdbc:sqlite::resource:basededatos/inventario_todo1.db";
		Connection conexion = null;

		try {
			conexion = DriverManager.getConnection(URL);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return conexion;
	}
	

	public Producto buscarProductoPorId(int id) {
		final String SQL = "SELECT * FROM Productos WHERE idProducto = ?";
		Connection conexion = conectar();
		
		try {
			PreparedStatement pstmt = conexion.prepareStatement(SQL);
			pstmt.setInt(1, id);
			
			ResultSet rst = pstmt.executeQuery();
			
			if (rst.next()) {
				Producto producto = new Producto();
				producto.setId(id);
				producto.setNombre(rst.getString("nombre"));
				producto.setDescripcion(rst.getString("descripcion"));
				producto.setPrecio(rst.getDouble("precio"));
				producto.setCantidad(rst.getInt("cantidad"));				
				conexion.close();				
				return producto;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conexion.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}


	public void crearProducto(Producto producto) {
		final String SQL = "INSERT INTO Productos VALUES (?, ?, ?, ?, ?)";
		Connection conexion = conectar();
		
		try {
			PreparedStatement pstmt = conexion.prepareStatement(SQL);
			pstmt.setInt(1, producto.getId());
			pstmt.setString(2, producto.getNombre());
			pstmt.setString(3, producto.getDescripcion());
			pstmt.setInt(4, producto.getCantidad());
			pstmt.setDouble(5, producto.getPrecio());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conexion.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


	public void actualizarProducto(Producto producto) {
		final String SQL = "UPDATE Productos SET nombre = ?, descripcion = ?, precio = ?, cantidad = ? WHERE idProducto = ?";
		Connection conexion = conectar();
		
		try {
			PreparedStatement pstmt = conexion.prepareStatement(SQL);
			pstmt.setString(1, producto.getNombre());
			pstmt.setString(2, producto.getDescripcion());
			pstmt.setDouble(3, producto.getPrecio());
			pstmt.setInt(4, producto.getCantidad());
			pstmt.setInt(5, producto.getId());
			
			pstmt.executeUpdate();			
			conexion.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	public void eliminarProductoPorId(int id) {
		final String SQL = "DELETE FROM Productos WHERE idProducto = ?";
		Connection conexion = conectar();
		
		try {
			PreparedStatement pstmt = conexion.prepareStatement(SQL);
			pstmt.setInt(1, id);			
			pstmt.executeUpdate();			
			conexion.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			try {
				conexion.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


	public List<Producto> obtenerProductos() {
		final String SQL = "SELECT * FROM Productos";
		List<Producto> productos = new ArrayList<>();
		Connection conexion = conectar();
		
		try {
			PreparedStatement pstmt = conexion.prepareStatement(SQL);			
			ResultSet rst = pstmt.executeQuery();
			Producto producto;
			
			while (rst.next()) {
				producto = new Producto();
				producto.setId(rst.getInt("idProducto"));
				producto.setNombre(rst.getString("nombre"));
				producto.setDescripcion(rst.getString("descripcion"));
				producto.setCantidad(rst.getInt("cantidad"));				
				producto.setPrecio(rst.getDouble("precio"));
				productos.add(producto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			try {
				conexion.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return productos;
	}


	public void crearMovimiento(Movimiento movimiento) {
		final String SQL = "INSERT INTO Movimientos VALUES (?, ?, ?, ?, ?)";
		Connection conexion = conectar();
		
		try {
			PreparedStatement pstmt = conexion.prepareStatement(SQL);
			
			Date fechaActual = new Date();
			
			pstmt.setInt(1, movimiento.getId());
			pstmt.setInt(2, movimiento.getIdProducto());
			pstmt.setInt(3, movimiento.getCantidad());
			pstmt.setString(4, sdf.format(fechaActual));
			pstmt.setString(5, movimiento.getTipo());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conexion.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<Movimiento> obtenerMovimientos(String tipo) {
		final String SQL = "SELECT * FROM Movimientos WHERE tipo = ?";
		List<Movimiento> movimientos = new ArrayList<>();
		Connection conexion = conectar();
		
		try {
			
			PreparedStatement pstmt = conexion.prepareStatement(SQL);
			pstmt.setString(1, tipo);
			
			ResultSet rst = pstmt.executeQuery();
			Movimiento movimiento;			
			
			while (rst.next()) {
				movimiento = new Movimiento();
				movimiento.setId(rst.getInt("idMovimiento"));
				movimiento.setIdProducto(rst.getInt("idProducto"));
				movimiento.setCantidad(rst.getInt("cantidad"));
				movimiento.setFecha(sdf.parse(rst.getString("fecha")));
				movimiento.setTipo(rst.getString("tipo"));
				movimientos.add(movimiento);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} catch (ParseException e) {
			e.printStackTrace();
			
		} finally {
			try {
				conexion.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return movimientos;
	}

}
