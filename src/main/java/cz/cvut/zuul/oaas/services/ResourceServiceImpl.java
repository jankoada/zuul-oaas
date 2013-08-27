package cz.cvut.zuul.oaas.services;

import cz.cvut.zuul.oaas.api.models.ResourceDTO;
import cz.cvut.zuul.oaas.api.resources.exceptions.NoSuchResourceException;
import cz.cvut.zuul.oaas.dao.ResourceDAO;
import cz.cvut.zuul.oaas.generators.IdentifierGenerator;
import cz.cvut.zuul.oaas.models.Resource;
import cz.cvut.zuul.oaas.models.Scope;
import cz.cvut.zuul.oaas.support.CaseInsensitiveToEnumConverter;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory.Builder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

import static lombok.AccessLevel.NONE;
import static lombok.AccessLevel.PACKAGE;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
@Service
@Setter @Slf4j
public class ResourceServiceImpl implements ResourceService {

    private ResourceDAO resourceDAO;
    private IdentifierGenerator identifierGenerator;
    private MapperFactory mapperFactory;

    @Setter(NONE) @Getter(PACKAGE)
    private MapperFacade mapper;


    public List<ResourceDTO> getAllResources() {
        return mapper.mapAsList(resourceDAO.findAll(), ResourceDTO.class);
    }

    public List<ResourceDTO> getAllPublicResources() {
        return mapper.mapAsList(resourceDAO.findAllPublic(), ResourceDTO.class);
    }

    public String createResource(ResourceDTO resourceDTO) {
        Resource resource = mapper.map(resourceDTO, Resource.class);

        String resourceId;
        do {
            log.debug("Generating unique resourceId");
            resourceId = identifierGenerator.generateArgBasedIdentifier(resource.getName());
        } while (resourceDAO.exists(resourceId));

        resource.setId(resourceId);

        log.info("Creating new resource: [{}]", resource);
        resourceDAO.save(resource);

        return resourceId;
    }

    public void updateResource(String id, ResourceDTO resourceDTO) throws NoSuchResourceException{
        log.info("Updating resource [{}]", resourceDTO);

        assertResourceExists(id);
        resourceDAO.save(mapper.map(resourceDTO, Resource.class));
    }

    public ResourceDTO findResourceById(String id) throws NoSuchResourceException {
        Resource resource = resourceDAO.findOne(id);

        if (resource == null) {
            throw new NoSuchResourceException("No such resource with id = " + id);
        }
        return mapper.map(resource, ResourceDTO.class);
    }

    public void deleteResourceById(String id) throws NoSuchResourceException {
        assertResourceExists(id);
        resourceDAO.delete(id);
    }


    private void assertResourceExists(String resourceId) {
        if (! resourceDAO.exists(resourceId)) {
            throw new NoSuchResourceException("No such resource with id = " + resourceId);
        }
    }

    @PostConstruct void setupMapper() {
        MapperFactory factory = defaultIfNull(mapperFactory, new Builder().build());

        factory.getConverterFactory()
                .registerConverter(new CaseInsensitiveToEnumConverter());

        factory.registerClassMap(factory
                .classMap(Resource.class, ResourceDTO.class)
                .field("scopes", "auth.scopes")
                .byDefault()
        );
        factory.registerClassMap(factory
                .classMap(Scope.class, ResourceDTO.Scope.class)
                .byDefault()
        );
        mapper = factory.getMapperFacade();
    }
}
