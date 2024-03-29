package com.mezzalira.web.interceptor;

import com.mezzalira.model.entity.Usuario;
import com.mezzalira.web.util.JsfUtil;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

/**
 * Created by -Cezar on 03/02/14.
 */
public class LoginInterceptor implements PhaseListener {

    @Override
    public void afterPhase(PhaseEvent phaseEvent) {
        FacesContext facesContext = phaseEvent.getFacesContext();
        String currentPage = facesContext.getViewRoot().getViewId();

        Usuario usuario = (Usuario) JsfUtil.getAttributeSession("usuario");
        if (currentPage.contains("pages/admin") && usuario == null) {
            NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
            nh.handleNavigation(facesContext, null, "loginPage");
        }
    }

    @Override
    public void beforePhase(PhaseEvent phaseEvent) {
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
}
