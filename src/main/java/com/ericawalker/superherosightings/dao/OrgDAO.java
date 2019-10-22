/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericawalker.superherosightings.dao;

import com.ericawalker.superherosightings.dto.Org;
import java.util.List;

/**
 *
 * @author ericabenton
 */
public interface OrgDAO {
    
    Org getOrgByID(int orgID);
    
    List<Org> getAllOrgs();
    
    Org addOrg(Org org);
    
    void updateOrg(Org org);
    
    void deleteOrgById(int orgID);
       
}