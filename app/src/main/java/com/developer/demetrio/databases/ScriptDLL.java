package com.developer.demetrio.databases;

public class ScriptDLL {


    //SQL PARA CRIAÇÃO DA TABELA LATLNG
    public static String getCreateLatLng() {
        StringBuilder sql = new StringBuilder();
        sql.append("    CREATE TABLE IF NOT EXISTS LATLNG( ");
        sql.append("ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ");
        sql.append("LATITUDE TEXT, ");
        sql.append("LONGITUDE TEXT) ");
        return sql.toString();
    }

    //SQL PARA CRIAÇÃO DA TABELA IPTU
    public static String getCreateTableIPTU() {
        StringBuilder sql = new StringBuilder();
        sql.append("    CREATE TABLE IF NOT EXISTS IPTU( ");
        sql.append("ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ");
        sql.append("CODIGO_DA_DIVIDA TEXT, ");
        sql.append("CODIGO_DE_BAIXA TEXT, ");
        sql.append("EXERCICIO TEXT, ");
        sql.append("MENSAGEM TEXT, ");
        sql.append("VALOR_TOTAL TEXT, ");
        sql.append("SOMA_DO_VALOR TEXT, ");
        sql.append("SOMA_DO_DESCONTO TEXT, ");
        sql.append("SOMA_DA_ISENCAO TEXT, ");
        sql.append("CODIGO_DE_BARRAS TEXT, ");
        sql.append("CAMPO_1 TEXT, ");
        sql.append("CAMPO_2 TEXT, ");
        sql.append("CAMPO_3 TEXT, ");
        sql.append("CAMPO_4 TEXT) ");
        return sql.toString();
    }

    //SQL PARA CRIAÇÃO DA TABELA DESCRIÇÃO DA DIVIDA
    public static String getCreateTableDescricaoDaDivida() {
        StringBuilder sql = new StringBuilder();
        sql.append("    CREATE TABLE IF NOT EXISTS DESCRICAO_DA_DIVIDA( ");
        sql.append("ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ");
        sql.append("CODIGO TEXT, ");
        sql.append("DESCRICAO TEXT, ");
        sql.append("VALOR TEXT, ");
        sql.append("PONTUALIDADE TEXT, ");
        sql.append("ISENCAO TEXT, ");
        sql.append("ID_IPTU INTEGER, ");

        sql.append("FOREIGN KEY(ID_IPTU) REFERENCES IPTU(ID)) ");
        return sql.toString();
    }

    //SQL PARA CRIAÇÃO DA TABELA TRIBUTOS
    public static String getCreateTableTributos() {
        StringBuilder sql = new StringBuilder();
        sql.append("    CREATE TABLE IF NOT EXISTS TRIBUTOS( ");
        sql.append("ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ");
        sql.append("ID_IPTU INTEGER, ");

        sql.append("FOREIGN KEY(ID_IPTU) REFERENCES IPTU(ID)) ");
        return sql.toString();
    }

    //SQL PARA CRIAÇÃO DA TABELA ATUALIZAÇÃO DO CONTRIBUINTE
    public static String getCreateTableAtualizacaoDoContribuinte() {
        StringBuilder sql = new StringBuilder();
        sql.append("    CREATE TABLE IF NOT EXISTS AUTALIZACAO_DO_CONTRIBUINTE( ");
        sql.append("ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ");
        sql.append("NOME TEXT, ");
        sql.append("CPF_CNPJ TEXT, ");
        sql.append("RG TEXT, ");
        sql.append("ORG_EMISSOR TEXT, ");
        sql.append("ESTADO_CIVIL TEXT, ");
        sql.append("SEXO TEXT, ");
        sql.append("COR TEXT, ");
        sql.append("NACIONALIDADE TEXT, ");
        sql.append("NATURALIDADE TEXT, ");
        sql.append("DATA_NASC TEXT, ");
        sql.append("TIPO_PESSOA TEXT, ");
        sql.append("ESCOLARIDADE TEXT, ");
        sql.append("TELEFONE TEXT, ");
        sql.append("CELULAR TEXT, ");
        sql.append("EMAIL TEXT) ");
         return sql.toString();
    }

    //SQL PARA CRIAÇÃO DA TABELA DOS DADOS CADASTRADOS DO CONTRIBUINTE
    public static String getCreateTableDadosCadastradosDoContribuinte() {
        StringBuilder sql = new StringBuilder();
        sql.append("    CREATE TABLE IF NOT EXISTS DADOS_DO_CONTRIBUINTE( ");
        sql.append("ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ");
        sql.append("NOME TEXT, ");
        sql.append("CPF TEXT, ");
        sql.append("RG TEXT, ");
        sql.append("ORG_EMISSOR TEXT, ");
        sql.append("DATA_NASC TEXT, ");
        sql.append("ESTADO_CIVIL TEXT, ");
        sql.append("NACIONALIDADE TEXT, ");
        sql.append("NATURALIDADE TEXT, ");
        sql.append("COR TEXT, ");
        sql.append("SEXO TEXT, ");
        sql.append("EMAIL TEXT, ");
        sql.append("CELULAR TEXT) ");

        return sql.toString();
    }

    //SQL PARA CRIAÇÃO DA TABELA DO CONTRIBUINTE
    public static String getCreateTableContribuinte() {
        StringBuilder sql = new StringBuilder();
        sql.append("    CREATE TABLE IF NOT EXISTS CONTRIBUINTE( ");
        sql.append("ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ");
        sql.append("ID_DADOS_DO_CONTRIBUINTE INTEGER, ");
        sql.append("ID_AUTALIZACAO_DO_CONTRIBUINTE INTEGER, ");
        sql.append("FOREIGN KEY(ID_DADOS_DO_CONTRIBUINTE) REFERENCES DADOS_DO_CONTRIBUINTE(ID), ");
        sql.append("FOREIGN KEY(ID_AUTALIZACAO_DO_CONTRIBUINTE) REFERENCES AUTALIZACAO_DO_CONTRIBUINTE(ID)) ");
        return sql.toString();
    }

    //SQL PARA CRIAÇÃO DA TABELA ENDEREÇO
    public static String getCreateTableEndereco() {
        StringBuilder sql = new StringBuilder();
        sql.append("    CREATE TABLE IF NOT EXISTS ENDERECO( ");
        sql.append("ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ");
        sql.append("CIDADE TEXT, ");
        sql.append("UF TEXT, ");
        sql.append("BAIRRO TEXT, ");
        sql.append("LOGRADOURO TEXT, ");
        sql.append("COMPLEMENTO TEXT, ");
        sql.append("NUMERO TEXT, ");
        sql.append("CEP TEXT) ");
        return sql.toString();
    }

    //SQL PARA CRIAÇÃO DA TABELA CODIGO DE COBRANÇA
    public static String getCreateTableCodigoDeCobranca() {
        StringBuilder sql = new StringBuilder();
        sql.append("    CREATE TABLE IF NOT EXISTS CODIGO_DE_COBRANCA( ");
        sql.append("ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ");
        sql.append("TIPO TEXT, ");
        sql.append("TAXA_TESTADA TEXT) ");

        return sql.toString();
    }

    //SQL PARA CRIAÇÃO DA TABELA ALIQUOTA
    public static String getCreateTableAliquota() {
        StringBuilder sql = new StringBuilder();
        sql.append("    CREATE TABLE IF NOT EXISTS ALIQUOTA( ");
        sql.append("ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ");
        sql.append("TERRENO TEXT, ");
        sql.append("EDIFICADO TEXT, ");
        sql.append("ZONEAMENTO TEXT, ");
        sql.append("TIPO_CONSTRUCAO TEXT, ");
        sql.append("ID_CODIGO_DE_COBRANCA, INTEGER, ");
        sql.append("FOREIGN KEY(ID_CODIGO_DE_COBRANCA) REFERENCES CODIGO_DE_COBRANCA(ID)) ");
        return sql.toString();
    }

    //SQL PARA CRIAÇÃO DA TABELA VALORES VENAIS
    public static String getCreateTableValoresVenais() {
        StringBuilder sql = new StringBuilder();
        sql.append("    CREATE TABLE IF NOT EXISTS VALORES_VENAIS( ");
        sql.append("ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ");
        sql.append("TERRENO TEXT, ");
        sql.append("EDIFICADA TEXT, ");
        sql.append("EXCEDENTE TEXT, ");
        sql.append("TOTAL TEXT) ");
        return sql.toString();
    }

    //SQL PARA CRIAÇÃO DA TABELA AREAS DO IMÓVEL
    public static String getCreateTableAreasDoImovel() {
        StringBuilder sql = new StringBuilder();
        sql.append("    CREATE TABLE IF NOT EXISTS AREAS_DO_IMOVEL( ");
        sql.append("ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ");
        sql.append("TESTADA TEXT, ");
        sql.append("AREA_DO_TERRENO TEXT, ");
        sql.append("AREA_TOTAL_DO_TERRENO TEXT, ");
        sql.append("EDIFICADO TEXT, ");
        sql.append("AREA_TOTAL_EDIFICADO TEXT, ");
        sql.append("EXCEDENTE TEXT, ");
        sql.append("FRACAO TEXT) ");
        return sql.toString();
    }

    //SQL PARA CRIAÇÃO DA TABELA CADASTRO
    public static String getCreateTableCadastro() {
        StringBuilder sql = new StringBuilder();
        sql.append("    CREATE TABLE IF NOT EXISTS CADASTRO( ");
        sql.append("ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ");
        sql.append("INSCRICAO TEXT, ");
        sql.append("NUM_CADASTRO TEXT, ");
        sql.append("DISTRITO TEXT, ");
        sql.append("SETOR TEXT, ");
        sql.append("QUADRA TEXT, ");
        sql.append("LOTE TEXT, ");
        sql.append("UNIDADE TEXT, ");
        sql.append("ID_AREAS_DO_IMOVEL, INTEGER, ");
        sql.append("ID_VALORES_VENAIS INTEGER, ");
        sql.append("ID_ALIQUOTA INTEGER, ");
        sql.append("FOREIGN KEY(ID_AREAS_DO_IMOVEL) REFERENCES AREAS_DO_IMOVEL(ID), ");
        sql.append("FOREIGN KEY(ID_VALORES_VENAIS) REFERENCES VALORES_VENAIS(ID), ");
        sql.append("FOREIGN KEY(ID_ALIQUOTA) REFERENCES ALIQUOTA(ID)) ");
        return sql.toString();
    }

    //SQL PARA CRIAÇÃO DA TABELA IMOVEL
    public static String getCreateTableImovel() {
        StringBuilder sql = new StringBuilder();
        sql.append("    CREATE TABLE IF NOT EXISTS IMOVEL( ");
        sql.append("ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ");
        sql.append("INDIC_EMISSAO_CONTA INTEGER, ");
        sql.append("INDIC_ENVIO_WHATSAAP INTEGER, ");
        sql.append("INDIC_ENVIO_EMAIL INTEGER, ");
        sql.append("ID_CADASTRO INTEGER, ");
        sql.append("ID_ENDERECO INTEGER, ");
        sql.append("ID_CONTRIBUINTE INTEGER, ");
        sql.append("ID_TRIBUTO INTEGER, ");
        sql.append("ID_LATLNG, INTEGER, ");
        sql.append("FOREIGN KEY(ID_CADASTRO) REFERENCES CADASTRO(ID), ");
        sql.append("FOREIGN KEY(ID_ENDERECO) REFERENCES ENDERECO(ID), ");
        sql.append("FOREIGN KEY(ID_CONTRIBUINTE) REFERENCES CONTRIBUINTE(ID), ");
        sql.append("FOREIGN KEY(ID_TRIBUTO) REFERENCES TRIBUTOS(ID), ");
        sql.append("FOREIGN KEY(ID_LATLNG) REFERENCES LATLNG(ID)) ");
      return sql.toString();
    }
}
