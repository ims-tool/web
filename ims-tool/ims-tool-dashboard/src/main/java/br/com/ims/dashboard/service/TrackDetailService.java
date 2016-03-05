package br.com.ims.dashboard.service;

import java.util.List;

import br.com.ims.dashboard.dao.TrackDetailDAO;
import br.com.ims.dashboard.model.TagModel;
import br.com.ims.dashboard.model.TrackDetailModel;
import br.com.ims.dashboard.model.TrackServiceModel;

public class TrackDetailService {
	TrackDetailDAO dao = new TrackDetailDAO(); 
	public String getTrackDetailHtml(String logId) {
		String retorno = "<table class=\"table table-striped table-bordered table-hover\" id=\"dataTable-trackdetail\" >";
		
		List<TrackDetailModel> listTrack = dao.getTrackDetail(logId);
		
		for(TrackDetailModel model : listTrack) {
			retorno+= "'<tr><td>["+model.getRowdate()+"] "+model.getFormTypeName()+":</td><td>"+model.getFormId()+" - "+model.getFormName()+"</td><td>"+model.getDescription()+"</td></tr>";
			List<TrackServiceModel> listService = dao.getTrackServiceDetail(model.getTrackId(),model.getStartdate(),model.getStartdate(), "trackdetail", model.getFormTypeId());
			for(TrackServiceModel modelService : listService) {
				String tagName = modelService.getResultCall();

				if("TAG".equalsIgnoreCase(modelService.getMethodService())) {
					TagModel tag = dao.getTag(Integer.valueOf(modelService.getResultCall()));
					retorno+= "<tr><td></td><td></td><td>- "+modelService.getMethodService()+" --> TagId "+tag.getId().toString()+" - "+tag.getDescription();
				} else {
					retorno+= "<tr><td></td><td></td><td>"+modelService.getDescription()+" --> " +tagName;
				}
				
				if(modelService.getErrorCodeId() < 0) {
					retorno+= " -ERRO - ErrorCode: "+modelService.getErrorCodeId();
				}
				retorno+= "</td></tr>";
			}
		}
		retorno+= "</table>";
		
		return retorno;
		
	}
	public String getCheckListHtml(String logId) {
		String retorno = "<table class=\"table table-striped table-bordered table-hover\" id=\"dataTable-checklist\" >";
		
		List<TrackDetailModel> listTrack = dao.getCheckList(logId);
		
		for(TrackDetailModel model : listTrack) {
			retorno+= "'<tr><td>["+model.getRowdate()+"] "+model.getFormTypeName()+":</td><td>"+model.getFormId()+" - "+model.getFormName()+"</td><td>"+model.getDescription()+"</td></tr>";
			List<TrackServiceModel> listService = dao.getTrackServiceDetail(model.getTrackId(),model.getStartdate(),model.getStartdate(), "checklist", model.getFormTypeId());
			for(TrackServiceModel modelService : listService) {
				String tagName = modelService.getResultCall();

				if(modelService.getMethodService().equalsIgnoreCase("TAG")) {
					TagModel tag = dao.getTag(Integer.valueOf(modelService.getResultCall()));
					retorno+= "<tr><td></td><td></td><td>- "+modelService.getMethodService()+" --> TagId "+tag.getId().toString()+" - "+tag.getDescription();
				} else {
					retorno+= "<tr><td></td><td></td><td>"+modelService.getDescription()+" --> " +tagName;
				}
				
				if(modelService.getErrorCodeId() < 0) {
					retorno+= " -ERRO - ErrorCode: "+modelService.getErrorCodeId();
				}
				retorno+= "</td></tr>";
			}
		}
		retorno+= "</table>";
		
		return retorno;
	}
	public String getContext(String logId) {
		return dao.getContext(logId);
	}
	
}
