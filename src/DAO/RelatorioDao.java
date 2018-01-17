/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import ConexaoBD.ConnectionFactory;
import java.sql.Connection;

/**
 *
 * @author Fernanda
 */
public class RelatorioDao {
    Connection connection;

    public RelatorioDao() {
        this.connection = new ConnectionFactory().getConnection();
    }   
}
