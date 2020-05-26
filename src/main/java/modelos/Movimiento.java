package modelos;

import java.util.Date;

public class Movimiento {
	
	private static int contadorIds = 1;	
	private int id;
	private int idProducto;
	private int cantidad;
	private Date fecha;
	private String tipo;
	
	public Movimiento() {
		id = contadorIds;
		++contadorIds;
	}

	public Movimiento(int idProducto, int cantidad, String tipo) {
		this();
		this.fecha = new Date();
		this.idProducto = idProducto;
		this.cantidad = cantidad;
		this.tipo = tipo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
