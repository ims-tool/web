package br.com.ims.util;

import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/rest")
public class ApplicationConfig extends Application {

	public Set<Class<?>> getClasses() {
		Set<Class<?>> resources = new java.util.HashSet<>();
		addRestResourceClasses(resources);
		return resources;
	}

	private void addRestResourceClasses(Set<Class<?>> resources) {
		resources.add(br.com.ims.facade.VersionFacadeREST.class);
		resources.add(br.com.ims.facade.ParametersFacadeREST.class);
		resources.add(br.com.ims.facade.ServiceHourFacadeREST.class);
		resources.add(br.com.ims.facade.UserControlFacadeREST.class);
		resources.add(br.com.ims.facade.LogAuditFacadeREST.class);
		resources.add(br.com.ims.facade.MessageFacadeREST.class);
		resources.add(br.com.ims.facade.RouterFacadeREST.class);
	}
}