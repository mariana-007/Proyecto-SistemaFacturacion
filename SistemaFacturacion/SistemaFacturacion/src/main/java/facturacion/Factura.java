/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package facturacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Zachary Ruiz
 */
public class Factura {

    private int numeroFactura;
    private LocalDate fecha;
    private Usuario usuario;
    private double total;
    private List detalle;

    public int getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(int numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getCedulaCliente() {
        return usuario != null ? usuario.getCedula() : "";
    }

    public String getNombreCliente() {
        return usuario != null ? usuario.getNombre() : "";
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List getDetalle() {
        return detalle;
    }

    public void setDetalle(List detalle) {
        this.detalle = detalle;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Factura(int numeroFactura, LocalDate fecha, Usuario usuario, double total, List detalle) {
        this.numeroFactura = numeroFactura;
        this.fecha = fecha;
        this.usuario = usuario;
        this.total = total;
        this.detalle = detalle != null ? detalle : new ArrayList<>();
    }

    public Factura() {
    this.detalle = new ArrayList<>();
}
}
