package com.developer.demetrio.model.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public abstract class MaskEditUtil {

    public static final String FORMAT_CPF = "###.###.###-##";
    public static final String FORMAT_CNPJ = "##.###.###/####-##";
    public static final String FORMAT_CELLPHOONE = "## #####-####";
    public static final String FORMAT_PHOONE = "## ####-####";
    public static final String FORMAT_CEP = "#####-###";
    public static final String FORMAT_DATE = "##/##/####";
    public static final String FORMAT_INSCRICAO = "##.##.###.####.###";

    public static TextWatcher mask(final EditText editText, final String mask) {
        return new TextWatcher() {
            boolean atualizando;
            String old = "";

            @Override
            public void afterTextChanged(final Editable s) {

            }

            @Override
            public void beforeTextChanged(final CharSequence s, final int inicio, int cont, final int ultimo) {

            }

            @Override
            public void onTextChanged(final CharSequence s, final int inicio, final int antes, final int cont) {
                final String str = MaskEditUtil.unMask(s.toString());
                String mascara = "";

                if (atualizando) {
                    old = str;
                    atualizando = false;
                    return;
                }

                int i = 0;
                for (final char m : mask.toCharArray()) {
                    if (m != '#' && str.length() > old.length()) {
                        mascara += m;
                        continue;
                    }
                    try {
                        mascara += str.charAt(i);
                    }catch (final Exception e) {
                        e.printStackTrace();
                        break;
                    }
                    i++;
                }
                atualizando = true;
                editText.setText(mascara);
                editText.setSelection(mascara.length());
            }
        };
    }

    public static String unMask(final String s) {
        return s.replaceAll("[.]","").replaceAll("[-]","")
                .replaceAll("[/]", "").replaceAll("[(]", "")
                .replaceAll("[ ]", "").replaceAll("[:]", "")
                .replaceAll("[)]", "");
    }

}
