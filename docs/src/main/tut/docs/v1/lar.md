---
layout: docs
title: "lar v1"
section: "v1"
---

## LAR

### Parsing

* `/ts/parse`

   * `POST` - Returns a JSON representation of a TS, or a list of errors if the TS fails to parse

Example body: 

```json
1|0123456789|9|201301171330|2013|12-9379899|900|MIKES SMALL BANK   XXXXXXXXXXX|1234 Main St|Sacramento|CA|99999-9999|MIKES SMALL INC|1234 Kearney St|San Francisco|CA|99999-1234|Mrs. Krabappel|916-999-9999|999-753-9999|krabappel@gmail.com
```

Example response 

```json
{
    "parent": {
        "city": "San Francisco",
        "name": "MIKES SMALL INC",
        "state": "CA",
        "zipCode": "99999-1234",
        "address": "1234 Kearney St"
    },
    "activityYear": 2013,
    "timestamp": 201301171330,
    "agencyCode": 9,
    "respondent": {
        "city": "Sacramento",
        "name": "MIKES SMALL BANK   XXXXXXXXXXX",
        "state": "CA",
        "zipCode": "99999-9999",
        "id": "0123456789",
        "address": "1234 Main St"
    },
    "contact": {
        "name": "Mrs. Krabappel",
        "phone": "916-999-9999",
        "fax": "999-753-9999",
        "email": "krabappel@gmail.com"
    },
    "id": 1,
    "totalLines": 900,
    "taxId": "12-9379899"
}
```

* `/lar/parse`

   * `POST` - Returns a JSON representation of a LAR, or a list of errors if the LAR fails to parse

Example body:
```json
2|0|1|10164                    |20170224|1|1|3|1|21|3|1|20170326|45460|18|153|0501.00|2|2|5| | | | |5| | | | |1|2|31|0| | | |NA   |2|1
```

Example reponse
```
{
    "respondentId": "0",
    "applicant": {
        "coSex": 2,
        "coRace5": "",
        "coEthnicity": 2,
        "race2": "",
        "coRace2": "",
        "coRace1": 5,
        "race4": "",
        "race3": "",
        "race1": 5,
        "sex": 1,
        "coRace3": "",
        "income": "31",
        "coRace4": "",
        "ethnicity": 2,
        "race5": ""
    },
    "hoepaStatus": 2,
    "agencyCode": 1,
    "actionTakenType": 1,
    "denial": {
        "reason1": "",
        "reason2": "",
        "reason3": ""
    },
    "rateSpread": "NA",
    "loan": {
        "applicationDate": "20170224",
        "propertyType": 1,
        "amount": 21,
        "purpose": 3,
        "id": "10164",
        "occupancy": 1,
        "loanType": 1
    },
    "id": 2,
    "actionTakenDate": 20170326,
    "geography": {
        "msa": "45460",
        "state": "18",
        "county": "153",
        "tract": "0501.00"
    },
    "lienStatus": 1,
    "preapprovals": 3,
    "purchaserType": 0
}
```

Example error response
```json
{
    "lineNumber": 0,
    "errorMessages": [
        "An incorrect number of data fields were reported: 38 data fields were found, when 39 data fields were expected."
    ]
}
```

### Validation


* `/ts/validate`

    * `POST` - Returns a list of syntactical, validity and/or quality errors.

| Query parameter | Description |
| --------------- | ----------- |
| check | String. Valid entries are: "syntactical", "validity", "quality".  If left blank or any other text is entered, will default to all checks. |

Example body: 

```json
{
    "parent": {
        "city": "San Francisco",
        "name": "MIKES SMALL INC",
        "state": "CA",
        "zipCode": "99999-1234",
        "address": "1234 Kearney St"
    },
    "activityYear": 2013,
    "timestamp": 201301171330,
    "agencyCode": 9,
    "respondent": {
        "city": "Sacramento",
        "name": "MIKES SMALL BANK   XXXXXXXXXXX",
        "state": "CA",
        "zipCode": "99999-9999",
        "id": "0123456789",
        "address": "1234 Main St"
    },
    "contact": {
        "name": "Mrs. Krabappel",
        "phone": "916-999-9999",
        "fax": "999-753-9999",
        "email": "krabappel@gmail.com"
    },
    "id": 1,
    "totalLines": 900,
    "taxId": "12-9379899"
}
```

Example response: 

```json
{
    "syntactical": {
        "errors": []
    },
    "validity": {
        "errors": []
    },
    "quality": {
        "errors": []
    }
}
```


* `/lar/validate`

    * `POST` - Returns a list of syntactical, validity and/or quality errors.  This endpoint omits certain edits that are not relevant to a single LAR.  Edits that are omitted: macro edits, TS-only edits (e.g. Q130), and the following: Q022, S025, S270.

| Query parameter | Description |
| --------------- | ----------- |
| check | String. Valid entries are: "syntactical", "validity", "quality".  If left blank or any other text is entered, will default to all checks. |

Example body:
```json
{
    "respondentId": "0",
    "applicant": {
        "coSex": 2,
        "coRace5": "",
        "coEthnicity": 2,
        "race2": "",
        "coRace2": "",
        "coRace1": 5,
        "race4": "",
        "race3": "",
        "race1": 5,
        "sex": 1,
        "coRace3": "",
        "income": "31",
        "coRace4": "",
        "ethnicity": 2,
        "race5": ""
    },
    "hoepaStatus": 2,
    "agencyCode": 1,
    "actionTakenType": 1,
    "denial": {
        "reason1": "",
        "reason2": "",
        "reason3": ""
    },
    "rateSpread": "NA",
    "loan": {
        "applicationDate": "20170224",
        "propertyType": 1,
        "amount": 21,
        "purpose": 3,
        "id": "10164",
        "occupancy": 1,
        "loanType": 1
    },
    "id": 2,
    "actionTakenDate": 20170326,
    "geography": {
        "msa": "45460",
        "state": "18",
        "county": "153",
        "tract": "0501.00"
    },
    "lienStatus": 1,
    "preapprovals": 3,
    "purchaserType": 0
}
```

Example response:
```json
{
    "syntactical": {
        "errors": []
    },
    "validity": {
        "errors": []
    },
    "quality": {
        "errors": []
    }
}
```

### Parse and Validate

* `/ts/parseAndValidate`

    * `POST` - Returns a list of syntactical, validity and/or quality errors.

| Query parameter | Description |
| --------------- | ----------- |
| check | String. Valid entries are: "syntactical", "validity", "quality".  If left blank or any other text is entered, will default to all checks. |

Example body: 

```json
1|0123456789|9|201301171330|2013|12-9379899|900|MIKES SMALL BANK   XXXXXXXXXXX|1234 Main St|Sacramento|CA|99999-9999|MIKES SMALL INC|1234 Kearney St|San Francisco|CA|99999-1234|Mrs. Krabappel|916-999-9999|999-753-9999|krabappel@gmail.com
```

Example response: 

```json
{
    "syntactical": {
        "errors": []
    },
    "validity": {
        "errors": []
    },
    "quality": {
        "errors": []
    }
}
```


* `/lar/parseAndValidate`

    * `POST` - Returns a list of syntactical, validity and/or quality errors. This endpoint omits certain edits that are not relevant to a single LAR.  Edits that are omitted: macro edits, TS-only edits (e.g. Q130), and the following: Q022, S025, S270.

| Query parameter | Description |
| --------------- | ----------- |
| check | String. Valid entries are: "syntactical", "validity", "quality".  If left blank or any other text is entered, will default to all checks. |

Example body:
```json
2|0|1|10164                    |20170224|1|1|3|1|21|3|1|20170326|45460|18|153|0501.00|2|2|5| | | | |5| | | | |1|2|31|0| | | |NA   |2|1
```

Example response:
```json
{
    "syntactical": {
        "errors": []
    },
    "validity": {
        "errors": []
    },
    "quality": {
        "errors": []
    }
}
```


