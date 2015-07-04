package com.mezzalira.web.controller;

import com.mezzalira.model.entity.*;
import com.mezzalira.model.framework.ICrudService;
import com.mezzalira.model.service.*;
import com.mezzalira.web.comparator.TermoComparator;
import com.mezzalira.web.framework.CrudController;
import com.mezzalira.web.model.SiglaDataModel;
import com.mezzalira.web.util.ReadWordUtil;
import com.mezzalira.web.util.Termo;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.faces.application.FacesMessage;
import javax.servlet.http.Part;
import java.io.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Cezar Mezzalira
 * Date: 14/12/13
 * Time: 17:32
 * To change this template use File | Settings | File Templates.
 */
@Controller
@Scope("view")
public class SiglaController extends CrudController<Sigla, Integer> {

    @Autowired
    private SiglaService siglaService;


    //demais objetos para auto-complete
    @Autowired
    private LinguagemService linguagemService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private SubAreaService subAreaService;

    @Autowired
    private TipoSiglaService tipoSiglaService;

    @Autowired
    private UsuarioService usuarioService;

    private ReadWordUtil readWordUtil;

    private List<Termo> terms = new ArrayList<>();

    private List<Sigla> siglas = new ArrayList<>();

    private SiglaDataModel siglaDataModel;

    private String destination = "";
    private String fileName = "";
    private String fileExtension = "";

    public SiglaController() {
        this.destination = "/home/cezar/dev/temp/";
    }

    @Override
    protected ICrudService<Sigla, Integer> getService() {
        return siglaService;
    }

    @Override
    protected String getUrlFormPage() {
        return "/pages/admin/siglaForm.xhtml?faces-redirect=true";
    }

    @Override
    public void find() {
        if (entity.getSigla() != null)
            lsEntity = siglaService.findBySigla(entity.getSigla());
        else
            lsEntity = siglaService.findAll();
    }

    public void showCadSigla() {

        RequestContext.getCurrentInstance().execute("PF('cadSigla').show()");
    }


    public void readArquivo(InputStream inputStream, String fileExtension) {

        readWordUtil = new ReadWordUtil();

        List<String> paragrafos = new ArrayList<>();

        //Verifica a extensão do arquivo e executa o método correto.
        if (ReadWordUtil.EXTENSION_FILE_DOCX.equalsIgnoreCase(fileExtension)) {
            paragrafos = readWordUtil.readDocxFile(inputStream);
        } else if (ReadWordUtil.EXTENSION_FILE_DOC.equalsIgnoreCase(fileExtension)) {
            paragrafos = readWordUtil.readDocFile(inputStream);
        }


        if (paragrafos.size() > 0) {
            terms = readWordUtil.findTerms(paragrafos);
            StringBuilder builder = new StringBuilder("Termos: ");

            Collections.sort(terms, new TermoComparator());

            for (Termo term : terms) {
                builder.append(term.getTermo()).append(" - ");
            }

            System.out.println(builder.toString());
        } else {
            addMessage(new FacesMessage("Nenhum termo foi encontrado no documento carregado."));
        }
    }


    public void upload(FileUploadEvent event) {
        try {
            //copia para pasta do sistema
            copyFile(event.getFile().getFileName(), event.getFile().getInputstream());

            //faz analise do arquivo que foi recebido
            fileName = event.getFile().getFileName();

            fileExtension = fileName.substring(fileName.lastIndexOf("."));
            readArquivo(event.getFile().getInputstream(), fileExtension);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void copyFile(String fileName, InputStream in) {
        try {
            // write the inputStream to a FileOutputStream
            OutputStream out = new FileOutputStream(new File(destination + fileName));

            int read;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            in.close();
            out.flush();
            out.close();

            addMessage(new FacesMessage("Upload do arquivo feito com sucesso!"));
        } catch (IOException e) {
            addMessage(new FacesMessage("Problemas ao fazer upload do arquivo!"));
            System.out.println(e.getMessage());
        }
    }

    public void pesquisarSiglas(){
        //converte a lista em set para fazer a pesquisa no banco
        Set<String> termos = new HashSet<>();
        for (Termo termo : terms){
            termos.add(termo.getTermo());
        }

        //faz a consulta das siglas que estavam no documento
        siglas = siglaService.findBySiglaIn(termos);

        addToModel();

    }

    private void addToModel() {
        siglaDataModel = new SiglaDataModel(siglas);
    }


    public void findNaoAprovados() {
        lsEntity = siglaService.findByDataAprovada();
    }

    //completes para objetos
    public List<Area> completeArea() {
        return areaService.complete();
    }

    public List<SubArea> completeSubArea() {
        return subAreaService.complete();
    }

    public List<Linguagem> completeLinguagem() {
        return linguagemService.complete();
    }

    public List<TipoSigla> completeTipoSigla() {
        return tipoSiglaService.complete();
    }

    public void reset() {
        entity = new Sigla();
        RequestContext.getCurrentInstance().execute("PF('cadSigla').hide()");
    }


    public List<Termo> getTerms() {
        return terms;
    }

    public void setTerms(List<Termo> terms) {
        this.terms = terms;
    }

    public SiglaDataModel getSiglaDataModel() {
        return siglaDataModel;
    }
}
