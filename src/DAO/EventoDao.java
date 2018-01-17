/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import ConexaoBD.ConnectionFactory;
import Model.Evento;
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
public class EventoDao {    
    Connection connection;

    public EventoDao() {
        this.connection = new ConnectionFactory().getConnection();
    }
    
    //ADICIONA UM EVENTO NO BANCO
    public boolean adiciona(Evento evento){
        
        String sql = "insert into evento (nomeEvento,dataEvento,respEvento,qtdVisitante,desEvento, status) values(?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1,evento.getNomeEvento());
            stmt.setString(2,evento.getDataEvento());
            stmt.setString(3,evento.getRespEvento());
            stmt.setInt(4,evento.getVisitanteEvento());
            stmt.setString(5,evento.getDescEvento());
            stmt.setString(6,evento.getStatus());

            stmt.execute();
            stmt.close();
            return true;
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;  
        }    
    }
    
    //BUSCA UM EVENTO NO BANCO
    public Evento buscar(String id) throws SQLException {
        Statement stmt;
        ResultSet rs;
        
        stmt = connection.createStatement();
        rs = stmt.executeQuery("SELECT * FROM evento where idevento='"+id+"'");
        Evento e;
        e = null;
        
        if(rs.next()==true){
            e = new Evento();
            e.setIdEvento(Integer.parseInt(id));
            e.setNomeEvento(rs.getString("nomeevento"));
            e.setDataEvento(rs.getString("dataevento"));
            e.setRespEvento(rs.getString("respevento"));
            e.setVisitanteEvento(rs.getInt("qtdVisitante"));
            e.setDescEvento(rs.getString("desEvento"));
            e.setStatus(rs.getString("status"));
        }
        
        connection.close();
        return e;
    }
    
    //ALTERA UM EVENTO NO BANCO
    public void alterar(Evento evento){
        String sql = "UPDATE evento set nomeevento=?,dataevento=?,respevento=?,"
                + "qtdVisitante=?,desevento=?,status=? where idevento=?";
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1,evento.getNomeEvento());
            stmt.setString(2,evento.getDataEvento());
            stmt.setString(3,evento.getRespEvento());
            stmt.setInt(4,evento.getVisitanteEvento());
            stmt.setString(5,evento.getDescEvento());
            stmt.setString(6,evento.getStatus());
            
            stmt.setInt(7,evento.getIdEvento());
            
            stmt.execute();
            stmt.close();
        } catch (SQLException u){
            throw new RuntimeException(u);
        }
    }
    
    public List<Evento> listarEventos(){
        try {
            final List<Evento> eventos = new ArrayList<>();
            Statement stmt;
            ResultSet rs;
            
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM evento");
            Evento e;
            e = null;
            
            while(rs.next()){
                e = new Evento();
                e.setIdEvento(Integer.parseInt(rs.getString("idevento")));
                e.setNomeEvento(rs.getString("nomeevento"));
                e.setDataEvento(rs.getString("dataevento"));
                e.setRespEvento(rs.getString("respevento"));
                e.setVisitanteEvento(rs.getInt("qtdVisitante"));
                e.setDescEvento(rs.getString("desEvento"));
                e.setStatus(rs.getString("status"));
                eventos.add(e);
            }
            
            connection.close();
            return eventos;
        } catch (SQLException ex) {
            Logger.getLogger(EventoDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
