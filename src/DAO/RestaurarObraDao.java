/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import ConexaoBD.ConnectionFactory;
import Model.RestaurarObra;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fernanda
 */
public class RestaurarObraDao {
    Connection connection;

    public RestaurarObraDao() {
        this.connection = new ConnectionFactory().getConnection();
    }
    //ADICIONA OBRA A RESTAURAR NO BANCO
    public boolean adiciona(RestaurarObra restaurarObra){
        
        String sql = "insert into restaurarobra (obrarestaurar,empresa,dtlimite) values(?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1,restaurarObra.getObraRestaurar());
            stmt.setString(2,restaurarObra.getEmpresa());
            stmt.setString(3,restaurarObra.getDtLimite());

            stmt.execute();
            stmt.close();
            return true;
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;  
        }    
    }
    public RestaurarObra buscarPorId(String id) throws SQLException {
        Statement stmt;
        ResultSet rs;
        
        stmt = connection.createStatement();
        rs = stmt.executeQuery("SELECT * FROM restaurarObra where idrestaurarobra='"+id+"'");
        RestaurarObra restaurarObra;
        restaurarObra = null;
        
        if(rs.next()==true){
            restaurarObra = new RestaurarObra();
            restaurarObra.setObraRestaurar(rs.getString("obrarestaurar"));
            restaurarObra.setEmpresa(rs.getString("empresa"));
            restaurarObra.setDtLimite(rs.getString("dtLimite"));

        }
        
        connection.close();
        return restaurarObra;
    }
    
    public void alterar(RestaurarObra restaurarObra){
        String sql = "UPDATE restaurarobra set obrarestaurar=?,empresa=?,dtlimite=? where idrestaurarObra=?";
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1,restaurarObra.getObraRestaurar());
            stmt.setString(2,restaurarObra.getEmpresa());
            stmt.setString(3,restaurarObra.getDtLimite());
            
            stmt.setInt(4,restaurarObra.getIdRestaurar());
            stmt.execute();
            stmt.close();
        } catch (SQLException u){
            throw new RuntimeException(u);
        }
    }
    
    public List<RestaurarObra> listarRestauracoes(){
        try {
            final List<RestaurarObra> restaurar = new ArrayList<>();
            Statement stmt;
            ResultSet rs;
            
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM restaurarobra");
            RestaurarObra e;
            e = null;
            
            while(rs.next()){
                e = new RestaurarObra();
                e.setIdRestaurar(rs.getInt("idrestaurarobra"));
                e.setObraRestaurar(rs.getString("obrarestaurar"));
                e.setEmpresa(rs.getString("empresa"));
                e.setDtLimite(rs.getString("dtLimite"));
                restaurar.add(e);
            }
            
            connection.close();
            return restaurar;
            
        } catch (SQLException ex) {
            Logger.getLogger(RestaurarObraDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }    

    public List<RestaurarObra> buscarPorObra(String text) {
        return buscar("SELECT * FROM restaurarobra WHERE obraRestaurar LIKE '%"+text+"%'");
    }

    public List<RestaurarObra> buscarPorEmpresa(String text) {
        return buscar("SELECT * FROM restaurarobra WHERE empresa LIKE '%"+text+"%'");
    }

    public List<RestaurarObra> buscarPorData(String text) {
        return buscar("SELECT * FROM restaurarobra WHERE dtLimite LIKE '%"+text+"%'");
    }
    
    public List<RestaurarObra> buscar(String sql) {
        List<RestaurarObra> obras = new ArrayList<>();
        try {
            Statement stmt;
            ResultSet rs;
            
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);
            RestaurarObra restaurarObra;
            
            while(rs.next()){
                restaurarObra = new RestaurarObra();
                restaurarObra.setObraRestaurar(rs.getString("obrarestaurar"));
                restaurarObra.setEmpresa(rs.getString("empresa"));
                restaurarObra.setDtLimite(rs.getString("dtLimite"));
                obras.add(restaurarObra);
            }
            
            connection.close();
            return obras;
        } catch (SQLException ex) {
            Logger.getLogger(RestaurarObraDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
