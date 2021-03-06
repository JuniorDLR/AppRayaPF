/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelos.Repuesto;
import modelos.Usuario;

/**
 *
 * @author Junior de la Rocha
 */
public class TablaRepuesto {
    private Conexion conex = new Conexion() ;
   private Connection conn;
    private PreparedStatement mostrarRepuesto;
    private PreparedStatement insertarRepuesto;
    private PreparedStatement editarRepuesto;
    private PreparedStatement eliminarRepuesto;
    
    
    
    private List<Repuesto> listaR = new ArrayList();
    
    
    public TablaRepuesto(){
        
          try{
            conn = conex.obtenerConexion();
            mostrarRepuesto = conn.prepareStatement("Select * from Repuesto");
            insertarRepuesto = conn.prepareStatement("insert into Repuesto(nombre,descripcion,precio,existencia) values(?, ?, ?, ?)");
            editarRepuesto = conn.prepareStatement("Update Repuesto set nombre = ?, descripcion = ? ,precio=?, existencia=?  where id = ?");
            eliminarRepuesto = conn.prepareStatement("Delete from Repuesto where id = ?");
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
}
    
    
       public TablaRepuesto(List<Repuesto> lista) {
        listaR = lista;
        try{
             conn = conex.obtenerConexion();
            mostrarRepuesto = conn.prepareStatement("Select * from Repuesto");
            insertarRepuesto = conn.prepareStatement("insert into Repuesto(nombre,descripcion,precio,existencia) values(?, ?, ?, ?)");
            editarRepuesto = conn.prepareStatement("Update Repuesto set nombre = ?, descripcion = ?,precio= ?, existencia= ? where id = ?");
            eliminarRepuesto = conn.prepareStatement("Delete from Repuesto where id = ?");
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        this.listarRepuesto();
    }
       
       
       
    public List<Repuesto> getListaR() {
        return listaR;
    }

    public void setListaU(List<Repuesto> listaR) {
        this.listaR = listaR;
    }
    
     
    private void listarRepuesto(){
        ResultSet rs;
        try{
            rs = this.mostrarRepuesto.executeQuery();
            listaR.clear();
            while(rs.next()){
                listaR.add(new Repuesto                (
                        rs.getInt("id"),
                        rs.getString("Nombre"),
                        rs.getString("Descripcion"),
                        rs.getFloat("precio"),
                        rs.getInt("existencia"),
                        1
                ));
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
       
       
       
    public int guardarRegistro( String nombre, String descripcion, float precio, int existencia){
        int b = 0;
        try{
            
            this.insertarRepuesto.setString(1, nombre);
            this.insertarRepuesto.setString(2, descripcion );
            this.insertarRepuesto.setFloat(3, precio );
            this.insertarRepuesto.setInt(4, existencia);
            b = this.insertarRepuesto.executeUpdate();
       
        }catch(SQLException ex){
            this.conex.close(conn);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return b;
    }
           
    public int editarRegistro( String nombre, String descripcion, float precio, int existencia, int id){
        int b = 0;
        try{
            
            this.editarRepuesto.setString(1, nombre);
            this.editarRepuesto.setString(2, descripcion);
            this.editarRepuesto.setFloat(3, precio);
            this.editarRepuesto.setInt(4, existencia);
            this.editarRepuesto.setInt(5, id);
            b = this.editarRepuesto.executeUpdate();
          
        }catch(SQLException ex){
            this.conex.close(conn);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return b;
    }
             
    public int eliminarRegistro(int id){
        int b = 0;
        try{
            this.eliminarRepuesto.setInt(1, id);
            b = this.eliminarRepuesto.executeUpdate();
        }catch(SQLException ex){
            this.conex.close(conn);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return b;
    }
                 
                 
    public void actualizarBD(){
        try{
            int b;
            int contIns = 0, contEdi = 0, contEli = 0, contErr = 0;
            String msn="Operaciones realizadas: \n";
            for(Repuesto R: listaR){
                switch(R.getEstado()){
                    case 0:
                        b = this.guardarRegistro( R.getNombreRepuesto(), R.getDescripcion(), R.getPrecio(), R.getExistencia());
                        if (b!=0) contIns++;
                        break;
                    case 1:
                        break;
                    case 2:
                        b = this.editarRegistro(R.getNombreRepuesto(), R.getDescripcion(), R.getPrecio(), R.getExistencia(), R.getId());
                        if (b!=0) contEdi++;
                        break;
                    case 3:
                        b = this.eliminarRegistro(R.getId());
                        if (b!=0) contEli++;
                        break;
                    default:
                        System.out.println("Estado invalido");
                        contErr++;
                        break;
                }
            }
            msn += "Registros insertados: " + contIns +"\nRegistros Editados: "
                    + contEdi +"\nRegistros Eliminados: "+ contEli+".";
            JOptionPane.showMessageDialog(null, "Operaci??n de actualizaci??n terminada...\n"+
                    msn, "Taler La Raya", JOptionPane.INFORMATION_MESSAGE);
            this.listarRepuesto();
        }catch(HeadlessException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", 
                    JOptionPane.ERROR_MESSAGE);
        }
        this.listarRepuesto();
   }
    
}