package br.com.ims.flow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.ims.flow.common.Constants;
import br.com.ims.flow.common.DbConnection;
import br.com.ims.flow.factory.ServicesFactory;
import br.com.ims.flow.model.ConditionEntity;
import br.com.ims.flow.model.FormEntity;
import br.com.ims.flow.model.FormTypeEntity;
import br.com.ims.flow.model.TagEntity;
import br.com.ims.flow.model.TagTypeEntity;

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
		return obj;
	}
	public List<FormEntity> getByFilter(String where) {

		String sql = "SELECT f.id f_id,f.name f_name,f.description f_description,f.formype f_formtype, f.formid f_formid, f.condition f_condition, f.positionx f_positionx, f.positiony f_positiony,"+ 
					 "t.id t_id, t.description t_description, "+ 
					 "tt.id tt_id, tt.name tt_name,tt.description tt_description "+
					 "FROM flow.form f "+ 
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
				ConditionEntity condition = null;
				if(rs.getString("f_condition") != null && rs.getString("f_condition").length() > 0) {
					condition =  ServicesFactory.getInstance().getConditionService().get(rs.getString("f_condition"));
				}
				FormTypeEntity formType = ServicesFactory.getInstance().getFormTypeService().get(rs.getString("f_formtype"));
				
				Object obj = this.getObject(formType, rs.getString("f_id"));
				
				FormEntity form = new FormEntity();
				form.setId(rs.getString("f_id"));
				form.setDescription(rs.getString("f_description"));
				form.setCondition(condition);
				form.setTag(tag);
				form.setPositionX(rs.getString("f_positionx"));
				form.setPositionY(rs.getString("f_positiony"));

				//continuar aqui
				result.add(audio);
				
				/*
				 * private FormTypeEntity formType;
	private Object formId;
	private TagEntity tag;
	private ConditionEntity condition;
	private FormEntity nextFormDefault;
	private VersionEntity versionId;
				 */
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
		
		return result;
	}
	public List<FormEntity> getAll() {
		return this.getByFilter(null);
	}

	@Override
	public FormEntity get(String id) {
		// TODO Auto-generated method stub
		List<FormEntity> result = this.getByFilter("WHERE f.id = "+id);
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
		
	}
	public List<FormEntity> getByTypeName(String typeName) {
		return this.getFilter("WHERE tt.name = '"+typeName+"'");
		
	}

	@Override
	public boolean save(FormEntity entity) {
		// TODO Auto-generated method stub
		result.add(entity);
		return true;
		
	}

	@Override
	public boolean update(FormEntity entity) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean delete(FormEntity entity) {
		// TODO Auto-generated method stub
		result.remove(entity);
		return true;
		
	}

}
