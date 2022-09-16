package dao;

import model.Empresa;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class EmpresaDAO extends DAO {	
	public EmpresaDAO() {
		super();
		conectar();
	}
	
	
	public void finalize() {
		close();
	}
	
	
	public boolean insert(Empresa produto) {
		boolean status = false;
		try {
			String sql = "INSERT INTO produto (descricao, preco, quantidade, datafabricacao, datavalidade) "
		               + "VALUES ('" + produto.getDescricao() + "', "
		               + produto.getPreco() + ", " + produto.getQuantidade() + ", ?, ?);";
			PreparedStatement st = conexao.prepareStatement(sql);
		    st.setTimestamp(1, Timestamp.valueOf(produto.getDataFabricacao()));
			st.setDate(2, Date.valueOf(produto.getDataValidade()));
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	
	public Empresa get(int id) {
		Empresa produto = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM produto WHERE id="+id;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 produto = new Empresa(rs.getInt("id"), rs.getString("descricao"), (float)rs.getDouble("preco"), 
	                				   rs.getInt("quantidade"), 
	        			               rs.getTimestamp("datafabricacao").toLocalDateTime(),
	        			               rs.getDate("datavalidade").toLocalDate());
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return produto;
	}
	
	
	public List<Empresa> get() {
		return get("");
	}

	
	public List<Empresa> getOrderByID() {
		return get("id");		
	}
	
	
	public List<Empresa> getOrderByDescricao() {
		return get("descricao");		
	}
	
	
	public List<Empresa> getOrderByPreco() {
		return get("preco");		
	}
	
	
	private List<Empresa> get(String orderBy) {
		List<Empresa> produtos = new ArrayList<Empresa>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM produto" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Empresa p = new Empresa(rs.getInt("id"), rs.getString("descricao"), (float)rs.getDouble("preco"), 
	        			                rs.getInt("quantidade"),
	        			                rs.getTimestamp("datafabricacao").toLocalDateTime(),
	        			                rs.getDate("datavalidade").toLocalDate());
	            produtos.add(p);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return produtos;
	}
	
	
	public boolean update(Empresa produto) {
		boolean status = false;
		try {  
			String sql = "UPDATE produto SET descricao = '" + produto.getDescricao() + "', "
					   + "preco = " + produto.getPreco() + ", " 
					   + "quantidade = " + produto.getQuantidade() + ","
					   + "datafabricacao = ?, " 
					   + "datavalidade = ? WHERE id = " + produto.getID();
			PreparedStatement st = conexao.prepareStatement(sql);
		    st.setTimestamp(1, Timestamp.valueOf(produto.getDataFabricacao()));
			st.setDate(2, Date.valueOf(produto.getDataValidade()));
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public boolean delete(int id) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM produto WHERE id = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
}