package com.developer.demetrio.impressao;

import android.content.Context;

import com.developer.demetrio.execoes.ImpressaoException;
import com.developer.demetrio.impressao.utils.ZebraUtils;
import com.developer.demetrio.model.Imovel;
import com.developer.demetrio.util.ConstantesSistemas;
import com.zebra.sdk.util.internal.StringUtilities;
//import com.developer.demetrio.zebra.android.util.internal.StringUtilities;
//import com.itextpdf.text.Document;

import java.io.BufferedOutputStream;

public class ImpressaoTributo extends Impressao{
   // protected StringBuilder buffer;
    private Context context;

   // private FormatarIPTU formatarIPTU;

    public StringBuilder coletarIptuFormatado(Imovel imovel) {
        this.buffer = new StringBuilder();
   //   this.formatarIPTU = new FormatarIPTU();
      //  Document document = new Document();
     //  document =  formatarIPTU.gerarIPTU(imovel,this.context);
       /* if (document != null && !document.equals("")) {
           //this.buffer.append(document);

            buffer.append("Testando o comando imprimir com StringBuilder");
            this.buffer.append(comandoImprimir());

        }*/
        buffer.append("Testando o comando imprimir com StringBuilder");
        buffer.append("\n");
        this.buffer.append(comandoImprimir());
        this.buffer.append(imovel.getCadastro().getNumCadastro(),0, 6);
        buffer.append("\n");
        this.buffer.append(comandoImprimir());
       this.buffer.append(formarLinha(7, 0, 40, 140, imovel.getCadastro().getNumCadastro(), 0, 0));
        buffer.append("\n");
        this.buffer.append(comandoImprimir());
        this.buffer.append(formarLinha(7, 0, 100, 140, imovel.getCadastro().getInscricao(), 0, 0) + StringUtilities.LF);
        buffer.append("\n");
      //  this.buffer.append(comandoImprimir());
        this.buffer.append(comandoImprimir());
      return this.buffer;
    }

    public boolean print(Context context, Imovel imovel) throws ImpressaoException {
        this.context = context;
        System.out.println("Entrou no método print com os dados "+ context.toString() + " imovel "+ imovel.getClass().toString());
       // Zebra
       return ZebraUtils.getInstance(this.context).imprimir(coletarIptuFormatado(imovel));
       // return true;
    }

    protected String comandoImprimir() {
        return "FORM\nPRINT ";
    }

}
