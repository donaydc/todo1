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

public class ProductosCrearFormulario extends JInternalFrame {

	/**
	 * Definitions
	 */
	private static final long serialVersionUID = 7218636655538886731L;
	private JTextField txtId;
	private JTextField txtNombre;
	private JTextField txtDescripcion;
	private JTextField txtPrecio;
	private JTextField txtCantidad;

	/**
	 * Create the frame.
	 */
	public ProductosCrearFormulario(Aplicacion app) {

		Aplicacion aplicacion = app;
		setTitle("Productos - Crear");
		setClosable(true);
		setBounds(100, 80, 450, 260);

		JPanel pnlProductosCrear = new JPanel();
		pnlProductosCrear
				.setBorder(new TitledBorder(null, "Datos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(pnlProductosCrear, BorderLayout.CENTER);
		pnlProductosCrear.setLayout(new FormLayout(
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
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, }));
		
		JLabel lblId = new JLabel("ID");
		pnlProductosCrear.add(lblId, "2, 2");

		txtId = new JTextField();
		pnlProductosCrear.add(txtId, "12, 2, fill, default");
		txtId.setColumns(10);

		JLabel lblNombre = new JLabel("Nombre");
		pnlProductosCrear.add(lblNombre, "2, 4");

		txtNombre = new JTextField();
		pnlProductosCrear.add(txtNombre, "12, 4, fill, default");
		txtNombre.setColumns(10);

		JLabel lblDescripcion = new JLabel("Descripción");
		pnlProductosCrear.add(lblDescripcion, "2, 6");

		txtDescripcion = new JTextField();
		pnlProductosCrear.add(txtDescripcion, "12, 6, fill, default");
		txtDescripcion.setColumns(10);

		JLabel lblPrecio = new JLabel("Precio");
		pnlProductosCrear.add(lblPrecio, "2, 8");

		txtPrecio = new JTextField();
		pnlProductosCrear.add(txtPrecio, "12, 8, fill, default");
		txtPrecio.setColumns(10);

		JLabel lblCantidad = new JLabel("Cantidad");
		pnlProductosCrear.add(lblCantidad, "2, 12");

		txtCantidad = new JTextField();
		pnlProductosCrear.add(txtCantidad, "12, 12, fill, default");
		txtCantidad.setColumns(10);

		JButton btnCrear = new JButton("Crear");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = txtId.getText().trim();
				String nombre = txtNombre.getText().trim();
				String descripcion = txtDescripcion.getText().trim();
				String precio = txtPrecio.getText().trim();
				String cantidad = txtCantidad.getText().trim();

				if (id.isEmpty()) {
					JOptionPane.showMessageDialog(ProductosCrearFormulario.this, "El campo ID es obligatorio.",
							"Mensaje", JOptionPane.WARNING_MESSAGE);
					return;
				}

				Long numero = LongValidator.getInstance().validate(id);

				if (numero == null) {
					JOptionPane.showMessageDialog(ProductosCrearFormulario.this, "El campo ID debe ser un numero.",
							"Mensaje", JOptionPane.WARNING_MESSAGE);
					return;
				}

				if (numero <= 0) {
					JOptionPane.showMessageDialog(ProductosCrearFormulario.this,
							"El campo ID debe ser un numero positivo (mayor a cero).", "Mensaje",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				Producto producto = aplicacion.buscarProductoPorId(numero.intValue());

				if (producto != null) {
					JOptionPane.showMessageDialog(ProductosCrearFormulario.this,
							"Ya existe un proveedor con el ID que se ha especificado. Intente otro ID.", "Mensaje",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				if (nombre.isEmpty()) {
					JOptionPane.showMessageDialog(ProductosCrearFormulario.this, "El campo Nombre es obligatorio.",
							"Mensaje", JOptionPane.WARNING_MESSAGE);
					return;
				}

				if (descripcion.isEmpty()) {
					JOptionPane.showMessageDialog(ProductosCrearFormulario.this,
							"El campo Descripcion es obligatorio.", "Mensaje", JOptionPane.WARNING_MESSAGE);
					return;
				}

				Double valor = DoubleValidator.getInstance().validate(precio);

				if (valor == null) {
					JOptionPane.showMessageDialog(ProductosCrearFormulario.this,
							"El campo Precio debe ser un numero.", "Mensaje", JOptionPane.WARNING_MESSAGE);
					return;
				}

				if (valor <= 0) {
					JOptionPane.showMessageDialog(ProductosCrearFormulario.this,
							"El campo Precio debe ser un numero positivo (mayor a cero).", "Mensaje",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				numero = LongValidator.getInstance().validate(cantidad);

				if (numero == null) {
					JOptionPane.showMessageDialog(ProductosCrearFormulario.this,
							"El campo Cantidad debe ser un numero.", "Mensaje", JOptionPane.WARNING_MESSAGE);
					return;
				}

				if (numero <= 0) {
					JOptionPane.showMessageDialog(ProductosCrearFormulario.this,
							"El campo Cantidad debe ser un numero positivo (mayor a cero).", "Mensaje",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				producto = new Producto(Integer.parseInt(id), nombre, descripcion, Double.parseDouble(precio),
						Integer.parseInt(cantidad));

				aplicacion.crearProducto(producto);
				
				Movimiento movimiento = new Movimiento(Integer.parseInt(id), Integer.parseInt(cantidad), "Agregar");
				
				aplicacion.crearMovimiento(movimiento);

				JOptionPane.showMessageDialog(ProductosCrearFormulario.this,
						"El producto se ha creado de forma satisfactoria.", "Mensaje", JOptionPane.INFORMATION_MESSAGE);

				aplicacion.actualizarDatos();
				
				txtId.setText("");
				txtNombre.setText("");
				txtDescripcion.setText("");
				txtPrecio.setText("");
				txtCantidad.setText("");
			}
		});
		pnlProductosCrear.add(btnCrear, "12, 18");

	}

}
