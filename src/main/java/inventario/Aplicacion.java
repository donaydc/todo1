package inventario;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import conexion.ConexionBD;
import modelos.GestionInventario;
import modelos.Movimiento;
import modelos.Producto;

import javax.swing.JDesktopPane;

import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;
import java.awt.event.ActionEvent;

public class Aplicacion {

	private JFrame frmInventario;
	private JDesktopPane dpnEscritorio;
	private GestionInventario gestionInventario;

	public JTabbedPane tabbedPane;
	public JPanel panelPro;
	public JPanel panelVen;
	public JPanel panelCom;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Aplicacion window = new Aplicacion();
					window.frmInventario.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Aplicacion() {
		initialize();
		gestionInventario = new GestionInventario();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmInventario = new JFrame();
		frmInventario.setTitle("Inventario - Todo1");
		frmInventario.setBounds(100, 100, 640, 440);
		frmInventario.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frmInventario.setLocationRelativeTo(null);

		dpnEscritorio = new JDesktopPane();
		frmInventario.setContentPane(dpnEscritorio);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setBounds(10, 10, 604, 380);
		frmInventario.getContentPane().add(splitPane);

		JPanel panelBtns = new JPanel();
		splitPane.setLeftComponent(panelBtns);
		panelBtns.setLayout(new GridLayout(4, 1, 0, 0));

		JButton btnNuevoProducto = new JButton("Nuevo Producto");
		btnNuevoProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProductosCrearFormulario productosCrearFormulario = new ProductosCrearFormulario(Aplicacion.this);
				dpnEscritorio.add(productosCrearFormulario);
				productosCrearFormulario.show();
			}
		});
		btnNuevoProducto.setBounds(new Rectangle(0, 0, 100, 100));
		panelBtns.add(btnNuevoProducto);

		JButton btnVenderProducto = new JButton("Vender Producto");
		btnVenderProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProductosActualizarFormulario productosActualizarFormulario = new ProductosActualizarFormulario(
						Aplicacion.this, "Vender");
				dpnEscritorio.add(productosActualizarFormulario);
				productosActualizarFormulario.show();
			}
		});
		panelBtns.add(btnVenderProducto);

		JButton btnAgregarProducto = new JButton("Agregar Producto");
		btnAgregarProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProductosActualizarFormulario productosActualizarFormulario = new ProductosActualizarFormulario(
						Aplicacion.this, "Agregar");
				dpnEscritorio.add(productosActualizarFormulario);
				productosActualizarFormulario.show();
			}
		});
		panelBtns.add(btnAgregarProducto);

		JButton btnEliminarProducto = new JButton("Eliminar Producto");
		btnEliminarProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProductosEliminarFormulario productosEliminarFormulario = new ProductosEliminarFormulario(
						Aplicacion.this);
				dpnEscritorio.add(productosEliminarFormulario);
				productosEliminarFormulario.show();
			}
		});
		panelBtns.add(btnEliminarProducto);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 604, 379);
		splitPane.setRightComponent(tabbedPane);

		actualizarDatos();
	}

	public void actualizarDatos() {

		panelPro = new JPanel();
		panelVen = new JPanel();
		panelCom = new JPanel();
		tabbedPane.removeAll();

		tabbedPane.addTab("Productos", null, panelPro, null);
		nuevaTabla(panelPro, new String[] { "Id", "Nombre", "Descripcion", "Cantidad", "Precio" }, "Productos");

		tabbedPane.addTab("Ventas", null, panelVen, null);
		nuevaTabla(panelVen, new String[] { "Id", "Producto", "Cantidad", "Fecha" }, "Vender");

		tabbedPane.addTab("Ingresos", null, panelCom, null);
		nuevaTabla(panelCom, new String[] { "Id", "Producto", "Cantidad", "Fecha" }, "Agregar");

	}

	private void nuevaTabla(JPanel panel, String[] columnas, String tipo) {

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 45, 594, 345);
		panel.add(scrollPane);

		JTable table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {}, columnas) {

			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		table.getColumnModel().getColumn(0).setPreferredWidth(42);
		table.getColumnModel().getColumn(2).setPreferredWidth(120);
		scrollPane.setViewportView(table);

		if (tipo.equals("Productos")) {
			agregarValoresPro(table, tipo);
		} else {
			agregarValoresMov(table, tipo);
		}
	}

	private void agregarValoresPro(JTable table, String tipo) {

		ConexionBD conexionBD = new ConexionBD();
		List<Producto> productos = conexionBD.obtenerProductos();
		Producto[] productosCopia = new Producto[productos.size()];
		productos.toArray(productosCopia);

		Producto[] listaProductos = productosCopia;
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		if (listaProductos.length > 0) {
			for (int i = 0; i < listaProductos.length; i++) {
				Producto producto = listaProductos[i];

				int id = producto.getId();
				String nombre = producto.getNombre();
				String descripcion = producto.getDescripcion();
				int cantidad = producto.getCantidad();
				double precio = producto.getPrecio();

				Object[] row = { id, nombre, descripcion, cantidad, precio };
				model.addRow(row);
			}
		}
	}

	private void agregarValoresMov(JTable table, String tipo) {

		ConexionBD conexionBD = new ConexionBD();
		List<Movimiento> movimientos = conexionBD.obtenerMovimientos(tipo);
		Movimiento[] movimientosCopia = new Movimiento[movimientos.size()];
		movimientos.toArray(movimientosCopia);

		Movimiento[] listaMovimiento = movimientosCopia;
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		if (listaMovimiento.length > 0) {
			for (int i = 0; i < listaMovimiento.length; i++) {
				
				Movimiento movimiento = listaMovimiento[i];
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				
				Producto producto = conexionBD.buscarProductoPorId(movimiento.getIdProducto());

				int id = movimiento.getId();
				int cantidad = movimiento.getCantidad();
				String fecha = sdf.format(movimiento.getFecha());

				Object[] row = { id, producto.getNombre(), cantidad, fecha };
				model.addRow(row);
			}
		}
	}

	public Producto buscarProductoPorId(int id) {
		return gestionInventario.buscarProductoPorId(id);
	}

	public void crearProducto(Producto producto) {
		gestionInventario.crearProducto(producto);
	}

	public void actualizarProducto(Producto producto) {
		gestionInventario.actualizarProducto(producto);
	}

	public void eliminarProductoPorId(int id) {
		gestionInventario.eliminarProductoPorId(id);
	}

	public Producto[] obtenerProductos() {
		return gestionInventario.obtenerProductos();
	}

	public void crearMovimiento(Movimiento movimiento) {
		gestionInventario.crearMovimiento(movimiento);
	}

}
