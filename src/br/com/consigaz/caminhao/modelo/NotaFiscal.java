package br.com.consigaz.caminhao.modelo;

import java.io.Serializable;

import br.com.consigaz.caminhao_sharedlib.modelo.Cliente_pedido;
import br.com.consigaz.caminhao_sharedlib.modelo.Condicao_pagamento;
import br.com.consigaz.caminhao_sharedlib.modelo.Item_nfe;
import br.com.consigaz.caminhao_sharedlib.modelo.Local_entrega_pedido;
import br.com.consigaz.caminhao_sharedlib.modelo.Pedido;
import br.com.consigaz.caminhao_sharedlib.modelo.Produto;

public class NotaFiscal implements Serializable{
			
	private int idInterno;
	private double total;
	private Item_nfe item_nfe;
	private Produto produto;
	private Cliente_pedido cliente_pedido;
	
	private Pedido pedido;
	private Local_entrega_pedido local_entrega_pedido;
	private Condicao_pagamento condicao_pagamento;
	
	
	
	
	public Condicao_pagamento getCondicao_pagamento() {
		return condicao_pagamento;
	}
	public void setCondicao_pagamento(Condicao_pagamento condicao_pagamento) {
		this.condicao_pagamento = condicao_pagamento;
	}
	public Pedido getPedido() {
		return pedido;
	}
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	public Local_entrega_pedido getLocal_entrega_pedido() {
		return local_entrega_pedido;
	}
	public void setLocal_entrega_pedido(Local_entrega_pedido local_entrega_pedido) {
		this.local_entrega_pedido = local_entrega_pedido;
	}
	public int getIdInterno() {
		return idInterno;
	}
	public void setIdInterno(int idInterno) {
		this.idInterno = idInterno;
	}
	public Item_nfe getItem_nfe() {
		return item_nfe;
	}
	public void setItem_nfe(Item_nfe item_nfe) {
		this.item_nfe = item_nfe;
	}
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public Cliente_pedido getCliente_pedido() {
		return cliente_pedido;
	}
	public void setCliente_pedido(Cliente_pedido cliente_pedido) {
		this.cliente_pedido = cliente_pedido;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
}
