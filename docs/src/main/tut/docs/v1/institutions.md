---
layout: docs
title:  "Institutions"
section: "v1"
---
## Institutions

### Search

* `/institutions?domain=<domain>`

   * `GET` - Returns a list of institutions filtered by their email domain. If none are found, an HTTP 404 error code (not found) is returned

   Example response, with HTTP code 200:

   ```json
   {
     "institutions":
     [
        {
          "id": "0",
          "name": "Bank 0",
          "domains": ["test@bank0.com"],
          "externalIds":[
            {
              "value": "1234",
              "name": "occ-charter-id"
            },
            {
              "value": "1234",
              "name": "ncua-charter-id"
            }
          ]
        }
     ]
   }
   ```
   
* `/institutions/<institutionID>`

    * `GET`

    Retrieves the details of an institution. If not found, returns HTTP code 404

    Example Response with HTTP code 200, in `JSON` format:

    ```json
        {
          "id": "123",
          "agency": "CFPB",
          "activityYear": "2017",
          "institutionType": "bank",
          "cra": false,
          "externalIds": [{
            "id": "bank-id",
            "idType": "fdic-certificate-number"
          }],
          "emailDomains": [
            "email1",
            "email2"
          ],
          "respondent": {
            "externalId": {
                "id": "bank-id",
                "idType": "fdic-certificate-number"
            },
            "name": "bank 0",
            "state": "VA",
            "city": "City Name",
            "fipsStateNumber": "2"
          },
          "hmdaFilerFlag": true,
          "parent": {
            "respondentId": "12-3",
            "idRssd": 3,
            "name": "parent name",
            "city": "parent city",
            "state": "VA"
          },
          "assets": 123,
          "otherLenderCode": 0,
          "topHolder": {
            "idRssd": 4,
            "name": "top holder name",
            "city": "top holder city",
            "state": "VA",
            "country": "USA"
      }
    }
    ```

### HMDA Filers

* `/filers`

    * `GET`

    Retrieves list of HMDA filers.
    Example response with HTTP code 200, in `JSON` format:

    ```json
    {
        "institutions": [
            {
                "institutionId": "0",
                "name": "bank-0 National Association",
                "period": "2017",
                "respondentId": "Bank0_RID"
            },
            {
                "institutionId": "1",
                "name": "Bak 1",
                "period": "2016",
                "respondentId": "Bank1_RID"
            }
        ]
    }
    ```

* `/filers/<period>`

    * `GET`

    Retrieves list of HMDA filers, filtered by period.
    Example response with HTTP code 200, in `JSON` format:

    ```json
    {
        "institutions": [
            {
                "institutionId": "0",
                "name": "bank-0 National Association",
                "period": "2017",
                "respondentId": "Bank0_RID"
            }
        ]
    }
    ```
