package org.bapakos.controller.admin;

import org.bapakos.manager.ServiceManager;
import org.bapakos.manager.ViewManager;

public class AdminTransactionController {
    private ViewManager viewManager;
    private ServiceManager serviceManager;
    public void setViewManager(ViewManager viewManager) {this.viewManager = viewManager;}
    public void setServiceManager(ServiceManager serviceManager) {this.serviceManager = serviceManager;}
}
