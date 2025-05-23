package ObjetosNegocio;

/**
 *
 * @author Kamilala
 */
public class ProductoGranel extends Producto {
    private double cantidad; // Cantidad del producto a granel

    // Constructor por defecto
    public ProductoGranel() {
        super(); // Llama al constructor por defecto de la clase Producto
        this.cantidad = 0.01; // Inicializa la cantidad a un valor mínimo válido
    }

    // Constructor que recibe un producto y una cantidad
    public ProductoGranel(Producto producto, double cantidad) {
        super(producto.getClave(), producto.getNombre(), producto.getTipo(), producto.getUnidad());
        try {
            validarProductoGranel(producto); // Valida que el producto sea de tipo granel
            setCantidad(cantidad); // Valida y asigna la cantidad
        } catch (IllegalArgumentException e) {
            System.out.println("Error al crear ProductoGranel: " + e.getMessage());
            this.cantidad = 0.01; // Valor por defecto en caso de error
        }
    }

    // Constructor que recibe solo un producto
    public ProductoGranel(Producto producto) {
        this(producto, 0.01); // Reutiliza el constructor que recibe un producto y una cantidad
    }

    // Métodos de acceso (getters y setters)
  
    public double getCantidad() {
    return this.cantidad;
    }

    
     public static void desplegarProductosGranel(ProductoGranel... productosGranel) {
        for (ProductoGranel producto : productosGranel) {
            if (producto != null) {
                System.out.println(producto);
            }
        }
    }
    

    public void setCantidad(double cantidad) {
        if (cantidad < 0.01) {
            throw new IllegalArgumentException("La cantidad de producto a granel debe ser al menos 0.01.");
        }
        this.cantidad = cantidad;
    }

    // Validación del producto a granel
    private void validarProductoGranel(Producto producto) {
        if (!producto.getTipo().equals("G")) {
            throw new IllegalArgumentException("El producto debe ser de tipo 'G' (granel).");
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
        ProductoGranel other = (ProductoGranel) obj;
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