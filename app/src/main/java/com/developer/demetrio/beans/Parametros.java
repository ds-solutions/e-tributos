package com.developer.demetrio.beans;

import android.content.ContentValues;
import android.database.Cursor;

import com.developer.demetrio.fachada.Fachada;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Parametros extends ObjetoBasico implements Serializable {
    private static final long serialVersionUID = 1;
    private static Parametros instancia = null;
    private static String[] colunas = new String[]{};

    //
    private Long idUsuario;
    private String login;
    private String senha;

    // DADOS DO OBJETO IMÓVEL
    private Long idImovel;

    // DADOS DO OBJETO CADASTRO
    private Long idCadastro;
    private String inscricao;
    private String numCadastro;
    private String distrito;
    private String setor;
    private String quadra;
    private String lote;
    private String unidade;

    // DADOS DO OBJETO AREAS DO IMOVEL
    private Long idAreaDoImovel;
    private String testada;
    private String areaDoTerreno;
    private String areaTotalDoTerreno;
    private String edificado;
    private String areaTotalEdificado;
    private String excedente;
    private String fracao;

    // DADOS DO OBJETO ALICOTAS
    private Long idAlicota;
    private String alicotaDoTerreno;
    private String alicotaDaEdificado;
    private String zoneamento;
    private String tipoConstrucao;

    // DADOS DO OBJETO CODIGO DE COBRANÇA
    private Long idCodigoCobranca;
    private String tipo;
    private String taxaTestada;

    // DADOS DO OBJETO VALRES VENAIS
    private Long idValoresVenais;
    private String valoresVenaisTerreno;
    private String valoresVenaisEdificada;
    private String valoresVenaisExcedente;
    private String valoresVenaisTotal;

    // DADOS DO ENDERECO
    private Long idEndereco;
    private String cidade;
    private String uf;
    private String bairro;
    private String logradouro;
    private String complemento;
    private String numero;
    private String cep;

    // DADOS DO CONTRIBUINTE
    private Long idContribuinte;
    private String nome;
    private String cpf;
    private String rg;
    private String orgEmissor;
    private Date dataNasc;
    private String estadoCivil;
    private String nacionalidade;
    private String naturalidade;
    private String raca;
    private String sexo;

    // DADOS DO TRIBUTO
    private Long idTributo;

    // DADOS DO IPTU
    private Long idIPTU;
    private String codigoDaDivida;
    private String codigoDeBaixa;
    private String mensagem;
    private String mensagem1;
    private String mensagem2;
    private String exercicio;
    private String vencimento;
    private String valorTotal;
    private String somaDoValor;
    private String somaDoDesconto;
    private String somaIsencao;
    private String digitosDoCodigoDeBarras;
    private String campo1CodigoDeBarras;
    private String campo2CodigoDeBarras;
    private String campo3CodigoDeBarras;
    private String campo4CodigoDeBarras;

    // DADOS DA DESCRICAO DA DIVIDA
    private long idDescricaoDaDivida;
    private String codigoDaDescricaoDaDivida;
    private String descricaoDaDivida;
    private String valor;
    private String pontualidade;
    private String isencao;


    @Override
    public String[] getColunas() {
        return new String[0];
    }

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public String getNomeTabela() {
        return null;
    }

    @Override
    public <T extends ObjetoBasico> ArrayList<T> preencherObjetos(Cursor cursor) {
        return null;
    }

    @Override
    public ContentValues preencherValues() {
        return null;
    }

    public static Parametros getInstancia() {
        if (instancia == null) {
            instancia = Fachada.getInstance().buscarParametros();
        }
        return instancia;
    }



    public static void setColunas(String[] colunas) {
        Parametros.colunas = colunas;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Long getIdImovel() {
        return idImovel;
    }

    public void setIdImovel(Long idImovel) {
        this.idImovel = idImovel;
    }

    public Long getIdCadastro() {
        return idCadastro;
    }

    public void setIdCadastro(Long idCadastro) {
        this.idCadastro = idCadastro;
    }

    public String getInscricao() {
        return inscricao;
    }

    public void setInscricao(String inscricao) {
        this.inscricao = inscricao;
    }

    public String getNumCadastro() {
        return numCadastro;
    }

    public void setNumCadastro(String numCadastro) {
        this.numCadastro = numCadastro;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getQuadra() {
        return quadra;
    }

    public void setQuadra(String quadra) {
        this.quadra = quadra;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public Long getIdAreaDoImovel() {
        return idAreaDoImovel;
    }

    public void setIdAreaDoImovel(Long idAreaDoImovel) {
        this.idAreaDoImovel = idAreaDoImovel;
    }

    public String getTestada() {
        return testada;
    }

    public void setTestada(String testada) {
        this.testada = testada;
    }

    public String getAreaDoTerreno() {
        return areaDoTerreno;
    }

    public void setAreaDoTerreno(String areaDoTerreno) {
        this.areaDoTerreno = areaDoTerreno;
    }

    public String getAreaTotalDoTerreno() {
        return areaTotalDoTerreno;
    }

    public void setAreaTotalDoTerreno(String areaTotalDoTerreno) {
        this.areaTotalDoTerreno = areaTotalDoTerreno;
    }

    public String getEdificado() {
        return edificado;
    }

    public void setEdificado(String edificado) {
        this.edificado = edificado;
    }

    public String getAreaTotalEdificado() {
        return areaTotalEdificado;
    }

    public void setAreaTotalEdificado(String areaTotalEdificado) {
        this.areaTotalEdificado = areaTotalEdificado;
    }

    public String getExcedente() {
        return excedente;
    }

    public void setExcedente(String excedente) {
        this.excedente = excedente;
    }

    public String getFracao() {
        return fracao;
    }

    public void setFracao(String fracao) {
        this.fracao = fracao;
    }

    public Long getIdAlicota() {
        return idAlicota;
    }

    public void setIdAlicota(Long idAlicota) {
        this.idAlicota = idAlicota;
    }

    public String getAlicotaDoTerreno() {
        return alicotaDoTerreno;
    }

    public void setAlicotaDoTerreno(String alicotaDoTerreno) {
        this.alicotaDoTerreno = alicotaDoTerreno;
    }

    public String getAlicotaDaEdificado() {
        return alicotaDaEdificado;
    }

    public void setAlicotaDaEdificado(String alicotaDaEdificado) {
        this.alicotaDaEdificado = alicotaDaEdificado;
    }

    public String getZoneamento() {
        return zoneamento;
    }

    public void setZoneamento(String zoneamento) {
        this.zoneamento = zoneamento;
    }

    public String getTipoConstrucao() {
        return tipoConstrucao;
    }

    public void setTipoConstrucao(String tipoConstrucao) {
        this.tipoConstrucao = tipoConstrucao;
    }

    public Long getIdCodigoCobranca() {
        return idCodigoCobranca;
    }

    public void setIdCodigoCobranca(Long idCodigoCobranca) {
        this.idCodigoCobranca = idCodigoCobranca;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTaxaTestada() {
        return taxaTestada;
    }

    public void setTaxaTestada(String taxaTestada) {
        this.taxaTestada = taxaTestada;
    }

    public Long getIdValoresVenais() {
        return idValoresVenais;
    }

    public void setIdValoresVenais(Long idValoresVenais) {
        this.idValoresVenais = idValoresVenais;
    }

    public String getValoresVenaisTerreno() {
        return valoresVenaisTerreno;
    }

    public void setValoresVenaisTerreno(String valoresVenaisTerreno) {
        this.valoresVenaisTerreno = valoresVenaisTerreno;
    }

    public String getValoresVenaisEdificada() {
        return valoresVenaisEdificada;
    }

    public void setValoresVenaisEdificada(String valoresVenaisEdificada) {
        this.valoresVenaisEdificada = valoresVenaisEdificada;
    }

    public String getValoresVenaisExcedente() {
        return valoresVenaisExcedente;
    }

    public void setValoresVenaisExcedente(String valoresVenaisExcedente) {
        this.valoresVenaisExcedente = valoresVenaisExcedente;
    }

    public String getValoresVenaisTotal() {
        return valoresVenaisTotal;
    }

    public void setValoresVenaisTotal(String valoresVenaisTotal) {
        this.valoresVenaisTotal = valoresVenaisTotal;
    }

    public Long getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(Long idEndereco) {
        this.idEndereco = idEndereco;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Long getIdContribuinte() {
        return idContribuinte;
    }

    public void setIdContribuinte(Long idContribuinte) {
        this.idContribuinte = idContribuinte;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getOrgEmissor() {
        return orgEmissor;
    }

    public void setOrgEmissor(String orgEmissor) {
        this.orgEmissor = orgEmissor;
    }

    public Date getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(Date dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getNaturalidade() {
        return naturalidade;
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Long getIdTributo() {
        return idTributo;
    }

    public void setIdTributo(Long idTributo) {
        this.idTributo = idTributo;
    }

    public Long getIdIPTU() {
        return idIPTU;
    }

    public void setIdIPTU(Long idIPTU) {
        this.idIPTU = idIPTU;
    }

    public String getCodigoDaDivida() {
        return codigoDaDivida;
    }

    public void setCodigoDaDivida(String codigoDaDivida) {
        this.codigoDaDivida = codigoDaDivida;
    }

    public String getCodigoDeBaixa() {
        return codigoDeBaixa;
    }

    public void setCodigoDeBaixa(String codigoDeBaixa) {
        this.codigoDeBaixa = codigoDeBaixa;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem1() {
        return mensagem1;
    }

    public void setMensagem1(String mensagem1) {
        this.mensagem1 = mensagem1;
    }

    public String getMensagem2() {
        return mensagem2;
    }

    public void setMensagem2(String mensagem2) {
        this.mensagem2 = mensagem2;
    }

    public String getExercicio() {
        return exercicio;
    }

    public void setExercicio(String exercicio) {
        this.exercicio = exercicio;
    }

    public String getVencimento() {
        return vencimento;
    }

    public void setVencimento(String vencimento) {
        this.vencimento = vencimento;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getSomaDoValor() {
        return somaDoValor;
    }

    public void setSomaDoValor(String somaDoValor) {
        this.somaDoValor = somaDoValor;
    }

    public String getSomaDoDesconto() {
        return somaDoDesconto;
    }

    public void setSomaDoDesconto(String somaDoDesconto) {
        this.somaDoDesconto = somaDoDesconto;
    }

    public String getSomaIsencao() {
        return somaIsencao;
    }

    public void setSomaIsencao(String somaIsencao) {
        this.somaIsencao = somaIsencao;
    }

    public String getDigitosDoCodigoDeBarras() {
        return digitosDoCodigoDeBarras;
    }

    public void setDigitosDoCodigoDeBarras(String digitosDoCodigoDeBarras) {
        this.digitosDoCodigoDeBarras = digitosDoCodigoDeBarras;
    }

    public String getCampo1CodigoDeBarras() {
        return campo1CodigoDeBarras;
    }

    public void setCampo1CodigoDeBarras(String campo1CodigoDeBarras) {
        this.campo1CodigoDeBarras = campo1CodigoDeBarras;
    }

    public String getCampo2CodigoDeBarras() {
        return campo2CodigoDeBarras;
    }

    public void setCampo2CodigoDeBarras(String campo2CodigoDeBarras) {
        this.campo2CodigoDeBarras = campo2CodigoDeBarras;
    }

    public String getCampo3CodigoDeBarras() {
        return campo3CodigoDeBarras;
    }

    public void setCampo3CodigoDeBarras(String campo3CodigoDeBarras) {
        this.campo3CodigoDeBarras = campo3CodigoDeBarras;
    }

    public String getCampo4CodigoDeBarras() {
        return campo4CodigoDeBarras;
    }

    public void setCampo4CodigoDeBarras(String campo4CodigoDeBarras) {
        this.campo4CodigoDeBarras = campo4CodigoDeBarras;
    }

    public long getIdDescricaoDaDivida() {
        return idDescricaoDaDivida;
    }

    public void setIdDescricaoDaDivida(long idDescricaoDaDivida) {
        this.idDescricaoDaDivida = idDescricaoDaDivida;
    }

    public String getCodigoDaDescricaoDaDivida() {
        return codigoDaDescricaoDaDivida;
    }

    public void setCodigoDaDescricaoDaDivida(String codigoDaDescricaoDaDivida) {
        this.codigoDaDescricaoDaDivida = codigoDaDescricaoDaDivida;
    }

    public String getDescricaoDaDivida() {
        return descricaoDaDivida;
    }

    public void setDescricaoDaDivida(String descricaoDaDivida) {
        this.descricaoDaDivida = descricaoDaDivida;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getPontualidade() {
        return pontualidade;
    }

    public void setPontualidade(String pontualidade) {
        this.pontualidade = pontualidade;
    }

    public String getIsencao() {
        return isencao;
    }

    public void setIsencao(String isencao) {
        this.isencao = isencao;
    }
}
