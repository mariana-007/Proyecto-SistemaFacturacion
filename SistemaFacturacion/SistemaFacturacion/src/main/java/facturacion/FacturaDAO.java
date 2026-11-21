/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package facturacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Zachary Ruiz
 */
public class FacturaDAO {

    public List<Factura> mostrarTodas() throws SQLException {
        String sql = "SELECT f.numero_factura, f.fecha, f.cedula_cliente, c.nombre AS nombre_cliente, f.total "
                + "FROM factura f "
                + "INNER JOIN cliente c ON f.cedula_cliente = c.cedula "
                + "ORDER BY f.numero_factura DESC";

        List<Factura> lista = new ArrayList<>();

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearFacturaEncabezado(rs));
            }
        }
        return lista;
    }

    public List<Factura> buscarFacturas(String filtro) throws SQLException {
        String texto = (filtro == null) ? "" : filtro.trim();

        if (texto.isEmpty()) {
            return mostrarTodas();
        }

        String sql = "SELECT f.numero_factura, f.fecha, f.cedula_cliente, c.nombre AS nombre_cliente, f.total "
                + "FROM factura f "
                + "INNER JOIN cliente c ON f.cedula_cliente = c.cedula "
                + "WHERE f.cedula_cliente LIKE ? OR c.nombre LIKE ? OR CAST(f.numero_factura AS CHAR) LIKE ? "
                + "ORDER BY f.numero_factura DESC";

        List<Factura> lista = new ArrayList<>();
        String patron = "%" + texto + "%";

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, patron);
            ps.setString(2, patron);
            ps.setString(3, patron);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearFacturaEncabezado(rs));
                }
            }
        }
        return lista;
    }

    private Factura mapearFacturaEncabezado(ResultSet rs) throws SQLException {
        Factura f = new Factura();
        f.setNumeroFactura(rs.getInt("numero_factura"));

        java.sql.Date fechaSql = rs.getDate("fecha");
        if (fechaSql != null) {
            f.setFecha(fechaSql.toLocalDate());
        }
        Usuario u = new Usuario();
        u.setCedula(rs.getString("cedula_cliente"));
        u.setNombre(rs.getString("nombre_cliente"));
        f.setUsuario(u);

        f.setTotal(rs.getDouble("total"));
        return f;
    }
}
