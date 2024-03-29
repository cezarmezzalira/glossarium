/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mezzalira.web.util;

import com.mezzalira.model.enumeration.EnumLabel;

import javax.faces.model.SelectItem;


public class EnumUtil {
    
    public static SelectItem[] populaSelectItem(Object[] values) {
        SelectItem[] itens = new SelectItem[values.length];
        int i = 0;
        for (Object value : values) {
            EnumLabel item = (EnumLabel) value;
            itens[i++] = new SelectItem(item, item.getLabel());
        }
        return itens;
    }
    
}
