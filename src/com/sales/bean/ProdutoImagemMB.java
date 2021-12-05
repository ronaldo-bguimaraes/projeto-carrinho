package com.sales.bean;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.sales.entities.Produto;
import com.sales.models.ProdutoModel;

@ManagedBean
@ApplicationScoped
public class ProdutoImagemMB {
  private ProdutoModel produtoModel = new ProdutoModel();
  private Produto produto = new Produto();

  public StreamedContent getImagem() throws IOException {
    FacesContext context = FacesContext.getCurrentInstance();

    if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
      // So, we're rendering the HTML. Return a stub StreamedContent so that it will
      // generate right URL.
      return new DefaultStreamedContent();
    }
    else {
      // So, browser is requesting the image. Return a real StreamedContent with the
      // image bytes.
      String id = context.getExternalContext().getRequestParameterMap().get("imagemId");
      produto = produtoModel.find(Integer.parseInt(id));
      return new DefaultStreamedContent(new ByteArrayInputStream(produto.getFoto()));
    }
  }

  public String getImagemString() throws IOException {
    FacesContext context = FacesContext.getCurrentInstance();

    if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
      // So, we're rendering the HTML. Return a stub StreamedContent so that it will
      // generate right URL.
      return new DefaultStreamedContent().toString();
    }
    else {
      // So, browser is requesting the image. Return a real StreamedContent with the
      // image bytes.
      String id = context.getExternalContext().getRequestParameterMap().get("imagemId");
      produto = produtoModel.find(Integer.parseInt(id));
      return new DefaultStreamedContent(new ByteArrayInputStream(produto.getFoto())).toString();
    }
  }
}
