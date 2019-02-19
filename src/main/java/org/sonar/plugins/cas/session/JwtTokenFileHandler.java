package org.sonar.plugins.cas.session;

import org.apache.commons.lang.StringUtils;
import org.sonar.plugins.cas.util.SimpleJwt;

import javax.xml.bind.JAXB;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class JwtTokenFileHandler {
    private String sessionStorePath;

    public JwtTokenFileHandler(String sessionStorePath) {
        this.sessionStorePath = sessionStorePath;
    }

    public boolean isJwtStored(String jwtId) {
        String jwtFile = sessionStorePath + File.separator + jwtId;
        return Files.exists(Paths.get(jwtFile));
    }

    public SimpleJwt get(String jwtId) {
        return null;
    }


    public void store(String jwtId, SimpleJwt jwt) throws IOException {
        if (StringUtils.isEmpty(jwtId)) {
            throw new IllegalArgumentException("Could not store JWT: jwtId must not be null");
        }
        if (jwt == null) {
            throw new IllegalArgumentException("Could not store JWT: jwt must not be null");
        }

        String jwtFile = sessionStorePath + File.separator + jwtId;
        Path path = Files.createFile(Paths.get(jwtFile));

        Charset charset = Charset.forName("US-ASCII");
        try (BufferedWriter writer = Files.newBufferedWriter(path, charset)) {
            JAXB.marshal(jwt, writer);
            writer.flush();
        }
    }

    public void replace(String jwtId, SimpleJwt invalidated) {

    }
}
