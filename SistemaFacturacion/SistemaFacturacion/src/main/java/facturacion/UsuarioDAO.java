package facturacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Acceso a datos de la tabla usuario (CRUD + validación de login). */
public class UsuarioDAO {

    private Usuario crearUsuario(ResultSet rs) throws SQLException {
        return new Usuario(rs.getString("cedula"),
                           rs.getString("nombre"),
                           rs.getString("contrasena"));
    }

    /** Todos los usuarios. */
    public List<Usuario> listar() throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT cedula, nombre, contrasena FROM usuario ORDER BY nombre";
        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(crearUsuario(rs));
            }
        }
        return lista;
    }

    /** Busca por nombre o cédula (coincidencia parcial). */
    public List<Usuario> buscar(String texto) throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT cedula, nombre, contrasena FROM usuario "
                   + "WHERE cedula LIKE ? OR nombre LIKE ? ORDER BY nombre";
        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, "%" + texto + "%");
            ps.setString(2, "%" + texto + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(crearUsuario(rs));
                }
            }
        }
        return lista;
    }

    /** Devuelve el usuario con esa cédula o null si no existe. */
    public Usuario obtenerPorCedula(String cedula) throws SQLException {
        String sql = "SELECT cedula, nombre, contrasena FROM usuario WHERE cedula = ?";
        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, cedula);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return crearUsuario(rs);
                }
            }
        }
        return null;
    }

    public boolean insertar(Usuario u) throws SQLException {
        String sql = "INSERT INTO usuario (cedula, nombre, contrasena) VALUES (?, ?, ?)";
        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, u.getCedula());
            ps.setString(2, u.getNombre());
            ps.setString(3, u.getContrasena());
            return ps.executeUpdate() > 0;
        }
    }

    /** Modifica nombre y contraseña; la cédula (llave primaria) no se cambia. */
    public boolean modificar(Usuario u) throws SQLException {
        String sql = "UPDATE usuario SET nombre = ?, contrasena = ? WHERE cedula = ?";
        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getContrasena());
            ps.setString(3, u.getCedula());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(String cedula) throws SQLException {
        String sql = "DELETE FROM usuario WHERE cedula = ?";
        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, cedula);
            return ps.executeUpdate() > 0;
        }
    }

    /** Cantidad total de usuarios (para no borrar el último y quedar sin acceso). */
    public int contar() throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuario";
        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    /**
     * Valida el login. Devuelve el Usuario si cédula y contraseña son correctas,
     * o null si no coinciden. BINARY hace que la contraseña distinga mayúsculas.
     */
    public Usuario validarLogin(String cedula, String contrasena) throws SQLException {
        String sql = "SELECT cedula, nombre, contrasena FROM usuario "
                   + "WHERE cedula = ? AND BINARY contrasena = ?";
        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, cedula);
            ps.setString(2, contrasena);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return crearUsuario(rs);
                }
            }
        }
        return null;
    }
}
