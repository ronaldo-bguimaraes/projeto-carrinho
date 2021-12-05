package com.sales.bean;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.imageio.ImageIO;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.sales.entities.Produto;
import com.sales.models.ProdutoModel;
//import com.sales.util.UploadArquivo;

@SessionScoped
@ManagedBean(name = "produtoMB")
public class ProdutoMB {

  private List<Produto> produtos;
  private ProdutoModel produtoModel;
  private Produto produto;
  // Objetos para manipulação e gravação da imagem no banco de dados
  // private UploadArquivo arquivo = new UploadArquivo();
  private UploadedFile file;
  byte[] bImagem;
  private StreamedContent sc;
  private String produtoId;

  public ProdutoMB() {
    produto = new Produto();
    produtoModel = new ProdutoModel();
    this.produtos = produtoModel.findAll();
  }

  public Produto getProduto() {
    return produto;
  }

  public Produto buscarProduto(int id) {
    produtoModel = new ProdutoModel();
    produto = produtoModel.find(id);
    return produto;
  }

  public String detalhesProduto() {
    produtoId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("produtoId");
    produto = produtoModel.find(Integer.parseInt(produtoId));
    return "product-details.xhtml";
  }

  public void setProduto(Produto produto) {
    this.produto = produto;
  }

  public List<Produto> getProdutos() {
    this.produtos = produtoModel.findAll();
    return produtos;
  }

  public void setProdutos(List<Produto> produtos) {
    this.produtos = produtos;
  }

  public UploadedFile getFile() {
    return file;
  }

  public void setFile(UploadedFile file) {
    this.file = file;
    // this.produto.setFoto(file.getFileName().toString());
    this.bImagem = new byte[(int) file.getSize()];
    this.bImagem = file.getContents();
  }

// public UploadArquivo getArquivo() {
//  return arquivo;
// }
//
// public void setArquivo(UploadArquivo arquivo) {
//  this.arquivo = arquivo;
// }

  public byte[] getbImagem() {
    return bImagem;
  }

  public void setbImagem(byte[] bImagem) {
    this.bImagem = bImagem;
  }

  public StreamedContent getSc() {
    return sc;
  }

  public void setSc(StreamedContent sc) {
    this.sc = sc;
  }

  public String getProdutoId() {
    return produtoId;
  }

  public void setProdutoId(String produtoId) {
    this.produtoId = produtoId;
  }

  public String index() {
    this.produtos = produtoModel.findAll();
    return "/pages/page-index-1.xhtml?faces-redirect=true";
  }

  public void saveOrUpdate() {
    produto.setFoto(this.getbImagem());
    produtoModel.saveOrUpdate(produto);
    produto = new Produto();
  }

  public void salvar() {
    produtoModel.saveOrUpdate(produto);
    produto = new Produto();
  }

  /*
   * public void uploadAction(FileUploadEvent event) {
   * this.arquivo.fileUpload(event, ".jpg", "/home/alberto/Pictures/");
   * FacesMessage message = new FacesMessage("Sucesso!!! ",
   * event.getFile().getFileName() + " foi inserido.");
   * FacesContext.getCurrentInstance().addMessage(null, message); }
   * 
   */

  public void upload() {
    if (file != null) {
      FacesMessage message = new FacesMessage("Sucesso", file.getFileName() + " foi enviado.");
      FacesContext.getCurrentInstance().addMessage(null, message);
    }
  }

  public void handleFileUpload(FileUploadEvent event) {
    this.file = event.getFile();
    this.bImagem = event.getFile().getContents();
    String fileName = file.getFileName();
    long fileSize = file.getSize();
    String infoAboutFile = "Arquivo recebido:" + fileName + " Tamanho:" + fileSize;
    FacesMessage msg = new FacesMessage("Sucesso!!!", event.getFile().getFileName() + " foi lido. " + infoAboutFile);
    FacesContext.getCurrentInstance().addMessage(null, msg);
  }

  public void uploadAction(FileUploadEvent event) {
    this.bImagem = event.getFile().getContents();
  }

  // Helper Method 1 - Fetch Maximum School Id From The Database
  /*
   * private int getMaxProdId() {
   * 
   * int maxProdId = 1; Query queryObj =
   * emObj.createQuery("SELECT MAX(s.id)+1 FROM Produtos s");
   * if(queryObj.getSingleResult() != null) { maxProdId = (Integer)
   * queryObj.getSingleResult(); } return maxProdId; }
   */
  public BufferedImage exibirFoto(int id) {

    // byte[] foto = null;

    // LENDO E COPIANDO IMAGEM ##############################################
    BufferedImage img = null;
    // getProduto(id);

    try {
      img = ImageIO.read(new ByteArrayInputStream(buscarProduto(id).getFoto()));
      // lblFoto.setIcon(new ImageIcon(img));
      // ImageIO.write(img, "PNG", new File("h.png"));

    }
    catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return img;
  }

  public StreamedContent getImagem(int id) {
    System.out.println("Sistema de Imagens");
    return new DefaultStreamedContent(new ByteArrayInputStream(buscarProduto(id).getFoto()));
  }

  public StreamedContent getPhoto() {

    FacesContext context = FacesContext.getCurrentInstance();

    if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
      // So, we're rendering the view. Return a stub StreamedContent so that it will
      // generate right URL.
      return new DefaultStreamedContent();
    }
    else {
      // So, browser is requesting the image. Return a real StreamedContent with the
      // image bytes.
      int id = Integer.parseInt(context.getExternalContext().getRequestParameterMap().get("id"));
//       Image image = (Image) produto.getFoto();
      return new DefaultStreamedContent(new ByteArrayInputStream(buscarProduto(id).getFoto()));
    } 

  }

  public StreamedContent getImage() throws IOException {

    FacesContext context = FacesContext.getCurrentInstance();

    if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
      // So, we're rendering the view. Return a stub StreamedContent so that it will
      // generate right URL.
      return new DefaultStreamedContent();
    }
    else {
      // So, browser is requesting the image. Return a real StreamedContent with the
      // image bytes.
      // String id = context.getExternalContext().getRequestParameterMap().get("id");
      // Image image = (Image) produto.getFoto();
      return new DefaultStreamedContent(new ByteArrayInputStream(produto.getFoto()));
    }
  }
}