---
layout: docs
title: "rate spread"
section: "v1"
---

## Rate Spread

### Rate Spread Calculator

* `rateSpread`

    * `POST` - Calculate Rate Spread


Example payload, in `JSON` format:

```json
{
  "actionTakenType": 1,
  "loanTerm": 30,
  "amortizationType": "FixedRate",
  "apr": 6.0,
  "lockInDate": "2017-11-20",
  "reverseMortgage": 2
}
```

`RateType` can take the following values: `FixedRate` and `VariableRate`

Example Response, in `JSON` format:

```json
{
  "rateSpread": "2.01"
}
```

The response is either a number representing the Rate Spread or "NA"

* `rateSpread/csv`

    * `POST` - Batch Rate Spread calculator

Example file contents:

```
1,30,FixedRate,6.0,2017-11-20,2
1,30,VariableRate,6.0,2017-11-20,2
```

The contents of this file include the `Action Taken Type` (values 1,2,8), `Loan Term` (1 - 50 years),
`Amortization Type` (`FixedRate` or `VariableRate`), `APR`, `Lock In Date` and `Reverse Mortgage` (values 1 or 2)

Example response in `CSV` format:

```csv
action_taken_type,loan_term,amortization_type,apr,lock_in_date,reverse_mortgage,rate_spread
1,30,FixedRate,6.0,2017-11-20,2,2.01
1,30,VariableRate,6.0,2017-11-20,2,2.15
```

