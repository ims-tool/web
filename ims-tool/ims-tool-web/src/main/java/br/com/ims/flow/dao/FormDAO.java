package br.com.ims.flow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.common.Constants;
import br.com.ims.flow.common.DbConnection;
import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.AbstractEntity;
import br.com.ims.flow.model.AnnounceEntity;
import br.com.ims.flow.model.AnswerEntity;
import br.com.ims.flow.model.ConditionEntity;
import br.com.ims.flow.model.DecisionEntity;
import br.com.ims.flow.model.DisconnectEntity;
import br.com.ims.flow.model.FlowEntity;
import br.com.ims.flow.model.FormEntity;
import br.com.ims.flow.model.FormTypeEntity;
import br.com.ims.flow.model.MenuEntity;
import br.com.ims.flow.model.PromptCollectEntity;
import br.com.ims.flow.model.ReturnEntity;
import br.com.ims.flow.model.TagEntity;
import br.com.ims.flow.model.TagTypeEntity;
import br.com.ims.flow.model.TransferEntity;

public class FormDAO extends AbstractDAO<FormEntity>{
	
	private static FormDAO instance = null;
	private DbConnection db = null;
	
	private FormDAO() {
		super();
		db = new DbConnection("");
	}
	
	public static FormDAO getInstance() {
		if(instance == null) {
			instance = new FormDAO();
		}
		return instance;
	}
	public Object getObject(FormTypeEntity formType, String objectId ) {
		Object obj = null;
		if(formType.getName().equals(Constants.FORM_TYPE_ANNOUNCE)) {
			obj = (Object)ServicesFactory.getInstance().getAnnounceService().get(objectId);
		}
		if(formType.getName().equals(Constants.FORM_TYPE_ANSWER)) {
			obj = (Object)ServicesFactory.getInstance().getAnswerService().get(objectId);
		}
		if(formType.getName().equals(Constants.FORM_TYPE_RETURN)) {
			obj = (Object)ServicesFactory.getInstance().getReturnService().get(objectId);
		}
		if(formType.getName().equals(Constants.FORM_TYPE_DISCONNECT)) {
			obj = (Object)ServicesFactory.getInstance().getDisconnectService().get(objectId);
		}
		if(formType.getName().equals(Constants.FORM_TYPE_PROMPT_COLLECT)) {
			obj = (Object)ServicesFactory.getInstance().getPromptCollectService().get(objectId);
		}
		if(formType.getName().equals(Constants.FORM_TYPE_MENU)) {
			obj = (Object)ServicesFactory.getInstance().getMenuService().get(objectId);
		}
		if(formType.getName().equals(Constants.FORM_TYPE_DECISION)) {
			obj = (Object)ServicesFactory.getInstance().getDecisionService().get(objectId);
		}
		if(formType.getName().equals(Constants.FORM_TYPE_FLOW)) {
			obj = (Object)ServicesFactory.getInstance().getFlowService().get(objectId);
		}
		if(formType.getName().equals(Constants.FORM_TYPE_TRANSFER)) {
			obj = (Object)ServicesFactory.getInstance().getTransferService().get(objectId);
		}
		if(formType.getName().equals(Constants.FORM_TYPE_OPERATION)) {
			obj = (Object)ServicesFactory.getInstance().getOperationService().get(objectId);
		}
		return obj;
	}
	
	public List<FormEntity> getByFilter(String where) {
		return this.getByFilter(where,false);
	}
	
	public List<FormEntity> getByFilter(String where, boolean lazy) {

		String sql = "SELECT f.id f_id,f.name f_name,f.description f_description,f.formtype f_formtype, f.formid f_formid, f.condition f_condition, f.positionx f_positionx, f.positiony f_positiony,"+ 
					 "t.id t_id, t.description t_description, "+ 
					 "tt.id tt_id, tt.name tt_name,tt.description tt_description "+
					 "FROM flow.form f "+
					 "INNER JOIN flow.formtype ft ON f.formtype = ft.id "+ 
					 "LEFT JOIN flow.tag t ON f.tag = t.id "+ 
					 "LEFT JOIN flow.tagtype tt ON t.tagtypeid = tt.id "+ 
					 "<WHERE> "+
					 "ORDER BY f.name ";
		if(where != null && where.length() > 0) {
			sql = sql.replace("<WHERE>", where);
		}
		sql = sql.replace("<WHERE>", "");
		List<FormEntity> result = new ArrayList<FormEntity>();
		ResultSet rs = null;
		try {
			rs = db.ExecuteQuery(sql);
			while(rs.next()) {
				TagEntity tag = null;
				if(rs.getString("t_id") != null && rs.getString("t_id").length() > 0) {
					TagTypeEntity tagType = new TagTypeEntity();
					tagType.setId(rs.getString("tt_id"));
					tagType.setName(rs.getString("tt_name"));
					tagType.setDescription(rs.getString("tt_description"));
					
					tag = new TagEntity();
					tag.setId(rs.getString("t_id"));
					tag.setDescription(rs.getString("t_description"));
					tag.setType(tagType);
				}
				
				FormTypeEntity formType = ServicesFactory.getInstance().getFormTypeService().get(rs.getString("f_formtype"));
				
				ConditionEntity condition = null;
				Object obj = null;
				if(!lazy) {		
					if(rs.getString("f_condition") != null && rs.getString("f_condition").length() > 0) {
						condition =  ServicesFactory.getInstance().getConditionService().get(rs.getString("f_condition"));
					}
					obj = this.getObject(formType, rs.getString("f_id"));
				}
				
				FormEntity form = new FormEntity();
				form.setId(rs.getString("f_id"));
				form.setName(rs.getString("f_name"));
				form.setDescription(rs.getString("f_description"));
				form.setFormType(formType);
				form.setFormId(obj);
				form.setCondition(condition);
				form.setTag(tag);
				form.setPositionX(rs.getString("f_positionx"));
				form.setPositionY(rs.getString("f_positiony"));

				result.add(form);
				
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			try {
				if(rs != null && !rs.isClosed())
					rs.close();
			} 
			catch(Exception e) {};
		}
		
		return result;
		
	}
	public List<FormEntity> getAll() {
		return this.getByFilter(null);
	}

	@Override
	public FormEntity get(String id) {
		// TODO Auto-generated method stub
		List<FormEntity> result = this.getByFilter("WHERE f.id = '"+id+"'");
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
		
	}
	public FormEntity get(String id,boolean lazy) {
		// TODO Auto-generated method stub
		List<FormEntity> result = this.getByFilter("WHERE f.id = '"+id+"'",lazy);
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
		
	}
	public List<FormEntity> getByTypeName(String typeName) {
		return this.getByFilter("WHERE lower(ft.name) = '"+typeName.toLowerCase()+"'");
		
	}

	public boolean saveObj(FormEntity entity) {
		boolean result = true;
		Object obj = entity.getFormId();
		if(entity.getFormType().getName().equals(Constants.FORM_TYPE_ANNOUNCE)) {
			result = ServicesFactory.getInstance().getAnnounceService().save((AnnounceEntity)obj);
		}
		if(entity.getFormType().getName().equals(Constants.FORM_TYPE_ANSWER)) {
			result = ServicesFactory.getInstance().getAnswerService().save((AnswerEntity)obj);
		}
		if(entity.getFormType().getName().equals(Constants.FORM_TYPE_RETURN)) {
			result = ServicesFactory.getInstance().getReturnService().save((ReturnEntity)obj);
		}
		if(entity.getFormType().getName().equals(Constants.FORM_TYPE_DISCONNECT)) {
			result = ServicesFactory.getInstance().getDisconnectService().save((DisconnectEntity)obj);
		}
		if(entity.getFormType().getName().equals(Constants.FORM_TYPE_PROMPT_COLLECT)) {
			result = ServicesFactory.getInstance().getPromptCollectService().save((PromptCollectEntity)obj);
		}
		if(entity.getFormType().getName().equals(Constants.FORM_TYPE_MENU)) {
			result = ServicesFactory.getInstance().getMenuService().save((MenuEntity)obj);
		}
		if(entity.getFormType().getName().equals(Constants.FORM_TYPE_DECISION)) {
			result = ServicesFactory.getInstance().getDecisionService().save((DecisionEntity)obj);
		}
		if(entity.getFormType().getName().equals(Constants.FORM_TYPE_FLOW)) {
			result = ServicesFactory.getInstance().getFlowService().save((FlowEntity)obj);
		}
		if(entity.getFormType().getName().equals(Constants.FORM_TYPE_TRANSFER)) {
			result = ServicesFactory.getInstance().getTransferService().save((TransferEntity)obj);
		}
		return result;
	}
	public boolean deleteObj(FormEntity entity) {
		boolean result = true;
		Object obj = entity.getFormId();
		if(entity.getFormType().getName().equals(Constants.FORM_TYPE_ANNOUNCE)) {
			result = ServicesFactory.getInstance().getAnnounceService().delete((AnnounceEntity)obj);
		}
		if(entity.getFormType().getName().equals(Constants.FORM_TYPE_ANSWER)) {
			result = ServicesFactory.getInstance().getAnswerService().delete((AnswerEntity)obj);
		}
		if(entity.getFormType().getName().equals(Constants.FORM_TYPE_RETURN)) {
			result = ServicesFactory.getInstance().getReturnService().delete((ReturnEntity)obj);
		}
		if(entity.getFormType().getName().equals(Constants.FORM_TYPE_DISCONNECT)) {
			result = ServicesFactory.getInstance().getDisconnectService().delete((DisconnectEntity)obj);
		}
		if(entity.getFormType().getName().equals(Constants.FORM_TYPE_PROMPT_COLLECT)) {
			result = ServicesFactory.getInstance().getPromptCollectService().delete((PromptCollectEntity)obj);
		}
		if(entity.getFormType().getName().equals(Constants.FORM_TYPE_MENU)) {
			result = ServicesFactory.getInstance().getMenuService().delete((MenuEntity)obj);
		}
		if(entity.getFormType().getName().equals(Constants.FORM_TYPE_DECISION)) {
			result = ServicesFactory.getInstance().getDecisionService().delete((DecisionEntity)obj);
		}
		if(entity.getFormType().getName().equals(Constants.FORM_TYPE_FLOW)) {
			result = ServicesFactory.getInstance().getFlowService().delete((FlowEntity)obj);
		}
		if(entity.getFormType().getName().equals(Constants.FORM_TYPE_TRANSFER)) {
			result = ServicesFactory.getInstance().getTransferService().delete((TransferEntity)obj);
		}
		return result;
	}
	public boolean updateObj(FormEntity entity) {
		boolean result = true;
		Object obj = entity.getFormId();
		if(entity.getFormType().getName().equals(Constants.FORM_TYPE_ANNOUNCE)) {
			result = ServicesFactory.getInstance().getAnnounceService().update((AnnounceEntity)obj);
		}
		if(entity.getFormType().getName().equals(Constants.FORM_TYPE_ANSWER)) {
			result = ServicesFactory.getInstance().getAnswerService().update((AnswerEntity)obj);
		}
		if(entity.getFormType().getName().equals(Constants.FORM_TYPE_RETURN)) {
			result = ServicesFactory.getInstance().getReturnService().update((ReturnEntity)obj);
		}
		if(entity.getFormType().getName().equals(Constants.FORM_TYPE_DISCONNECT)) {
			result = ServicesFactory.getInstance().getDisconnectService().update((DisconnectEntity)obj);
		}
		if(entity.getFormType().getName().equals(Constants.FORM_TYPE_PROMPT_COLLECT)) {
			result = ServicesFactory.getInstance().getPromptCollectService().update((PromptCollectEntity)obj);
		}
		if(entity.getFormType().getName().equals(Constants.FORM_TYPE_MENU)) {
			result = ServicesFactory.getInstance().getMenuService().update((MenuEntity)obj);
		}
		if(entity.getFormType().getName().equals(Constants.FORM_TYPE_DECISION)) {
			result = ServicesFactory.getInstance().getDecisionService().update((DecisionEntity)obj);
		}
		if(entity.getFormType().getName().equals(Constants.FORM_TYPE_FLOW)) {
			result = ServicesFactory.getInstance().getFlowService().update((FlowEntity)obj);
		}
		if(entity.getFormType().getName().equals(Constants.FORM_TYPE_TRANSFER)) {
			result = ServicesFactory.getInstance().getTransferService().update((TransferEntity)obj);
		}
		return result;
	}
	
	@Override
	public boolean save(FormEntity entity) {
		// TODO Auto-generated method stub
		
		boolean result = true;
		String sql = "INSERT INTO flow.form (id,name,description,formtype,formid,tag,condition,versionid) "
					   + "VALUES ('"+entity.getId()+"','"+entity.getName()+"','"+entity.getDescription()+"','"+entity.getFormType().getId()+"','"+((AbstractEntity)entity.getFormId()).getId()+"',"
					   + (entity.getTag() == null ? "NULL" : entity.getTag().getId())+","+(entity.getCondition() == null ? "NULL" : entity.getCondition().getId() )+",'"+entity.getVersionId().getId()+"')";
		result = db.ExecuteSql(sql);
		return result;
		
	}

	@Override
	public boolean update(FormEntity entity) {
		boolean result = true;
		String sql = "UPDATE flow.form SET name = '"+entity.getName()+"',description='"+entity.getDescription()+"',"
					   + "formtype='"+entity.getFormType().getId()+"',formid='"+((AbstractEntity)entity.getFormId()).getId()+"',"
					   + "tag="+(entity.getTag() == null ? "NULL" :  entity.getTag().getId())+","
					   + "condition="+(entity.getCondition() == null ? "NULL" : entity.getCondition().getId())+","
					   + "versionid='"+entity.getVersionId().getId()+"' "
					   + "WHERE id = '"+entity.getId()+"' ";
		result = db.ExecuteSql(sql);
		
		return result;
	}

	@Override
	public boolean delete(FormEntity entity) {
		// TODO Auto-generated method stub
		
		boolean result = true;
		
		String sql = "DELETE flow.form WHERE id ='"+entity.getId()+"' ";
		result = db.ExecuteSql(sql);
		
		return result;
	}

}
