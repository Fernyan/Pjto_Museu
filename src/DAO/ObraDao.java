/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Obra;
import ConexaoBD.ConnectionFactory;
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
public class ObraDao {
    Connection connection;

    public ObraDao() {
        this.connection = new ConnectionFactory().getConnection();
    }

    //ADICIONA UMA OBRA NO BANCO
    public boolean adiciona(Obra obra){

        String sql = "insert into obra (titulo,autor,dtcriacao,tipo,estado,Imagem) values(?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1,obra.getTitulo());
            stmt.setString(2,obra.getAutor());
            stmt.setString(3,obra.getDtcriacao());
            stmt.setString(4,obra.getTipo());
            stmt.setString(5,obra.getEstado());
            stmt.setString(6,obra.getImagem());

            stmt.execute();
            stmt.close();
            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public Obra buscarPorId(String id) throws SQLException {
        Statement stmt;
        ResultSet rs;

        stmt = connection.createStatement();
        rs = stmt.executeQuery("SELECT * FROM obra where idobra='"+id+"'");
        Obra o;
        o = null;

        if(rs.next()==true){
            o = new Obra();
            o.setTitulo(rs.getString("titulo"));
            o.setAutor(rs.getString("autor"));
            o.setDtcriacao(rs.getString("dtcriacao"));
            o.setTipo(rs.getString("tipo"));
            o.setEstado(rs.getString("estado"));
            o.setImagem(rs.getString("Imagem"));
        }

        connection.close();
        return o;
    }
    public void alterar(Obra obra){
        String sql = "UPDATE obra set titulo=?,autor=?,dtcriacao=?,tipo=?,estado=?,Imagem=? where idobra=?";
        try{
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1,obra.getTitulo());
                stmt.setString(2,obra.getAutor());
                stmt.setString(3,obra.getDtcriacao());
                stmt.setString(4,obra.getTipo());
                stmt.setString(5,obra.getEstado());
                stmt.setString(6,obra.getImagem());
                
                stmt.setInt(7,obra.getIdObra());
                stmt.execute();
            }
        } catch (SQLException u){
            throw new RuntimeException(u);
        }
    }
    
    public List<Obra> listarObras(){
        try {
            final List<Obra> obras = new ArrayList<>();
            Statement stmt;
            ResultSet rs;
            
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM obra");
            Obra e;
            e = null;
            
            while(rs.next()){
                e = new Obra();
                e.setTitulo(rs.getString("titulo"));
                e.setAutor(rs.getString("autor"));
                e.setDtcriacao(rs.getString("dtcriacao"));
                e.setTipo(rs.getString("tipo"));
                e.setEstado(rs.getString("estado"));
                e.setImagem(rs.getString("Imagem"));
                e.setIdObra(Integer.parseInt(rs.getString("idobra")));
                obras.add(e);
            }
            
            connection.close();
            return obras;
            
        } catch (SQLException ex) {
            Logger.getLogger(RestaurarObraDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }    

    public List<Obra> buscarPorAutor(String text) {
        return buscar("SELECT * FROM obra WHERE autor LIKE '%"+text+"%'");
    }

    public List<Obra> buscarPorTitulo(String text) {
        return buscar("SELECT * FROM obra WHERE titulo LIKE '%"+text+"%'");
    }

    public List<Obra> buscarPorEstado(String text) {
        return buscar("SELECT * FROM obra WHERE estado LIKE '%"+text+"%'");
    }
    
    private List<Obra> buscar(String sql){
        try {
            final List<Obra> obras = new ArrayList<>();
            ResultSet rs;
            Statement stmt;
            
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);
            Obra e;
            e = null;
            
            while(rs.next()){
                e = new Obra();
                e.setTitulo(rs.getString("titulo"));
                e.setAutor(rs.getString("autor"));
                e.setDtcriacao(rs.getString("dtcriacao"));
                e.setTipo(rs.getString("tipo"));
                e.setEstado(rs.getString("estado"));
                e.setImagem(rs.getString("Imagem"));
                e.setIdObra(Integer.parseInt(rs.getString("idobra")));
                obras.add(e);
            }
            
            connection.close();
            return obras;
            
        } catch (SQLException ex) {
            Logger.getLogger(RestaurarObraDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}