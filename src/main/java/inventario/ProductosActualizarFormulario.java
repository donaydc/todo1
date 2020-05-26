package inventario;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.border.TitledBorder;

import org.apache.commons.validator.routines.DoubleValidator;
import org.apache.commons.validator.routines.LongValidator;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import modelos.Movimiento;
import modelos.Producto;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ProductosActualizarFormulario extends JInternalFrame {

	/**
	 * Serial Version ID.
	 */
	private static final long serialVersionUID = -8224002992324117103L;
	private JTextField txtId;
	private JTextField txtNombre;
	private JTextField txtDescripcion;
	private JTextField txtCantidad;
	private JTextField txtPrecio;
	private JButton btnActualizar;
	private int stock = 0;
	
	/**
	 * Create the frame.
	 */
	public ProductosActualizarFormulario(Aplicacion app, String tipoActualizacion) {
		
		Aplicacion aplicacion = app;
		String tipo = tipoActualizacion;
		
		setTitle("Productos - " + tipo);
		setClosable(true);
		setBounds(100, 80, 450, 288);

		JPanel pnlProductosActualizar = new JPanel();
		pnlProductosActualizar
				.setBorder(new TitledBorder(null, "Datos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(pnlProductosActualizar, BorderLayout.CENTER);
		pnlProductosActualizar.setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"), },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, }));

		JLabel lblId = new JLabel("ID");
		pnlProductosActualizar.add(lblId, "2, 2");

		txtId = new JTextField();
		pnlProductosActualizar.add(txtId, "12, 2, fill, default");
		txtId.setColumns(10);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = txtId.getText().trim();

				if (id.isEmpty()) {
					JOptionPane.showMessageDialog(ProductosActualizarFormulario.this, "El campo ID es obligatorio.",
							"Mensaje", JOptionPane.WARNING_MESSAGE);
					return;
				}

				Long numero = LongValidator.getInstance().validate(id);

				if (numero == null) {
					JOptionPane.showMessageDialog(ProductosActualizarFormulario.this, "El campo ID debe ser un numero.",
							"Mensaje", JOptionPane.WARNING_MESSAGE);
					return;
				}

				if (numero <= 0) {
					JOptionPane.showMessageDialog(ProductosActualizarFormulario.this,
							"El campo ID debe ser un numero positivo (mayor a cero).", "Mensaje",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				Producto producto = aplicacion.buscarProductoPorId(numero.intValue());

				if (producto == null) {
					JOptionPane.showMessageDialog(ProductosActualizarFormulario.this,
							"No existe un producto con el ID especificado.", "Mensaje", JOptionPane.WARNING_MESSAGE);
					
					txtId.setEnabled(true);
					txtNombre.setText("");
					txtNombre.setEnabled(false);
					txtDescripcion.setText("");
					txtDescripcion.setEnabled(false);
					txtPrecio.setText("");
					txtPrecio.setEnabled(false);
					txtCantidad.setText("");
					txtCantidad.setEnabled(false);
					
					btnActualizar.setEnabled(false);
					btnBuscar.setEnabled(true);

					return;
				}

				txtNombre.setText(producto.getNombre());
				txtDescripcion.setText(producto.getDescripcion());
				txtPrecio.setText(String.valueOf(producto.getPrecio()));
				txtCantidad.setText("0");
				txtCantidad.setEnabled(true);
				
				btnActualizar.setEnabled(true);
				txtId.setEnabled(false);
				stock = producto.getCantidad();
			}
		});
		
		pnlProductosActualizar.add(btnBuscar, "12, 4");

		JLabel lblNombre = new JLabel("Nombre");
		pnlProductosActualizar.add(lblNombre, "2, 6");

		txtNombre = new JTextField();
		txtNombre.setEnabled(false);
		pnlProductosActualizar.add(txtNombre, "12, 6, fill, default");
		txtNombre.setColumns(10);

		JLabel lblDescripcion = new JLabel("Descripcion");
		pnlProductosActualizar.add(lblDescripcion, "2, 8");

		txtDescripcion = new JTextField();
		txtDescripcion.setEnabled(false);
		pnlProductosActualizar.add(txtDescripcion, "12, 8, fill, default");
		txtDescripcion.setColumns(10);

		JLabel lblPrecio = new JLabel("Precio");
		pnlProductosActualizar.add(lblPrecio, "2, 10");

		txtPrecio = new JTextField();
		txtPrecio.setEnabled(false);
		pnlProductosActualizar.add(txtPrecio, "12, 10, fill, default");
		txtPrecio.setColumns(10);

		JLabel lblCantidad = new JLabel(tipo);
		pnlProductosActualizar.add(lblCantidad, "2, 14");

		txtCantidad = new JTextField();
		txtCantidad.setEnabled(false);
		pnlProductosActualizar.add(txtCantidad, "12, 14, fill, default");
		txtCantidad.setColumns(10);

		btnActualizar = new JButton(tipo);
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = txtId.getText().trim();
				String nombre = txtNombre.getText().trim();
				String descripcion = txtDescripcion.getText().trim();
				String precio = txtPrecio.getText().trim();
				String cantidad = txtCantidad.getText().trim();
				
				if (nombre.isEmpty()) {
					JOptionPane.showMessageDialog(ProductosActualizarFormulario.this, "El campo Nombre es obligatorio.",
							"Mensaje", JOptionPane.WARNING_MESSAGE);
					return;
				}

				if (descripcion.isEmpty()) {
					JOptionPane.showMessageDialog(ProductosActualizarFormulario.this, "El campo Descripcion es obligatorio.",
							"Mensaje", JOptionPane.WARNING_MESSAGE);
					return;
				}

				Double valor = DoubleValidator.getInstance().validate(precio);

				if (valor == null) {
					JOptionPane.showMessageDialog(ProductosActualizarFormulario.this,
							"El campo Precio debe ser un numero.", "Mensaje", JOptionPane.WARNING_MESSAGE);
					return;
				}

				if (valor <= 0) {
					JOptionPane.showMessageDialog(ProductosActualizarFormulario.this,
							"El campo Precio debe ser un numero positivo (mayor a cero).", "Mensaje",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				Long numero = LongValidator.getInstance().validate(cantidad);

				if (numero == null) {
					JOptionPane.showMessageDialog(ProductosActualizarFormulario.this,
							"El campo " + tipo + " debe ser un numero.", "Mensaje", JOptionPane.WARNING_MESSAGE);
					return;
				}

				if (numero <= 0) {
					JOptionPane.showMessageDialog(ProductosActualizarFormulario.this,
							"El campo " + tipo + " debe ser un numero positivo (mayor a cero).", "Mensaje",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				if (numero > stock && tipo.equals("Vender")) {
					JOptionPane.showMessageDialog(ProductosActualizarFormulario.this,
							"El campo " + tipo + " no puede ser mayor al stock del producto (mayor a " + stock + ").", "Mensaje",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				int cantidadFinal = 0;
				if ( tipo.equals("Vender") ) {
					cantidadFinal = stock - Integer.parseInt(cantidad);
				} else {
					cantidadFinal = stock + Integer.parseInt(cantidad);
				}
				
				try {
					Producto producto = new Producto(Integer.parseInt(id), nombre, descripcion, Double.parseDouble(precio), cantidadFinal);
					
					aplicacion.actualizarProducto(producto);
					
					Movimiento movimiento = new Movimiento(Integer.parseInt(id), Integer.parseInt(cantidad), tipo);
					
					aplicacion.crearMovimiento(movimiento);
					
					JOptionPane.showMessageDialog(ProductosActualizarFormulario.this,
							"Los datos del producto se han actualizado.", "Informacion", JOptionPane.INFORMATION_MESSAGE);
					
				} catch (Exception error) {
					
					JOptionPane.showMessageDialog(ProductosActualizarFormulario.this,
							error, "Error", JOptionPane.INFORMATION_MESSAGE);
				
				}
				
				aplicacion.actualizarDatos();
				
				txtId.setEnabled(true);
				txtNombre.setText("");
				txtNombre.setEnabled(false);
				txtDescripcion.setText("");
				txtDescripcion.setEnabled(false);
				txtPrecio.setText("");
				txtPrecio.setEnabled(false);
				txtCantidad.setText("");
				txtCantidad.setEnabled(false);
				
				btnActualizar.setEnabled(false);
				btnBuscar.setEnabled(true);
			}
		});
		btnActualizar.setEnabled(false);
		pnlProductosActualizar.add(btnActualizar, "12, 20");

	}
}
