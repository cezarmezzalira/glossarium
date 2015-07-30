package com.mezzalira.web.controller;

import com.mezzalira.model.entity.*;
import com.mezzalira.model.framework.ICrudService;
import com.mezzalira.model.service.*;
import com.mezzalira.web.comparator.SiglaComparator;
import com.mezzalira.web.framework.CrudController;
import com.mezzalira.web.model.SiglaDataModel;
import com.mezzalira.web.util.ReadWordUtil;
import com.mezzalira.web.util.Termo;
import com.mezzalira.web.util.WordPOIUtil;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.servlet.http.HttpServletResponse;
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

    private ReadWordUtil readWordUtil;

    private List<String> terms = new ArrayList<>();

    private List<Sigla> siglas;
    //private List<Sigla> siglas = new ArrayList<>();

    private SiglaDataModel siglaDataModel;

    private String destination = "";
    private String fileName = "";
    private String fileExtension = "";
    private List<Sigla> itensAdicionados;

    public SiglaController() {
        this.destination = "/home/cezar/dev/temp/";
        itensAdicionados = new ArrayList<>();
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

            Collections.sort(terms);

            for (String term : terms) {
                builder.append(term).append(" - ");
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

    public void pesquisarSiglas() {

        //converte a lista em set para fazer a pesquisa no banco
        Set<String> termos = new HashSet<>();
        for (String termo : terms) {
            termos.add(termo);
        }

        //faz a consulta das siglas que estavam no documento
        lsEntity = siglaService.findBySiglaIn(termos);

        addToModel();

    }

    public void atualizarDocumento() {

        String path = destination + fileName;
        String pathDownload = destination + "Glossarium_" + fileName;

        //adicionarNaLista();

        WordPOIUtil wordPOIUtil = new WordPOIUtil();
        boolean generateFile = wordPOIUtil.replaceAndGenerateWord(path, pathDownload, "#LISTASIGLA", itensAdicionados);

        //chamo a tela para download
        //RequestContext.getCurrentInstance().execute("PF('dialogDownload').show()");
        try {
            if (generateFile) {
                downloadFile(pathDownload);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void downloadFile(String pathDownload) throws IOException {

        //Cria instancia do arquivo
        File file = new File(pathDownload);

        // Get HTTP response

        ExternalContext ec = getFacesContext().getExternalContext();

        ec.responseReset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand. We want to get rid of them, else it may collide.
        ec.setResponseContentType("application/msword"); // Check http://www.iana.org/assignments/media-types for all types. Use if necessary ExternalContext#getMimeType() for auto-detection based on filename.
        ec.setResponseHeader("Content-Disposition", "attachment; filename=\"Glossarium_" + fileName + "\""); // The Save As popup magic is done here. You can give it any file name you want, this only won't work in MSIE, it will use current request URL as file name instead.

        // Open response output stream
        OutputStream responseOutputStream = ec.getResponseOutputStream();

        // Read PDF contents
        FileInputStream fileInputStream = new FileInputStream(file);
        // Read PDF contents and write them to the output
        byte[] bytesBuffer = new byte[2048];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(bytesBuffer)) > 0) {
            responseOutputStream.write(bytesBuffer, 0, bytesRead);
        }

        // Make sure that everything is out
        responseOutputStream.flush();

        // Close both streams
        fileInputStream.close();
        responseOutputStream.close();

        // JSF doc:
        // Signal the JavaServer Faces implementation that the HTTP response for this request has already been generated
        // (such as an HTTP redirect), and that the request processing lifecycle should be terminated
        // as soon as the current phase is completed.
        getFacesContext().responseComplete();

    }

    //Caso necessite, posso utilizar este método para criar um HashSet ordenado.
    //Cezar 05-04-2015
    public void adicionarNaLista() {
        for (Sigla sigla : siglas) {

            if (!itensAdicionados.contains(sigla)) {
                itensAdicionados.add(sigla);
            }
        }
        //Ordeno a lista depois de inserir os dados para que a visualização do relatório fique correta.
        Collections.sort(itensAdicionados, new SiglaComparator());
    }


    private void addToModel() {
        siglaDataModel = new SiglaDataModel(lsEntity);
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


    public List<String> getTerms() {
        return terms;
    }

    public void setTerms(List<String> terms) {
        this.terms = terms;
    }

    public SiglaDataModel getSiglaDataModel() {
        return siglaDataModel;
    }

    public List<Sigla> getSiglas() {
        return siglas;
    }

    public void setSiglas(List<Sigla> siglas) {
        this.siglas = siglas;
    }

    public void onRowSelect(SelectEvent event) {
        if (!itensAdicionados.contains(((Sigla) event.getObject()))) {
            itensAdicionados.add(((Sigla) event.getObject()));
        }

    }

    public void onRowUnselect(UnselectEvent event) {
        itensAdicionados.remove(((Sigla) event.getObject()));
    }

}
