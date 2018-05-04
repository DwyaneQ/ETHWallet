package com.gongchuang.ethtoken.domain;

/**
 * Created by dwq on 2018/3/22/022.
 * e-mail:lomapa@163.com
 */

public class VerifyMnemonicWordTag {
    private String mnemonicWord;
    private boolean isSelected;

    public String getMnemonicWord() {
        return mnemonicWord;
    }

    public void setMnemonicWord(String mnemonicWord) {
        this.mnemonicWord = mnemonicWord;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


}
