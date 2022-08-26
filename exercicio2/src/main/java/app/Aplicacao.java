package app;

import java.util.List;
import java.util.Scanner;

import dao.DAO;
import dao.ProdutoDAO;
import model.Produto;

public class Aplicacao {
	public static Scanner input = new Scanner(System.in);
	
	public static void main(String[] args) throws Exception {
		int op;
		ProdutoDAO produtoDAO = new ProdutoDAO();
			
		do {
			op = menu();
			
			switch(op){
				case 1:
					listarProd(produtoDAO);
					break;
				case 2:
					inserirProd(produtoDAO);
					break;
				case 3:
					excluirProd(produtoDAO);
					break;
				case 4:
					atualizarProd(produtoDAO);
					break;
				case 5:
					System.out.print("Saindo");
			}
		} while(op != 5);
	}
	
	public static void listarProd(ProdutoDAO produtoDAO) {
		System.out.println("\n\n==== Mostrar produtos ordenados por código === ");
		List<Produto> produtos = produtoDAO.getOrderByCodigo();
		for (Produto u: produtos) {
			System.out.println(u.toString());
		}
	}

	public static Produto criarProduto() {
		int codigo;
		String nome;
		String tipo;
		
		System.out.print("Codigo: ");
		codigo = input.nextInt();
		clearBuffer();
		System.out.print("Nome: ");
		nome = input.nextLine();
		System.out.print("Tipo: ");
		tipo = input.nextLine();
		
		Produto produto = new Produto(codigo, nome, tipo);
		
		return produto;
	}
	
	public static void inserirProd(ProdutoDAO produtoDAO) {
		System.out.println("\n\n==== Inserir produto === ");
		
		Produto produto = criarProduto();
		if(produtoDAO.insert(produto) == true) {
			System.out.println("Inserção com sucesso -> " + produto.toString());
		}
	}
	
	public static void excluirProd(ProdutoDAO produtoDAO) {
		System.out.println("\n\n==== Excluir produto === ");
		System.out.println("Codigo: ");
		int codigo = input.nextInt();
	
		Produto produto = produtoDAO.get(codigo);
		
		System.out.println("\nExcluindo produto (código " + produto.getCodigo() + ")");
		produtoDAO.delete(produto.getCodigo());
	}
	
	public static void atualizarProd(ProdutoDAO produtoDAO) {
		System.out.println("\n\n==== Atualizar produto === ");
		System.out.println("Codigo: ");
		int codigo = input.nextInt();
	
		Produto produto = produtoDAO.get(codigo);
		
		System.out.println("\nAtualizar produto (código " + produto.getCodigo() + ")");
		Produto novo = criarProduto();
		
		if(produtoDAO.insert(novo) == true) {
			produtoDAO.delete(produto.getCodigo());
			System.out.println("Atualização com sucesso -> " + novo.toString());
		}
	}
	
	private static void clearBuffer() {
        if (input.hasNextLine()) {
            input.nextLine();
        }
    }
	
	public static int menu() {
		int op;
		
		System.out.println("\n\n=== MENU === ");
		System.out.println("1 - Listar\n");
		System.out.println("2 - Inserir\n");
		System.out.println("3 - Excluir\n");
		System.out.println("4 - Atualizar\n");
		System.out.println("5 - Sair\n");
		System.out.print("Sua opcao: ");
		op = input.nextInt();
		
		return op;
	}
}
