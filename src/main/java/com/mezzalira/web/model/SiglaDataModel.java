package com.mezzalira.web.model;

import com.mezzalira.model.entity.Sigla;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Cezar
 * Date: 24/01/14
 * Time: 18:45
 * To change this template use File | Settings | File Templates.
 */
public class SiglaDataModel extends ListDataModel<Sigla> implements SelectableDataModel<Sigla> {

    public SiglaDataModel() {
    }

    public SiglaDataModel(List<Sigla> data) {
        super(data);
    }

    @Override
    public Sigla getRowData(String rowKey) {

        List<Sigla> siglas = (List<Sigla>) getWrappedData();

        for(Sigla sigla : siglas) {
            if(sigla.getSiglaid().toString().equals(rowKey))
                return sigla;
        }

        return null;
    }

    @Override
    public Object getRowKey(Sigla sigla) {
        return sigla.getSiglaid();
    }



}
