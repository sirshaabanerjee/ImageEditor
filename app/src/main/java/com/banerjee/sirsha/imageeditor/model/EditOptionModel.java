package com.banerjee.sirsha.imageeditor.model;

/**
 * Created by Sirsha Banerjee on 19/1/18.
 */

public class EditOptionModel {

    public String editOptionName = "";
    public int editOptionImageResourceID;

    public String getEditOptionName() {
        return editOptionName;
    }

    public void setEditOptionName(String editOptionName) {
        this.editOptionName = editOptionName;
    }

    public int getEditOptionImageResourceID() {
        return editOptionImageResourceID;
    }

    public void setEditOptionImageResourceID(int editOptionImageResourceID) {
        this.editOptionImageResourceID = editOptionImageResourceID;
    }
}
