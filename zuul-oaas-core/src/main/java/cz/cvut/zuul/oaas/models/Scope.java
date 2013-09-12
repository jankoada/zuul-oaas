package cz.cvut.zuul.oaas.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;

import java.io.Serializable;

/**
 * Represents a scope of the access token issued by authorization server in
 * OAuth 2.0 protocol. Access token scopes are then used in the authorization
 * and token endpoints. The value of the scope parameter is expressed as a list
 * of space-delimited, case sensitive strings (%x21 / %x23-5B / %x5D-7E). The
 * strings are defined by the authorization server.
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor

@TypeAlias("Scope")

public class Scope implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    
    private String description;

    private boolean secured = false;
}