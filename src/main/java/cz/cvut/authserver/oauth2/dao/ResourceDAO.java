package cz.cvut.authserver.oauth2.dao;

import cz.cvut.authserver.oauth2.api.resources.exceptions.NoSuchResourceException;
import cz.cvut.authserver.oauth2.models.resource.Resource;
import java.util.List;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public interface ResourceDAO {
    
    /**
     * Finds resource with the given id.
     *
     * @param id resource's id
     * @return resource if exists
     * @throws NoSuchResourceException when no matching resource was found
     */
    public Resource findResourceById(Long id) throws NoSuchResourceException;
    
    /**
     * Creates new resource.
     * 
     * @param resource resource to be created
     * @return created Resource
     */
    public Resource createResource(Resource resource);
    
    /**
     * Update resource with the given id.
     * 
     * @param id resource's id
     * @param resource resource's content to be updated with
     * @throws NoSuchResourceException when no matching resource was found
     */
    public void updateResource(Long id, Resource resource) throws NoSuchResourceException;

    /**
     * Delete resource with the given id.
     * 
     * @param id resource§s id
     * @throws NoSuchResourceException when no matching resource was found
     * @return true if it contained the specified resource
     */
    public boolean deleteResourceById(Long id) throws NoSuchResourceException;
    
    /**
     * Returns all resources.
     * 
     * @return all resources
     */
    public List<Resource> getAllResources();
}