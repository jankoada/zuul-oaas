package cz.cvut.authserver.oauth2.services;

import javax.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.BaseClientDetails;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public interface ClientsService {
    
    public ClientDetails findClientDetailsById(@PathVariable String clientId) throws OAuth2Exception;

    public void createClientDetails(BaseClientDetails client) throws ClientAlreadyExistsException;
    
    public void removeClientDetails(String clientId) throws NoSuchClientException;
    
    public void resetClientSecret(String clientId) throws NoSuchClientException;
    
    public void addResourceToClientDetails(String clientId, @RequestBody String resourceId) throws Exception;
    
    public void removeResourceFromClientDetails(String clientId, String resourceId) throws OAuth2Exception, NoSuchClientException;
    
    public void addGrantToClientDetails(String clientId, String grantType) throws Exception;

    public void deleteGrantFromClientDetails(String clientId, String grantType) throws OAuth2Exception, NoSuchClientException;

    public void addScopeToClientDetails(String clientId, @RequestBody String scope) throws Exception;
    
    public void removeScopeFromClientDetails(String clientId, String scope) throws OAuth2Exception, NoSuchClientException;

    public void addRoleToClientDetails(String clientId, String role) throws Exception;

    public void deleteRoleFromClientDetails(String clientId, String role) throws OAuth2Exception, NoSuchClientException;

    public void addRedirectUriToClientDetails(String clientId, String redirectUri) throws Exception;

    public void deleteRedirectUriFromClientDetails(String clientId, String redirectUri) throws OAuth2Exception, NoSuchClientException;
    
    public void addBrandingInformationToClientDetails(String clientId, String brand) throws OAuth2Exception, NoSuchClientException;

}
