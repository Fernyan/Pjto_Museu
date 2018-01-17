/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import ConexaoBD.ConnectionFactory;
import Model.Exposicoes;
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
public class ExposicoesDao {
    Connection connection;

    public ExposicoesDao() {
        this.connection = new ConnectionFactory().getConnection();
    }
    
    //ADICIONA UMA EXPOSIÇÃO NO BANCO
    public boolean adiciona(Exposicoes exposicoes){
        
        String sql = "insert into exposicoes (nomeExp,obraExp,qtdVisitanteExp,salaExp,dtIniExp,dtFimExp) values(?,?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1,exposicoes.getNomeExp());
            stmt.setString(2,exposicoes.getObraExp());
            stmt.setInt(3,exposicoes.getQtdVisitanteExp());
            stmt.setInt(4,exposicoes.getSalaExp());
            stmt.setString(5,exposicoes.getDtIniExp());
            stmt.setString(6,exposicoes.getDtFimExp());
            stmt.setString(7,exposicoes.getStatus());

            stmt.execute();
            stmt.close();
            return true;
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;  
        }    
    }

    //ALTERA UMA EXPOSIÇÃO NO BANCO
    public void alterar(Exposicoes exposicoes){
        String sql = "UPDATE exposicoes set nomeexp=?,obraexp=?,qtdVisitanteExp=?,"
                + "salaExp=?,dtIniExp=?,dtFimExp=?,status=? where idexposicoes=?";
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1,exposicoes.getNomeExp());
            stmt.setString(2,exposicoes.getObraExp());
            stmt.setInt(3,exposicoes.getQtdVisitanteExp());
            stmt.setInt(4,exposicoes.getSalaExp());
            stmt.setString(5,exposicoes.getDtIniExp());
            stmt.setString(6,exposicoes.getDtFimExp());
            stmt.setString(7,exposicoes.getStatus());
            
            stmt.setInt(8,exposicoes.getIdExp());
            
            stmt.execute();
            stmt.close();
        } catch (SQLException u){
            throw new RuntimeException(u);
        }
    }    

    public List<Exposicoes> listarExposicoes() {
        try {
            final List<Exposicoes> exposicoes = new ArrayList<>();
            Statement stmt;
            ResultSet rs;
            
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM exposicoes");
            Exposicoes exposicao;
            
            while(rs.next()){
                exposicao = new Exposicoes();
                exposicao.setIdExp(rs.getInt("idexposicoes"));
                exposicao.setNomeExp(rs.getString("nomeexp"));
                exposicao.setObraExp(rs.getString("obraexp"));
                exposicao.setQtdVisitanteExp(rs.getInt("qtdVisitanteExp"));
                exposicao.setSalaExp(rs.getInt("salaExp"));
                exposicao.setDtIniExp(rs.getString("dtIniExp"));
                exposicao.setDtFimExp(rs.getString("dtFimExp"));
                exposicao.setStatus(rs.getString("status"));
                exposicoes.add(exposicao);
            }
            
            connection.close();
            return exposicoes;
            
        } catch (SQLException ex) {
            Logger.getLogger(RestaurarObraDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Exposicoes> buscarPorObra(String text) {
        return buscar("SELECT * FROM exposicoes WHERE obraexp LIKE '%"+text+"%'");
    }

    public List<Exposicoes> buscarPorSala(String text) {
        return buscar("SELECT * FROM exposicoes WHERE salaexp = '"+text+"'");
    }
    
    private List<Exposicoes> buscar(String sql){
        try {
            final List<Exposicoes> exposicoes = new ArrayList<>();
            Statement stmt;
            ResultSet rs;
            
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);
            Exposicoes exposicao;
            
            while(rs.next()){
                exposicao = new Exposicoes();
                exposicao.setIdExp(rs.getInt("idexposicoes"));
                exposicao.setNomeExp(rs.getString("nomeexp"));
                exposicao.setObraExp(rs.getString("obraexp"));
                exposicao.setQtdVisitanteExp(rs.getInt("qtdVisitanteExp"));
                exposicao.setSalaExp(rs.getInt("salaExp"));
                exposicao.setDtIniExp(rs.getString("dtIniExp"));
                exposicao.setDtFimExp(rs.getString("dtFimExp"));
                exposicao.setStatus(rs.getString("status"));
                exposicoes.add(exposicao);
            }
            
            connection.close();
            return exposicoes;
            
        } catch (SQLException ex) {
            Logger.getLogger(RestaurarObraDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
