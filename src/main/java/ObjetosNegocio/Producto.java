package ObjetosNegocio;

public class Producto {
    private String clave;
    private String nombre;
    private String tipo; // 'E' para empacado, 'G' para granel
    private String unidad; // "PZ", "KG", "L", "g"

    // Contadores estáticos para cada tipo de producto
    private static int contadorEM = 1;
    private static int contadorGR = 1;

    // Constructor por defecto 
    public Producto() {
        this("Producto por defecto", "E", "Pz");
    }

    // Constructor q inicialice los atributos
    public Producto(String nombre, String tipo, String unidad) {
        setNombre(nombre);
        setTipo(tipo);
        setUnidad(unidad);
        this.clave = generarClaveAutomatica();
    }

    // Constructor completo (con validación de clave)
    public Producto(String clave, String nombre, String tipo, String unidad) {
        if (!esClaveValida(clave, tipo)) {
            throw new IllegalArgumentException("Clave invalida o no coincide con tipo");
        }
        this.clave = clave;
        setNombre(nombre);
        setTipo(tipo);
        setUnidad(unidad);
        actualizarContadores(clave);
    }

    // Constructor de copia
    public Producto(Producto otro) {
        this.clave = otro.clave;
        this.nombre = otro.nombre;
        this.tipo = otro.tipo;
        this.unidad = otro.unidad;
    }
    
    public static Producto crearProducto(String clave, String nombre, String tipo, String unidad) {
        return new Producto(clave, nombre, tipo, unidad);
    }
    
    
    
    public static void desplegarProductos(Producto... productos) {
       for (Producto producto : productos) {
            if (producto != null) {
                System.out.println(producto);
            }
        }
    }
   
    public static void corregirNombreProducto(Producto producto, String nuevoNombre) {
        if (producto != null) {
            producto.setNombre(nuevoNombre);
            System.out.println("Producto despues de correccion: " + producto);
        }
    }
    
    public static void verificarProductosDiferentes(Producto p1, Producto p2) {
        if (p1 != null && p2 != null) {
            System.out.println(" Productos son diferentes? " + !p1.equals(p2));
        }
    }
    
    public static void verificarProductosIguales(Producto p1, Producto p2) {
        if (p1 != null && p2 != null) {
            System.out.println(" Productos son iguales? " + p1.equals(p2));
        }
    }

    // Genera clave automática según el tipo (EM001, GR001, etc.)
    private String generarClaveAutomatica() {
        String prefijo = tipo.equals("E") ? "EM" : "GR";
        int numero = tipo.equals("E") ? contadorEM++ : contadorGR++;
        return String.format("%s%03d", prefijo, numero);
    }

    // Valida el formato de la clave y su coherencia con el tipo
    private boolean esClaveValida(String clave, String tipo) {
        if (clave == null || !clave.matches("^[A-Za-z]{2}\\d{3}$")) {
        return false;
    }

    String prefijo = clave.substring(0, 2);
    String numeroStr = clave.substring(2);

    // Validar prefijo
    if (!prefijo.equals("EM") && !prefijo.equals("GR")) {
        return false;
    }

    // Validar prefijo-tipo
    if ((prefijo.equals("EM") && !tipo.equals("E")) ||
        (prefijo.equals("GR") && !tipo.equals("G"))) {
        return false;
    }

    // Validar que sean 3 dígitos numéricos
    try {
        Integer.valueOf(numeroStr);
    } catch (NumberFormatException e) {
        return false;
    }

    return true;    }

    // Actualiza contadores si la clave es mayor al actual
    private void actualizarContadores(String clave) {
        String prefijo = clave.substring(0, 2);
        int numero = Integer.parseInt(clave.substring(2));

        if (prefijo.equals("EM") && numero >= contadorEM) {
            contadorEM = numero + 1;
        } else if (prefijo.equals("GR") && numero >= contadorGR) {
            contadorGR = numero + 1;
        }
    }

    // --- Métodos de acceso (Getters y Setters) ---
    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        if (!esClaveValida(clave, this.tipo)) {
            throw new IllegalArgumentException("Clave inválida o no coincide con tipo");
        }
        this.clave = clave;
        actualizarContadores(clave);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre no puede estar vacío");
        }
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        if (!tipo.equals("E") && !tipo.equals("G")) {
            throw new IllegalArgumentException("Tipo debe ser 'E' o 'G'");
        }
        if (this.clave != null &&
            ((tipo.equals("E") && !clave.startsWith("EM")) ||
             (tipo.equals("G") && !clave.startsWith("GR")))) {
            throw new IllegalArgumentException("Tipo no coincide con prefijo de clave");
        }
        this.tipo = tipo;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        if (!unidad.equalsIgnoreCase("PZ") && !unidad.equalsIgnoreCase("KG") &&
            !unidad.equalsIgnoreCase("L") && !unidad.equalsIgnoreCase("G")) {
            throw new IllegalArgumentException("Unidad debe ser PZ, KG, L o G");
        }
        this.unidad = unidad.toUpperCase(); // Convertir a mayúsculas
    }
 

    // --- Métodos estándar ---
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Producto producto = (Producto) obj;
        return clave.equals(producto.clave);
    }

    @Override
    public int hashCode() {
        return clave.hashCode();
    }

    @Override
    public String toString() {
        return clave + "," + nombre + "," + tipo + "," + unidad;
    }
}