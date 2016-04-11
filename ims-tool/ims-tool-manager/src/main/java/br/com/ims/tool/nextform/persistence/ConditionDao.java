package br.com.ims.tool.nextform.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import br.com.ims.tool.nextform.exception.DaoException;
import br.com.ims.tool.nextform.model.ConditionDto;
import br.com.ims.tool.nextform.model.ConditionGroupDto;
import br.com.ims.tool.nextform.model.ConditionMapDto;
import br.com.ims.tool.nextform.model.ConditionParametersDto;
import br.com.ims.tool.nextform.model.ConditionValueDto;
import br.com.ims.tool.nextform.util.ConnectionDB;



public class ConditionDao {

	public ConditionDto getConditionsById(long conditionId) throws DaoException {
		
		ConditionDto dto = new ConditionDto();
		
		ResultSet rs = null;
		ConnectionDB conn = null;
		try {
			conn = new ConnectionDB();

			String query = 	" SELECT CD.ID, CD.NAME, CD. DESCRIPTION, CD.TAG, "+
							" CG.CONDITIONID, CG.ID, CG.ORDERNUM, CG.CONDITIONMAPID, CM.ID, CM.NAME,  "+
							" CM.DESCRIPTION, CM.TYPE, CM.METHODREFERENCE, CM.LOG_ACTIVE, CP.STATUS, CP.TIMEOUT "+
							" FROM flow.CONDITION CD, flow.CONDITIONGROUP CG, flow.CONDITIONMAP CM "+
							" LEFT JOIN flow.CONTROLPANEL CP ON CM.METHODREFERENCE = CP.METHODNAME "+
							" WHERE CD.ID = "+conditionId+" "+
							" AND CD.ID = CG.CONDITIONID "+
							" AND CG.CONDITIONMAPID = CM.ID "+
							" ORDER BY CG.ORDERNUM ASC ";

			rs = conn.ExecuteQuery(query);
			
			Collection<ConditionGroupDto> listaConditionGroup = new ArrayList<ConditionGroupDto>();
			boolean first = true;
			
			while (rs.next()) {
				if (first) {
					dto.setId(rs.getLong(1));
					dto.setName(rs.getString(2));
					dto.setDescription(rs.getString(3));
					dto.setTag(rs.getLong(4));
					first = false;
				}
				
				ConditionGroupDto conditionGroup = new ConditionGroupDto();
				conditionGroup.setConditionId(rs.getLong(5));
				conditionGroup.setId(rs.getLong(6));
				conditionGroup.setOrder(rs.getLong(7));
				conditionGroup.setConditionMapId(rs.getLong(8));
				
				ConditionMapDto conditionMap = new ConditionMapDto();
				conditionMap.setId(rs.getLong(9));
				conditionMap.setName(rs.getString(10));
				conditionMap.setDescription(rs.getString(11));
				conditionMap.setType(rs.getString(12));
				conditionMap.setMethodReference(rs.getString(13));
				
				conditionMap.setLogActive(rs.getInt(14));
				conditionMap.setActive("true".equalsIgnoreCase(rs.getString(15)) ? true : false);
				conditionMap.setTimeout(rs.getInt(16));
				
				//buscar lista de chance
				conditionGroup.setListaConditionValue(getListConditionValue(conditionGroup.getId()));
				//buscar lista de parameters
				conditionGroup.setListaConditionParameters(getListConditionParameters(conditionGroup.getId()));
				
				conditionGroup.setConditionMap(conditionMap);
				listaConditionGroup.add(conditionGroup);
			}
				
			dto.setListaConditionGroup(listaConditionGroup);
		
		} catch (SQLException e) {
			throw new DaoException("Erro ao Recuperar a Condition de ID: " + conditionId, e);
		} finally {
			conn.finalize();
		}
		
		return dto;

	}
	
	public Collection<ConditionParametersDto> getListConditionParameters(long groupId) throws DaoException {
		Collection<ConditionParametersDto> lista = new ArrayList<ConditionParametersDto>();

		ResultSet rs = null;
		ConnectionDB conn = null;
		
		try {
			conn = new ConnectionDB();

			String query = 	" SELECT ID, CONDITIONGROUPID, PARAMNAME, PARAMVALUE " +
							" FROM flow.CONDITIONPARAMETERS WHERE CONDITIONGROUPID = "+groupId+" ORDER BY ID ASC ";

			
			rs = conn.ExecuteQuery(query);
			while (rs.next()) {
				ConditionParametersDto dto = new ConditionParametersDto();
				
				dto.setId(rs.getLong(1));
				dto.setConditionGroupId(rs.getLong(2));
				dto.setParamName(rs.getString(3));
				dto.setParamValue(rs.getString(4));

				lista.add(dto);
			}
		} catch (SQLException e) {
			throw new DaoException("Erro ao Recuperar a Lista CONDITION Parameters de GROUPID: "
					+ groupId, e);
		} finally {
			conn.finalize();
		}
		return lista;
	}
	
	public Collection<ConditionValueDto> getListConditionValue(long groupId) throws DaoException {
		
		Collection<ConditionValueDto> listaValue = new ArrayList<ConditionValueDto>();

		ResultSet rs = null;
		ConnectionDB conn = null;
		
		try {
			conn = new ConnectionDB();

			String query = 	" SELECT ID, CONDITIONGROUPID, ORDERNUM, OPERATION, VALUE1, VALUE2, " +
							" VALUE3, VALUE4, VALUE5, VALUE6, VALUE7, VALUE8, VALUE9, VALUE10, " +
							" TAGTRUE, TAGFALSE " +
							" FROM flow.CONDITIONVALUE WHERE CONDITIONGROUPID = "+groupId+" ORDER BY ORDERNUM ";

			
			rs = conn.ExecuteQuery(query);
			
			while (rs.next()) {
				ConditionValueDto dto = new ConditionValueDto();

				dto.setId(rs.getLong(1));
				dto.setConditionGroupId(rs.getLong(2));
				dto.setOrder(rs.getLong(3));
				dto.setOperation(rs.getString(4));
				dto.setValue1(rs.getString(5));
				dto.setValue2(rs.getString(6));
				dto.setValue3(rs.getString(7));
				dto.setValue4(rs.getString(8));
				dto.setValue5(rs.getString(9));
				dto.setValue6(rs.getString(10));
				dto.setValue7(rs.getString(11));
				dto.setValue8(rs.getString(12));
				dto.setValue9(rs.getString(13));
				dto.setValue10(rs.getString(14));
				dto.setTagTrue(rs.getLong(15));
				dto.setTagFalse(rs.getLong(16));


				listaValue.add(dto);
			}
		} catch (SQLException e) {
			throw new DaoException("Erro ao Recuperar a Lista CONDITIONVALUE de GROUPID: "
					+ groupId, e);
		} finally {
			conn.finalize();
		}
		return listaValue;
	}

}
