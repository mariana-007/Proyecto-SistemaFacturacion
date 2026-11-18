CREATE DATABASE ventas;
USE ventas;

CREATE TABLE cliente (
    cedula VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    correo VARCHAR(100) NOT NULL
);

CREATE TABLE producto (
    codigo VARCHAR(20) PRIMARY KEY,
    descripcion VARCHAR(100) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    existencia INT NOT NULL
);

CREATE TABLE usuario (
    cedula VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    contrasena VARCHAR(100) NOT NULL
);

CREATE TABLE factura (
    numero_factura INT PRIMARY KEY AUTO_INCREMENT,
    fecha DATE NOT NULL,
    cedula_cliente VARCHAR(20) NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (cedula_cliente) REFERENCES cliente(cedula)
);

CREATE TABLE detalle_factura (
    id_detalle INT PRIMARY KEY AUTO_INCREMENT,
    numero_factura INT NOT NULL,
    codigo_producto VARCHAR(20) NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    cantidad INT NOT NULL,
    importe DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (numero_factura) REFERENCES factura(numero_factura),
    FOREIGN KEY (codigo_producto) REFERENCES producto(codigo)
);

-- Usuario inicial de prueba para el login
INSERT INTO usuario (cedula, nombre, contrasena) VALUES ('101010101', 'Administrador', 'admin123');
