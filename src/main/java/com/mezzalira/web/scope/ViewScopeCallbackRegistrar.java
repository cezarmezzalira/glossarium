/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mezzalira.web.scope;

import java.util.HashMap;
import java.util.Map;
import javax.faces.component.UIViewRoot;
import javax.faces.event.*;
import javax.faces.event.ViewMapListener;

/**
 *
 * @author Cezar Mezzalira
 */
public class ViewScopeCallbackRegistrar implements ViewMapListener {

    public void processEvent(SystemEvent event) throws AbortProcessingException {
        if (event instanceof PostConstructViewMapEvent) {
            PostConstructViewMapEvent viewMapEvent = (PostConstructViewMapEvent) event;
            UIViewRoot viewRoot = (UIViewRoot) viewMapEvent.getComponent();
            viewRoot.getViewMap().put(ViewScope.VIEW_SCOPE_CALLBACKS, new HashMap<String, Runnable>());
        } else if (event instanceof PreDestroyViewMapEvent) {
            PreDestroyViewMapEvent viewMapEvent = (PreDestroyViewMapEvent) event;
            UIViewRoot viewRoot = (UIViewRoot) viewMapEvent.getComponent();
            Map<String, Runnable> callbacks = (Map<String, Runnable>) viewRoot.getViewMap().get(ViewScope.VIEW_SCOPE_CALLBACKS);
            if (callbacks != null) {
                for (Runnable c : callbacks.values()) {
                    c.run();
                }
                callbacks.clear();
            }
        }
    }

    public boolean isListenerForSource(Object source) {
        return source instanceof UIViewRoot;
    }
}
