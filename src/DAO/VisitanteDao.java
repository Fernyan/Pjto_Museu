/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import ConexaoBD.ConnectionFactory;
import Model.Visitante;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Fernanda
 */
public class VisitanteDao {
    Connection connection;

    public VisitanteDao() {
        this.connection = new ConnectionFactory().getConnection();
    }
    
    //ADICIONA UM VISITANTE NO BANCO
    public boolean adiciona(Visitante visitante){
        
        String sql = "insert into visitante (nome,cpf,telefone,email,endereco,Cidade) values(?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1,visitante.getNome());
            stmt.setString(2,visitante.getCpf());
            stmt.setString(3,visitante.getTelefone());
            stmt.setString(4,visitante.getEmail());
            stmt.setString(5,visitante.getEndereco());
            stmt.setString(6,visitante.getCidade());

            stmt.execute();
            stmt.close();
            return true;
            
        } catch (Exception e) {
            //throw new RuntimeException(e);
            //JOptionPane.showMessageDialog(null, "Cpf ja cadastrado");
            return false;  
        }    
    }
public Visitante buscar(String cpf) throws SQLException {
        Statement stmt;
        ResultSet rs;
        
        stmt = connection.createStatement();
        rs = stmt.executeQuery("SELECT * FROM visitante where cpf ='"+cpf+"'");
        Visitante vis;
        vis = null;
        
        if(rs.next()==true){
            vis = new Visitante();
            vis.setNome(rs.getString("nome"));
            vis.setCpf(rs.getString("cpf"));
            vis.setTelefone(rs.getString("telefone"));
            vis.setEmail(rs.getString("email"));
            vis.setEndereco(rs.getString("endereco"));
            vis.setCidade(rs.getString("cidade"));
        }
        
        connection.close();
        return vis;
    }
}
