package gov.cfpb.keycloak.authenticator.hmda;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InstitutionServiceHmdaApiImplTest {

    @Test
    @DisplayName("\uD83D\uDE31")
    void testGetInstitutions(){
        System.out.println("Testing Institutions Service Client");
        //assertEquals(2, 2);

        String url = "https://ffiec-api.cfpb.gov/public";

        InstitutionService institutionService = new InstitutionServiceHmdaApiImpl(url, false);

        try {
            List<Institution> institutions = institutionService.findInstitutionsByDomain("bankofamerica.com");
            institutions.forEach(e -> e.getName());
        } catch (InstitutionServiceException e) {
            System.out.println(e.getLocalizedMessage());
        }


    }
}
