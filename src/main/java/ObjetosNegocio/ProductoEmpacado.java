package ObjetosNegocio;

/**
 *
 * @author Kamilala
 */
public class ProductoEmpacado extends Producto {
    private double cantidad; // Cantidad del producto empacado

    // Constructor por defecto
    public ProductoEmpacado() {
        super(); // Llama al constructor por defecto de la clase Producto
        this.cantidad = 1; // Inicializa la cantidad a 1 por defecto
    }

    // Constructor que recibe un producto y una cantidad
    public ProductoEmpacado(Producto producto, double cantidad) {
        super(producto.getClave(), producto.getNombre(), producto.getTipo(), producto.getUnidad());
        try {
            validarProductoEmpacado(producto); // Valida que sea un producto empacado
            setCantidad(cantidad); // Valida y asigna la cantidad
        } catch (IllegalArgumentException e) {
            System.out.println("Error al crear ProductoEmpacado: " + e.getMessage());
            this.cantidad = 1; // Valor por defecto en caso de error
        }
    }
    
    public static ProductoEmpacado crearProductoEmpacado(Producto producto, double cantidad) {
        return new ProductoEmpacado(producto, cantidad);
    }
    
    public static void desplegarProductosEmpacados(ProductoEmpacado... productosEmpacados) {
        for (ProductoEmpacado producto : productosEmpacados) {
            if (producto != null) {
                System.out.println(producto);
            }
        }
    }

    // Constructor que recibe solo un producto
    public ProductoEmpacado(Producto producto) {
        this(producto, 1); // Reutiliza el constructor que recibe un producto y una cantidad
    }

    // Métodos de acceso (getters y setters)
    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        if (cantidad < 1) {
            throw new IllegalArgumentException("La cantidad de producto empacado debe ser al menos 1.");
        }
        this.cantidad = cantidad;
    }

    // Validación del producto empacado
    private void validarProductoEmpacado(Producto producto) {
        if (!producto.getTipo().equals("E")) {
            throw new IllegalArgumentException("El producto debe ser empacado (tipo 'E').");
        }
    }

    // Sobrescritura de equals y hashCode
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ProductoEmpacado other = (ProductoEmpacado) obj;
        return Double.compare(other.cantidad, cantidad) == 0;
    }

    @Override
    public int hashCode() {
        return super.hashCode() + Double.hashCode(cantidad);
    }

    // Método toString mejorado
    @Override
    public String toString() {
        return super.toString() + "," + cantidad;
    }
}