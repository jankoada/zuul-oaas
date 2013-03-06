package cz.cvut.authserver.oauth2.api.models;

import cz.cvut.authserver.oauth2.api.validators.EachEnum;
import cz.cvut.authserver.oauth2.api.validators.EachURI;
import cz.cvut.authserver.oauth2.api.validators.EnumValue;
import cz.cvut.authserver.oauth2.api.validators.ValidURI;
import cz.cvut.authserver.oauth2.models.enums.AuthorizationGrant;
import cz.jirutka.validator.collection.constraints.EachPattern;
import cz.jirutka.validator.collection.constraints.EachSize;
import cz.jirutka.validator.spring.SpELAssert;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.oauth2.provider.BaseClientDetails.ArrayOrStringDeserializer;
import org.springframework.security.oauth2.provider.ClientDetails;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * DTO for {@link ClientDetails}.
 *
 * @author Jakub Jirutka <jakub@jirutka.cz>
 */
@SpELAssert(value = "hasRedirectUri()", applyIf = "authorizedGrantTypes.contains('auth_code')",
            message = "{validator.missing_redirect_uri}")
@JsonAutoDetect(JsonMethod.NONE)
@JsonSerialize(include = Inclusion.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientDTO implements Serializable {

    private static final long serialVersionUID = 1L;

	@JsonProperty("client_id")
	private String clientId;

	@JsonProperty("client_secret")
	private String clientSecret;

    @NotEmpty
    @EachSize( @Size(min = 5, max = 255) )
    @EachPattern( @Pattern(regexp = "[a-zA-Z0-9\\-_\\.]+") )
    @JsonProperty("scope")
	@JsonDeserialize(using = ArrayOrStringDeserializer.class)
	private Collection<String> scope;

    //TODO
	@JsonProperty("resource_ids")
	@JsonDeserialize(using = ArrayOrStringDeserializer.class)
	private Collection<String> resourceIds;

    @NotEmpty
    @EachEnum( @EnumValue(value = AuthorizationGrant.class,
        message = "{validator.invalid_grant_type}"))
	@JsonProperty("authorized_grant_types")
	@JsonDeserialize(using = ArrayOrStringDeserializer.class)
	private Collection<String> authorizedGrantTypes;

    @EachSize( @Size(min = 5, max = 255) )
    @EachURI( @ValidURI(relative = false, fragment = false) )
	@JsonProperty("redirect_uri")
	@JsonDeserialize(using = ArrayOrStringDeserializer.class)
	private Collection<String> registeredRedirectUri;

    @JsonProperty("authorities")
    @JsonDeserialize(using = ArrayOrStringDeserializer.class)
	private List<String> authorities;

    //TODO
	@JsonProperty("access_token_validity")
	private Integer accessTokenValiditySeconds;

    //TODO
	@JsonProperty("refresh_token_validity")
	private Integer refreshTokenValiditySeconds;

    @JsonProperty("product_name")
	private String productName;



    public String getClientId() {
        return clientId;
    }
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public Collection<String> getScope() {
        return scope;
    }
    public void setScope(Collection<String> scope) {
        this.scope = scope;
    }

    public Collection<String> getResourceIds() {
        return resourceIds;
    }
    public void setResourceIds(Collection<String> resourceIds) {
        this.resourceIds = resourceIds;
    }

    public Collection<String> getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }
    public void setAuthorizedGrantTypes(Collection<String> authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public Collection<String> getRegisteredRedirectUri() {
        return registeredRedirectUri;
    }
    public void setRegisteredRedirectUri(Collection<String> registeredRedirectUri) {
        this.registeredRedirectUri = registeredRedirectUri;
    }

    public List<String> getAuthorities() {
        return authorities;
    }
    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }
    public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }

    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValiditySeconds;
    }
    public void setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
        this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
    }

    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    @SuppressWarnings("UnusedDeclaration")
    public boolean hasRedirectUri() {
        return !registeredRedirectUri.isEmpty();
    }


	@Override
	public String toString() {
        return String.format("Client [%s]", clientId);
	}

}