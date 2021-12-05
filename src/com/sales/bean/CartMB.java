package com.sales.bean;


import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.sales.entities.Item;
import com.sales.entities.Produto;

@SessionScoped
@ManagedBean(name = "cartMB")
public class CartMB {

	private List<Item> items;

	public CartMB() {
		this.items = new ArrayList<Item>();
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public String comprar(Produto produto) {
		int index = temProduto(produto);
		if (index == -1) {
			this.items.add(new Item(produto, 1));
		} else {
			int quantidade = this.items.get(index).getQuantidade() + 1;
			this.items.get(index).setQuantidade(quantidade);
		}
		return "cart?faces-redirect=true";
	}

	public String deletar(Produto produto) {
		int index = this.temProduto(produto);
		this.items.remove(index);
		return "cart?faces-redirect=true";
	}

	public double total() {
		double s = 0;
		for(Item item : this.items) {
			s += item.getProduto().getPreco().doubleValue() * item.getQuantidade();
		}
		return s;
	}

	private int temProduto(Produto produto) {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getProduto().getId() == produto.getId()) {
				return i;
			}
		}
		return -1;
	}

}